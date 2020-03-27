package com.graphql.exemple;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackageClasses = ExemploGraphQlApplication.class)
@SpringBootApplication(scanBasePackages = "com.graphql")
@EntityScan("com.graphql")
@EnableJpaRepositories("com.graphql")
public class ExemploGraphQlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExemploGraphQlApplication.class, args);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Recife"));
	}

}
