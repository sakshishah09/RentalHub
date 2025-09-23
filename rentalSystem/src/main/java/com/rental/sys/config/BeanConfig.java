package com.rental.sys.config;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BeanConfig {
	
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}
	
//	  @Bean
//      public PasswordEncoder passwordEncoder() {
//          return new BCryptPasswordEncoder();
//      }

}
