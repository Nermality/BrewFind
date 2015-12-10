package coldcoffee.brewfind.api.Controllers;

import javax.validation.constraints.NotNull;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import coldcoffee.brewfind.api.Configuration.SpringMongoConfig;
import coldcoffee.brewfind.api.Objects.User;
import coldcoffee.brewfind.api.Repositories.UserRepository;

@Component
@Path("/user")
public class UserController {

	private ApplicationContext ctx;
	private UserRepository userRepo;
	
	public UserController() {
		ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		userRepo = ctx.getBean(UserRepository.class);
	}
	
	@Path("/new")
	@Produces("application/json")
	@PUT
	public User addUser(
			@NotNull @HeaderParam("uname") String uname,
			@NotNull @HeaderParam("pass")  String pass) {
		
		if(!userRepo.searchByUname(uname).isEmpty()) {
			return null;
		}
		
		User toIns = new User(uname, pass);
		userRepo.save(toIns);
		
		return toIns;
	}
}
