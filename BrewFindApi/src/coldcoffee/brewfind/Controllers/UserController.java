package coldcoffee.brewfind.Controllers;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import coldcoffee.brewfind.Objects.BrewFindObject;
import coldcoffee.brewfind.Objects.BrewFindResponse;
import coldcoffee.brewfind.Objects.BrewFindToken;
import coldcoffee.brewfind.Objects.User;
import coldcoffee.brewfind.Objects.UserQuery;
import coldcoffee.brewfind.Services.UserService;

// Controller to handle user...stuff
// Authentication, token handling, deleting, updating, etc. 

@Component
@Path("/user")
public class UserController {

	// UserService used for all database interactions
	@Autowired
	private UserService userService;
	
	private Gson gson = new Gson();

	/**
	 * Authentication endpoint
	 * @param uname - header parameter containing username
	 * @param pass 	- header parameter containing password
	 * @return - if successful, a BrewFindToken; otherwise errors. 
	 */
	@Path("/auth")
	@Produces("application/json")
	@GET
	public BrewFindResponse auth(
			@NotNull @HeaderParam("uname") 	String uname,
			@NotNull @HeaderParam("pass")	String pass) {
				
		// Sanitize data
		String newu = sanitize(uname);
		
		// Test if user is in db
		User u = userService.findUser(newu);
		if(u == null) {
			return new BrewFindResponse(2, "User " + newu + " not found in DB");
		}
		
		return userService.authUser(newu, pass, u);
	}
	
	/**
	 * Returns user-safe information on the user
	 * @param body - requires a legitimate user token to do anything
	 * @return - the user's information
	 */
	@Produces("application/json")
	@GET
	public BrewFindResponse getUser(String body) {
		
		BrewFindToken token = null;
		
		try {
			token = gson.fromJson(body, BrewFindToken.class);
		} catch( JsonSyntaxException e ) {
			return new BrewFindResponse(15, "Invalid object sent");
		}
		
		// Null check
		if(token == null) {
			return new BrewFindResponse(4, "No token found");
		}
		
		return userService.getUserFromToken(token);
	}
	
	/**
	 * Updates the user's information
	 * The only fields required (other than those to change) are username and access
	 * Username, id, and access cannot be changed
	 * @param body - the user object containing updated information
	 * @return user-safe information on the new user created.
	 */
	@Produces("application/json")
	@POST
	public BrewFindResponse updateUser(String body) {
		
		// TODO: Create logic that verifies a brewery user as legitimate or not!!
		UserQuery query = null;
		// Convert to query
		try {
			query = gson.fromJson(body, UserQuery.class);
		} catch (JsonSyntaxException e) {
			return new BrewFindResponse(15, "Invalid object sent");
		}
		
		if(query == null) {
			return new BrewFindResponse(9, "No query found");
		}
		
		// No token - fail
		if(query.getToken() == null) {
			return new BrewFindResponse(4, "No token found, authorization failed");
		}
		
		return userService.updateUserFromQuery(query);
	}
	
	/**
	 * Removes a user from our system. 
	 * @param body - BrewFindToken object
	 * @return - success is just an {status:0, message:"OK"}
	 */
	@Produces("application/json")
	@DELETE
	public BrewFindResponse deleteUser(String body) {
		
		BrewFindToken tok = null;
		
		try {
			tok = gson.fromJson(body, BrewFindToken.class);
		} catch (JsonSyntaxException e) {
			return new BrewFindResponse(15, "Invalid object sent");
		}
		
		// No token - fail
		if(tok == null) {
			return new BrewFindResponse(4, "No token found, authorization failed");
		}
		
		return userService.deleteUserFromToken(tok);
	}
	
	/**
	 * Creates a new user and incorporates it into our system.
	 * This is where the salt creation and hashing takes place.
	 * @param body - The new user object to be created
	 * @return - on success: user-safe information on the user just created. 
	 */
	@Produces("application/json")
	@PUT
	public BrewFindResponse addUser(String body) {

		UserQuery query = null;
		
		try {
			query = gson.fromJson(body, UserQuery.class);
		} catch (JsonSyntaxException e) {
			return new BrewFindResponse(15, "Invalid object sent");
		}
		
		if(query == null) {
			return new BrewFindResponse(9, "No query found");
		}
		
		if(query.getList().isEmpty()) {
			return new BrewFindResponse(9, "No user found");
		}
		
		List<? extends BrewFindObject> user = query.getList();
		User u = (User) user.get(0);
		
		return userService.createUser(u);
	}
	
	/**
	 * An attempt at sanitizing data for MongoDB...
	 * @param s - query string to sanitize
	 * @return - string with all malice removed
	 */
	public String sanitize(String s) {
		
		s = s.trim();
		if(s.startsWith("$")) {
			s = s.substring(1);
		}
		
		return s;
	}

}
