package com.rental.sys.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfiguration {
	@Bean
	public OpenAPI defineOpenApi() {
		Info information = new Info().title("Rental Hub").version("1.0").description(
				"This is Rental Project.");
		return new OpenAPI().info(information);
	}
}