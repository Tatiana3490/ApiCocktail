package com.example.apicocktail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ApicocktailApplication {

	public static void main(String[] args) {

		SpringApplication.run(ApicocktailApplication.class, args);
	}

}
