package com.educandoweb.course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educandoweb.course.entities.Category;
import com.educandoweb.course.repositories.CategoryRepository;

// Camada de SERVIÇO, usada pra acessar camada REPOSITORIES pra acesso a DADOS, aqui ficariam as regras de negócio, sempre é importante ter essa camada entre CONTROLLER e REPOSITORIES
// Temos @Component registra a classe como componente do Spring permitindo ser injetado via Autowired, temos tambem o @Repository, @Service, 
@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	public List<Category> findAll() {
		return repository.findAll();
	}
	
	public Category findById(Long id) {
		Optional<Category> obj = repository.findById(id); // O Optional, desde o Java 8, serve pra retornar o objeto tipo Category, no caso, no findById do Id, se fosse outro objeto, caçava por outro Database.
		return obj.get(); // Retorna objeto Category presente no optional
	}
}
