package com.educandoweb.course.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educandoweb.course.entities.Order;
import com.educandoweb.course.services.OrderService;

@RestController                     // Notação pra informar que é um CONTROLADOR
@RequestMapping(value = "/orders")   // Nome do recurso pra acessar depois
public class OrderResource {
	// Essa é a RESOURCE LAYER onde teremos os controladores REST
	
	//=====================================================================//
	//                     APPLICATION
	//               RESOURCE LAYER (REST CONTROLLERS)
	//             SERVICE LAYER                          || ENTITIES ||
	//             DATA ACCESS LAYER (DATA REPOSITORIES)  ||          ||
	//=====================================================================//
	
	@Autowired
	private OrderService service; //Vai ser injetado automaticamente
	
	@GetMapping   // Pra responder a requisição GET do HTTP
	public ResponseEntity<List<Order>> findAll() {   // ResponseEntity é um método especifico do Java pra atender uma requisição WEB

		// inibindo mock simples
		//Order u = new Order(1L, "Maria", "maria@gmail.com", "99999-9999", "12345");
		
		List<Order> list = service.findAll();
		
		return ResponseEntity.ok().body(list);
	}
	
	//Indica que minha requisição vai aceitar um id na url
	@GetMapping(value = "/{id}") 
	public ResponseEntity<Order> findById(@PathVariable Long id) {  // Esse @PathVariable serve pra indicar ao Spring que deve relacionar o id ao do GetMapping
		Order obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
}
