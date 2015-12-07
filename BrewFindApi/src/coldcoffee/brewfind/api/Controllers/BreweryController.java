package coldcoffee.brewfind.api.Controllers;

import javax.ws.rs.Path;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import coldcoffee.brewfind.api.Database.SpringMongoConfig;
import coldcoffee.brewfind.api.Repositories.BreweryRepository;

@Component
@Path("brewery")
public class BreweryController {
	
	private ApplicationContext ctx;
	private BreweryRepository breweryRepo;
	
	public BreweryController() {
		ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		breweryRepo = ctx.getBean(BreweryRepository.class);
	}
	
	

}
