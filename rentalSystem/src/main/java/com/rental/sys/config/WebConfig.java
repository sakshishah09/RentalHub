package com.rental.sys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200", "http://127.0.0.1:5500", "http://localhost:2024")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*")
						.allowCredentials(false);
			}

			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
//				registry.addResourceHandler("/GameImages/**").addResourceLocations("file:storage/GameImages/");
//				registry.addResourceHandler("/GameVideos/**").addResourceLocations("file:storage/GameVideos/");
			}
		};
	}
}