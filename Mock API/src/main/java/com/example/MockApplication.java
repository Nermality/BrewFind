package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class MockApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) { 
	 	return application.sources(MockApplication.class); 
	 } 
	 
	 
	public static void main(String[] args) { 
	 	new MockApplication() 
	 		.configure(new SpringApplicationBuilder(MockApplication.class)) 
			.run(args); 
	} 

}
