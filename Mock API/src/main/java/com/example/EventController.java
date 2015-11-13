package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

@Component
@Path("/event/{id}")
public class EventController {
	
	private TestDataRepository mockData;
	
	public EventController() {
		mockData = new TestDataRepository();
	}
	
	@GET
	@Produces("application/json")
	public EventObject get(@PathParam("id") int id) {		
		return mockData.getEventObj(id);
	}

}
