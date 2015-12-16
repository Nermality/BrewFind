package coldcoffee.brewfind.api;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import coldcoffee.brewfind.api.Controllers.BreweryController;
import coldcoffee.brewfind.api.Controllers.EventController;
import coldcoffee.brewfind.api.Controllers.UserController;

// This is the class to register all of the controllers in
// Jersey requires this when working with Spring

@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(EventController.class);
		register(BreweryController.class);
		register(UserController.class);
	}
}