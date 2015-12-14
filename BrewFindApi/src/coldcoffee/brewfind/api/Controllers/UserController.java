package coldcoffee.brewfind.api.Controllers;

import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import coldcoffee.brewfind.api.Objects.BrewFindResponse;
import coldcoffee.brewfind.api.Objects.BrewFindToken;
import coldcoffee.brewfind.api.Objects.User;
import coldcoffee.brewfind.api.Objects.UserQuery;
import coldcoffee.brewfind.api.Services.UserService;

@Component
@Path("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	private Gson gson = new Gson();
	
	private final String _sexy = "catfarts";
	
	public String sanitize(String s) {
		
		s = s.trim();
		if(s.startsWith("$")) {
			s = s.substring(1);
		}
		
		return s;
	}
	
	@Path("/test")
	@Produces("application/json")
	@GET
	public User getUser(){
		User toRet = userService.test();
		System.out.println(toRet);
		return toRet;
	}
	
	@Path("/auth")
	@Produces("application/json")
	@GET
	public BrewFindResponse auth(
			@NotNull @HeaderParam("uname") 	String uname,
			@NotNull @HeaderParam("pass")	String pass) {
		
		BrewFindResponse response;
		
		// Sanitize data
		String newu = sanitize(uname);
		
		// Test if user is in db
		User u = userService.getUser(newu);
		if(u == null) {
			response = new BrewFindResponse(2, "User " + newu + " not found in DB");
			return response;
		}
		
		// Else, authenticate
		if(pass != u.getU_pass()) {
			response = new BrewFindResponse(3, "Incorrect login information");
			return response;
		}
		
		// Check the existing token...?

		
		// Create token
		BrewFindToken token = new BrewFindToken(u.getU_access(), Base64.encodeAsString(newu.getBytes()));
		
		// Save token
		if(userService.saveNewToken(u, token) == null) {
			// some issue happened
			System.out.println("Didn't like saving that token...");
		}
		
		// Return token
		response = new BrewFindResponse(0, "OK", token);
		
		return response;
	}
	
	@Produces("application/json")
	@GET
	public User getUser(
			@NotNull @HeaderParam("token")	BrewFindToken token) {
		
		// Check to see if user in in db
		
		
		// If not, return error
		
		// Else, return user information
		return null;
	}
	
	@Produces("application/json")
	@POST
	public BrewFindResponse updateUser(String body) {
		BrewFindResponse response;
		
		// Check to see that user is in db
		UserQuery query = gson.fromJson(body, UserQuery.class);
		
		if(query.getToken() == null) {
			response = new BrewFindResponse(4, "No token found, authorization failed");
			return response;
		}
		
		if(!userService.checkToken(query.getToken())) {
			response = new BrewFindResponse(5, "Invalid token, authorization failed");
			return response;
		}
		
		
		
		// If not, error
		
		// Else, determine what's being updated
		
		// Sanitize data
		
		// Update information in database
		
		// Return new user object
		return null;
	}
	
	@Produces("application/json")
	@DELETE
	public String deleteUser(
			@NotNull @HeaderParam("token")	String token) {
		
		// Check if user is in db
		
		// If not, error
		
		// Else, remove information from user db
		
		// Shouldn't be any other ties to the user
		
		// Return success
		return null;
	}
	
	@Produces("application/json")
	@PUT
	public User addUser(String body) {

		// Make sure the user isn't in the database
		
		// If so, return fail
		
		// Sanitize data
		
		// Create new user object
		
		// Insert in db
		
		// Return created object
		return null;
	}
}
