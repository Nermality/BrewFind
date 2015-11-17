package com.example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Component;

@Component
@Path("/events")
public class EventsController {

	private TestDataRepository mockData;
	private SimpleDateFormat df;

	public EventsController() {
		mockData = new TestDataRepository();
		df = new SimpleDateFormat("MM/dd/yyyy");
	}
	
	@GET
	@Path("{month}/{year}")
	@Produces("application/json")
	public List<EventsObject> get(
			@PathParam("month")	int month,
			@PathParam("year")	int year,
			@QueryParam("ff")	@DefaultValue("false")	boolean fam,
			@QueryParam("pf")	@DefaultValue("false")	boolean pet) {
		
		ArrayList<EventsObject> toRet = new ArrayList<EventsObject>();
		month--;
		
		Calendar b = Calendar.getInstance();
		b.set(year, month, 1);
		Date start = b.getTime();
		
		Calendar c = Calendar.getInstance();
		c.set(year, month, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date end = c.getTime();
		
		mockData.getEventsList()
			.stream()
			.filter(e -> e.startDate.after(start))
			.filter(e -> e.startDate.before(end))
			.forEach(e -> toRet.add(e));
		
		ArrayList<EventsObject> toDel = new ArrayList<EventsObject>();
		if(fam)
		{
			toRet
				.stream()
				.filter(f -> f.familyFriendly != true)
				.forEach(f -> toDel.add(f));
		}
		if(pet)
		{
			toRet
				.stream()
				.filter(f -> f.petFriendly != true)
				.forEach(f -> toDel.add(f));
		}
		
		if(!toDel.isEmpty())
		{
			toRet.removeAll(toDel);
		}
		
		return toRet;
	}

}
