package com.graphql.exemple.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * Classe de Configuração do Flyway
 * 
 * @author Ricardo
 *
 */
@Configuration
public class FlywayConfig {

	@Value("${spring.profiles.active}")
	private String activeProfile;

	/**
	 * Roda o flyway padrão, logo no inicio
	 */
	@Bean
	FlywayMigrationInitializer flywayInitializer(Flyway flyway) {
		Flyway.configure().locations("classpath:db/migration");

		return new FlywayMigrationInitializer(flyway, (f) -> {
			f.migrate();
		});
	}

	/**
	 * Roda o flyway após a carga do JPA
	 */
	@Bean
	@DependsOn("entityManagerFactory")
	FlywayMigrationInitializer delayedFlywayInitializer(Flyway flyway) {
		Flyway.configure().locations("classpath:db/migrationAfterLoadJPA");

		return new FlywayMigrationInitializer(flyway, (f) -> {
			f.migrate();
		});
	}
}
