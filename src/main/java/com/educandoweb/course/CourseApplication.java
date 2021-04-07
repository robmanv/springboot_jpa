package com.educandoweb.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//============================================//
// BASICAMENTE TEMOS AS CAMADAS:
//============================================//
//  APPLICATION
//  CONTROLLERS ou RESOURCES
//  SERVICES      |-> ENTITIES
//  REPOSITORIES  |
//============================================//
@SpringBootApplication
public class CourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseApplication.class, args);
	}

}
