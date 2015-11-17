package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

@Component
@Path("/brewery/{id}")
public class BreweryController {

	private TestDataRepository mockData;

	public BreweryController(){	
		 mockData = new TestDataRepository();			
	}

	@GET
	@Produces("application/json")
	public BreweryObject get(@PathParam("id") int id) {
		return mockData.getBreweryObj(id);
	}
}
