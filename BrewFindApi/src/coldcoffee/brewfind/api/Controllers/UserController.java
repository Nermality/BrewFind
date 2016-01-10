package coldcoffee.brewfind.api.Controllers;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.bson.types.ObjectId;
import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import coldcoffee.brewfind.api.Objects.BrewFindQuery;
import coldcoffee.brewfind.api.Objects.BrewFindResponse;
import coldcoffee.brewfind.api.Objects.BrewFindToken;
import coldcoffee.brewfind.api.Objects.User;
import coldcoffee.brewfind.api.Services.UserService;

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
		
		BrewFindToken token = gson.fromJson(body, BrewFindToken.class);
		
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
		
		// Convert to query
		BrewFindQuery query = gson.fromJson(body, BrewFindQuery.class);
		
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
		
		BrewFindToken tok = gson.fromJson(body, BrewFindToken.class);
		
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

		BrewFindQuery query = gson.fromJson(body, BrewFindQuery.class);
		
		if(query == null) {
			return new BrewFindResponse(9, "No query found");
		}
		
		if(query.getQList().isEmpty()) {
			return new BrewFindResponse(9, "No user found");
		}
		
		User user = (User) query.getQList().get(0);
		
		return userService.createUser(user);
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
