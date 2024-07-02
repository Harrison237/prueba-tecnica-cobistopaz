package com.cobistopaz.prueba_tecnica;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PruebaTecnicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaTecnicaApplication.class, args);
	}

    @Bean
    OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Prueba Técnica Cobiz Topaz - Harrison Daniel Ramírez Burgos")
						.version("1.0")
						.description("Esta es una prueba ténica desarrollada mediante Spring Boot aplicando conceptos de arquitectura hexagonal y buenas practicas de desarrollo." +
								"\nPara el aseguramiento de EndPoints se utilizó el estándar Json Web Token (jwt), que podrá generar en el apartado del AuthController." +
								"\nEste proyecto fue desarrollado con el editor de código Visual Studio Code y el IDE IntelliJ IDEA Community Edition." +
								"\nEl motor de base de datos de este proyecto, según lo requerido, es MySQL.")
						.license(new License().name("Apache 2.0").url("https://springdoc.org/")))
				.addSecurityItem(new SecurityRequirement()
				.addList("Bearer Authentication"))
				.components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
	}

	private SecurityScheme createAPIKeyScheme() {
		return new SecurityScheme().type(SecurityScheme.Type.HTTP)
				.bearerFormat("JWT")
				.scheme("bearer");
	}

}
