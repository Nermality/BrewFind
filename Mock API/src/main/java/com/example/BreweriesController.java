package com.example;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Component;

@Component
@Path("/breweries")
public class BreweriesController {
	
	private TestDataRepository mockData;
		
	// filter by
		// rating
		// distance
		// hasGrowler
		// hasTours
		// hasFood
		// hasTapRoom
	private BreweriesController() {
		 mockData = new TestDataRepository();
	}
	
	@GET
	@Produces("application/json")
	public List<BreweriesObject> get(
			@QueryParam("r")	@DefaultValue("0.0")	double rating,
			@QueryParam("d")	@DefaultValue("0.0")	double distance,
			@QueryParam("hG")	@DefaultValue("false")  boolean hasGrowler,
			@QueryParam("hT")	@DefaultValue("false")	boolean hasTour,
			@QueryParam("hF")	@DefaultValue("false")	boolean hasFood,
			@QueryParam("hTR")	@DefaultValue("false")	boolean hasTapRoom) {
		ArrayList<BreweriesObject> toRet = new ArrayList<BreweriesObject>();
		mockData.getBreweriesList()
					.stream()
					.filter(b -> b.rating >= rating)
					.forEach(b -> toRet.add(b));
		
		ArrayList<BreweriesObject> toDel = new ArrayList<BreweriesObject>();
		if(hasGrowler)
		{
			toRet
				.stream()
				.filter(g -> g.hasGrowler != true)
				.forEach(g -> toDel.add(g));
		}
		if(hasTour)
		{
			toRet
				.stream()
				.filter(t -> t.hasTour != true)
				.forEach(t -> toDel.add(t));
		}
		if(hasFood)
		{
			toRet
				.stream()
				.filter(t -> t.hasFood != true)
				.forEach(t -> toDel.add(t));
		}
		if(hasTapRoom)
		{
			toRet
				.stream()
				.filter(t -> t.hasTRoom != true)
				.forEach(t -> toDel.add(t));
		}
		if(!toDel.isEmpty())
		{
			toRet.removeAll(toDel);
		}
		
		
		return toRet;
	}

}
