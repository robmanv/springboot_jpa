package com.educandoweb.course.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.educandoweb.course.services.exceptions.DatabaseException;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;

//Essa notação vai interceptar as exceções
@ControllerAdvice
public class ResourceExceptionHandler {
	
	// 1. Vou capturar qualquer RuntimeException usando a classe ResourceNotFoundException
	// 2. O Spring vai ficar observando essa classe, pra RuntimeException, e vou retornar o objeto StandardError, com a execução automática do método abaixo, executada pelo Spring
	// 3. Vou alimentar no meu método o RuntimeException alimentado pelo ResourceNotFoundException e o HttpServletRequest, este obrigatório
	// 4. Vou montar meu objeto StandardError com os dados padrões
	// 5. Retorno objeto StandardError com a seguinte aparência:
	////{
	//  "timestamp": "2020-12-24T01:59:02.987+00:00",
	//  "status": 500,
	//  "error": "Internal Server Error",
	//  "message": "",
	//  "path": "/users/7"
	//  }

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> resourceNotFound(DatabaseException e, HttpServletRequest request) {
		String error = "Database error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}

}
