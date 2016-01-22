package coldcoffee.brewfind.api.Controllers;

import java.util.ArrayList;
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

import coldcoffee.brewfind.api.Objects.BrewFindResponse;
import coldcoffee.brewfind.api.Services.EventService;
import coldcoffee.brewfind.api.Services.GoogleCalendarService;

@Component
@Path("/events")
public class EventController {
	
	@Autowired
	public EventService eventService;
	
	@Produces("application/json")
	@GET
	public List<com.google.api.services.calendar.model.Event> testGet() {
		
	
		System.out.println("In test endpoints");
		List<com.google.api.services.calendar.model.Event> events = eventService.getTests().getItems();
		return events;
		
	}
	
}
