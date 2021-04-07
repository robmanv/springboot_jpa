package com.educandoweb.course.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.services.UserService;

@RestController                     // Notação pra informar que é um CONTROLADOR
@RequestMapping(value = "/users")   // Nome do recurso pra acessar depois
public class UserResource {
	// Essa é a RESOURCE LAYER onde teremos os controladores REST
	
	//=====================================================================//
	//                     APPLICATION
	//               RESOURCE LAYER (REST CONTROLLERS)
	//             SERVICE LAYER                          || ENTITIES ||
	//             DATA ACCESS LAYER (DATA REPOSITORIES)  ||          ||
	//=====================================================================//
	
	@Autowired
	private UserService service; //Vai ser injetado automaticamente
	
	@GetMapping   // Pra responder a requisição GET do http
	public ResponseEntity<List<User>> findAll() {   // ResponseEntity é um método especifico do Java pra atender uma requisição WEB

		// inibindo mock simples
		//User u = new User(1L, "Maria", "maria@gmail.com", "99999-9999", "12345");
		
		List<User> list = service.findAll();
		
		return ResponseEntity.ok().body(list);
	}
	
	//Indica que minha requisição vai aceitar um id na url
	@GetMapping(value = "/{id}") 
	public ResponseEntity<User> findById(@PathVariable Long id) {  // Esse @PathVariable serve pra indicar ao Spring que deve relacionar o id ao do GetMapping
		User obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<User> insert(@RequestBody User obj) {  //O RequestBody serve pra desserializar o Json em objeto User, retorno deve ser 201
		obj = service.insert(obj); 
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri(); // obter caminho do recurso que inserimos
		return ResponseEntity.created(uri).body(obj); // no response.ok vai retornar 200, quando é insert o padrão é 201 devo usar o metodo created, criando a URI no esquema acima
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {   // o Void é com V maiúsculo, pois tem retorno mas vazio, informo o noContent, retorno deve ser 204
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	// Vai capturar do REST a chamada PUT
	@PutMapping(value = "/{id}")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}
