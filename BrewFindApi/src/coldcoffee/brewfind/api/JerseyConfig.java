package coldcoffee.brewfind.api;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import coldcoffee.brewfind.api.Controllers.BreweryController;
import coldcoffee.brewfind.api.Controllers.EventController;

@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(EventController.class);
		register(BreweryController.class);
	}
}