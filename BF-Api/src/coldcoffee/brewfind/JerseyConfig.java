package coldcoffee.brewfind;

import coldcoffee.brewfind.Controllers.DrinkController;
import coldcoffee.brewfind.Controllers.EventController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import coldcoffee.brewfind.Configuration.CORSResponseFilter;
import coldcoffee.brewfind.Controllers.BreweryController;
import coldcoffee.brewfind.Controllers.UserController;

// This is the class to register all of the controllers in
// Jersey requires this when working with Spring

@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(BreweryController.class);
		register(UserController.class);
		register(EventController.class);
		register(DrinkController.class);
		register(CORSResponseFilter.class);
	}
}