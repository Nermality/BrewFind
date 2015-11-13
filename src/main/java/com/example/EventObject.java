package com.example;

import java.util.Date;
import java.util.List;

public class EventObject {

	public int eventId;
	
	public String name;
	public String townName;
	
	//Should this be a brewery object? Mayhaps?
	public String hostedBy;
	public Date startDate;
	public String startTime;
	public Date endDate;
	public String endTime;
	public Double distance;
	public Double price;
	
	public float xcoord;
	public float ycoord;
	
	public boolean isDogFriendly;
	public boolean isFamFriendly;
	
	public List<String> musicalGuests;
	public List<BreweriesObject> brewersThere;
}
