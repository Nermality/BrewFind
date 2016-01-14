package coldcoffee.brewfind.api.Controllers;


import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import coldcoffee.brewfind.api.Objects.BrewFindObject;
import coldcoffee.brewfind.api.Objects.BrewFindQuery;
import coldcoffee.brewfind.api.Objects.BrewFindResponse;
import coldcoffee.brewfind.api.Objects.BrewFindToken;
import coldcoffee.brewfind.api.Objects.Brewery;
import coldcoffee.brewfind.api.Services.BreweryService;
import coldcoffee.brewfind.api.Services.UserService;

//Controller to control and edit brewery objects inside the database.
//add santization to breweries 
@Component
@Path("brewery")
public class BreweryController {

	@Autowired
	private BreweryService breweryService;
	
	@Autowired
	private UserService userService;
	
	Gson gson=new Gson();
	
	/**
	 * Full List endpoint
	 * @return- returns full list of breweries inside the database
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Produces("application/json")
	public BrewFindResponse getList(){
		
		//Retrieve all breweries in the database
		List<? extends BrewFindObject> bList = breweryService.getList();
		
		//check if item was created
		if(bList == null){
			return new BrewFindResponse(10, "brewery list failed to be created");
		}
		
		//return list if successful
		return new BrewFindResponse(0, "OK", (List<BrewFindObject>) bList);
	}
	
	/**
	 * Endpoint used to add a new brewery into the database
	 * Requires a BreweryQuery object that contains a token with admin rights and a brewery object
	 * if brewery already exist or not a admin addBrewery fails
	 * @param json JSON string that contains a BreweryQuery which contains the brewery to be inserted and token
	 * @return on success: Brewery added into database.
	 */
	@PUT
	public BrewFindResponse addBrewery(String json){
		
		//Convert into BreweryQuery
		BrewFindQuery query = gson.fromJson(json, BrewFindQuery.class);
		
		// Null check
		if(query == null){
			return new BrewFindResponse(9, "Query failed, query returned null");
		}
		
		//Check if token was sent
		if(query.getToken() == null) {
			return new BrewFindResponse(4, "No token found");
		}
		
		// Validate token
		BrewFindToken tok = query.getToken();
		if(!userService.checkToken(tok)) {
			return new BrewFindResponse(5, "Invalid token credentials");
		}
		
		//SEND OFF TO SERVICE
		return breweryService.addBreweryFromQuery(query);

	}
	/**
	 * updating brewery information endpoint
	 * Requires a BreweryQuery object that contains a token with admin rights or brewery rights and a brewery object
	 * if brewery does not exist or not an admin/brewery updateBrewery fails
	 * @param json JSON string that contains a BreweryQuery which contains the brewery to be updated and token
	 * @return on success: Brewery is updated 
	 */
	@POST
	@Produces("application/json")
	public BrewFindResponse updateBrewery(String json){
		
		BrewFindQuery query = gson.fromJson(json, BrewFindQuery.class);
		
		// Null check
		if(query == null){
			return new BrewFindResponse(9, "Query failed, query returned null");
		}
	
		//Check if token was sent
		if(query.getToken() == null) {
			return new BrewFindResponse(4, "No token found");
		}
				
		// Validate token
		BrewFindToken tok = query.getToken();
		if(!userService.checkToken(tok)) {
			return new BrewFindResponse(5, "Invalid token credentials");
		}
		
		// TODO: Check to see if the token owner and brewery getting updated match
		
		// SEND OFF TO SERVICE
		return breweryService.updateBreweryFromQuery(query);
	}
	
	
	/**
	 * Removes a user from our system. 
	 * @param body - BrewFindToken object
	 * @return - success is just an {status:0, message:"OK"}
	 */
	@Produces("application/json")
	@Path("/{id}")
	@DELETE
	public BrewFindResponse deleteBrewery(@PathParam("id") int brewNum, String json) {
		
		BrewFindToken token=gson.fromJson(json, BrewFindToken.class);
		
		//Check if token was sent
		if(token == null) {
			return new BrewFindResponse(4, "No token found, authorization failed");
		}
		
		// TODO: CHECK THE GODDAMN TOKEN
		
		//Check if token has proper privileges
		if(token.getAccess() != 3 ){
			return new BrewFindResponse(5, "User found in token does not have required privileges");
		}
		
		// SEND OFF TO SERVICE
		return breweryService.deleteBreweryFromToken(token, brewNum);
	}
	
	/**
	 * Copies information from an 'update' brewery object to their original object
	 * This allows breweries to be updated with all or little information 
	 * @param oldB - the brewerie's original brewery object
	 * @param newB - brewery object containing updates
	 * @return - fully updated, comprehensive brewery object
	 */
	public Brewery safeUpdate(Brewery oldB, Brewery newB) {
		return null;
		
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
