package com.example;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(BreweriesController.class);
		register(BreweryController.class);
		register(EventsController.class);
		register(EventController.class);
	}
}
