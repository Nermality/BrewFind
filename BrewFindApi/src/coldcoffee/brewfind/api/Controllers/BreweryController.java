package coldcoffee.brewfind.api.Controllers;


import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
		
		//Check if token has proper privileges
		if(tok.getAccess() < 3) {
			return new BrewFindResponse(11, "User must be admin to add brewery");
		}
		
		//Retrieve Brewery out of Brewery Query
		Brewery newBrew = (Brewery) query.getQList().get(0); 
		
		// Null check
		if(newBrew == null){
			return new BrewFindResponse(10, "brewery failed to be created");
		}
		
		//Check if brewery is in database
		if(breweryService.findBrewery(newBrew.b_name) != null) {
			return new BrewFindResponse(7, "Brewery found in system, insert failed");
		}
		
		else {
			// newBrew.setB_version(0);
			
			// find current brew number
			int curMax = breweryService.getCurBrewMax();
			if(curMax == 0) {
				return new BrewFindResponse(12, "Database failure");
			}
			curMax++;
			if(breweryService.findBrewery(curMax) != null) {
				// TODO: Put this in the service and shit
			}
			newBrew.setB_brewNum(curMax);
			
			// add brewery to database
			newBrew = breweryService.saveBrewery(newBrew);
			
			//return success message
			return new BrewFindResponse(0, "OK", newBrew);
		}
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
				
		//Check if token has proper privileges
		if(tok.getAccess() < 2) {
			return new BrewFindResponse(11, "User must be Brewer to edit brewery");
		}
		
		// TODO: Check to see if the token owner and brewery getting updated match
		
		//Retrieve Brewery out of Brewery Query
		@SuppressWarnings("unchecked")
		Brewery brewery = (Brewery)query.getQList().get(0); 
		
		if(brewery == null){
			return new BrewFindResponse(10, "brewery failed to be created");
		}
		
		 Brewery oldb = breweryService.findBrewery(brewery.getB_name());
		 
		//Check if brewery is in database
		if(oldb == null) {
			return new BrewFindResponse(8, "not found in system, insert failed");
		}
		else{
			//newBrew.setB_version(oldb.getB_version()+1);
			
			// TODO: SAFE UPDATE
			//update brewery in database
			breweryService.saveBrewery(brewery);
			
			//return success message
			return new BrewFindResponse(0, "Ok", brewery);
		}
	}
	
	
	/**
	 * Removes a user from our system. 
	 * @param body - BrewFindToken object
	 * @return - success is just an {status:0, message:"OK"}
	 */
	@Produces("application/json")
	@DELETE
	public BrewFindResponse deleteBrewery(String json) {
		
		BrewFindQuery query=gson.fromJson(json, BrewFindQuery.class);
		
		if(query == null){
			return new BrewFindResponse(9, "Query failed, query returned null");
		}
		
		//Check if token was sent
		if(query.getToken() == null) {
			return new BrewFindResponse(4, "No token found, authorization failed");
		}
		
		//Check if token has proper privileges
		if(query.getToken().access != 3 ){
			return new BrewFindResponse(5, "User found in token does not have required privileges");
		}
		
		//Retrieve Brewery out of Brewery Query
		@SuppressWarnings("unchecked")
		Brewery remBrew = (Brewery) query.getQList().get(0); 
		
		if(remBrew == null){
				return new BrewFindResponse(10, "No brewery object found");
			}
		
		Brewery del = breweryService.findBrewery(remBrew.getB_brewNum());

		//Check if brewery is in database
		if(del == null) {
			return new BrewFindResponse(7, "Brewery not found in system, delete failed");
		}
		
		// remove information from brewery db
		breweryService.deleteBrewery(del);
				
		return new BrewFindResponse(0, "OK");
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
