package coldcoffee.brewfind.api.Controllers;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.stereotype.Component;

import coldcoffee.brewfind.api.Objects.BrewFindResponse;

@Component
@Path("/tap")
public class TapController {
	
	// Takes list of brewNums/versions and returns any updates necessary
	@POST
	public BrewFindResponse update(String body) {
		
		return null;
	}
	
	// Given a brewNum returns taplist for that brewery
	@GET
	@Path("/{brewNum}")
	public BrewFindResponse getTap(@PathParam("brewNum") int brewNum) {
		
		return null;
	}
	
	// Given a brewNum, takes a taplist and replaces the existing one, incrementing version
	@PUT
	@Path("/{brewNum}")
	public BrewFindResponse updateTap(@PathParam("brewNum") int brewNum, String body) {
		
		return null;
	}
	
	// Removes a taplist entry from DB. Only done when brewery is shut down...?
	@DELETE
	@Path("/{brewNum}")
	public BrewFindResponse removeTap(@PathParam("brewNum") int brewNum, String body) {
		
		return null;
	}
	
	

}
