package com.graphql.exemple.ExemploGraphQL;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExemploGraphQlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExemploGraphQlApplication.class, args);
	}
	
	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Recife"));
	}

}
