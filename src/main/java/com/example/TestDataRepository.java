package com.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestDataRepository {
	
	public static EventsObject mockEventsA;
	public static EventsObject mockEventsB;
	public static List<EventsObject> mockEventsList;
	
	public static EventObject mockEventA;
	public static EventObject mockEventB;
	public static List<EventObject> mockEventList;
	
	public static BreweriesObject mockBreweriesA;
	public static BreweriesObject mockBreweriesB;
	public static BreweriesObject mockBreweriesC;
	public static List<BreweriesObject> mockBreweriesList;
	
	public static BreweryObject mockBreweryA;
	public static BreweryObject mockBreweryB;
	public static List<BreweryObject> mockBreweryList;
	
	public static Drink mockLTDrinkA;
	public static Drink mockLTDrinkB;
	public static Drink mockMHDrinkA;
	public static Drink mockMHDrinkB;
	public static List<Drink> mockLTDrinkList;
	public static List<Drink> mockMHDrinkList;
	
	public static List<String> mockMusicGuests;
	
	public TestDataRepository() {
		
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
		
		mockBreweriesA = new BreweriesObject();
			mockBreweriesA.breweryId = 1;
			mockBreweriesA.name = "Long Trail Brewing Co.";
			mockBreweriesA.rating = 4.0;
			mockBreweriesA.townName = "Bridgewater Corners";
			mockBreweriesA.addr = "5520 US Rte. 4";
			mockBreweriesA.zip = "05035";
			mockBreweriesA.hasFood = true;
			mockBreweriesA.hasGrowler = false;
			mockBreweriesA.hasTour = true;
			mockBreweriesA.hasTRoom = true;
			
		mockBreweriesB = new BreweriesObject();
			mockBreweriesB.breweryId = 2;
			mockBreweriesB.name = "Magic Hat Brewing Co.";
			mockBreweriesB.rating = 3.5;
			mockBreweriesB.townName = "South Burlington";
			mockBreweriesB.addr = "5 Bartlett Bay Road";
			mockBreweriesB.zip = "05403";
			mockBreweriesB.hasFood = false;
			mockBreweriesB.hasGrowler = true;
			mockBreweriesB.hasTour = true;
			mockBreweriesB.hasTRoom = false;
		
		mockBreweriesList = new ArrayList<BreweriesObject>();
			mockBreweriesList.add(mockBreweriesA);
			mockBreweriesList.add(mockBreweriesB);
			
		mockLTDrinkA = new Drink();
			mockLTDrinkA.name = "Long Trail Ale";
			mockLTDrinkA.rating = 3.4;
			mockLTDrinkA.type = "Ale";
			
		mockLTDrinkB = new Drink();
			mockLTDrinkA.name = "Blackbeary Wheat";
			mockLTDrinkA.rating = 4.2;
			mockLTDrinkA.type = "fukken beer";
			
		mockLTDrinkList = new ArrayList<Drink>();
			mockLTDrinkList.add(mockLTDrinkA);
			mockLTDrinkList.add(mockLTDrinkB);
			
		mockMHDrinkA = new Drink();
			mockMHDrinkA.name = "Electric Peel";
			mockMHDrinkA.rating = 4.7;
			mockMHDrinkA.type = "IPA";
			
		mockMHDrinkB = new Drink();
			mockMHDrinkB.name = "#9";
			mockMHDrinkB.rating = 3.6;
			mockMHDrinkB.type = "IPA";
			
		mockMHDrinkList = new ArrayList<Drink>();
			mockMHDrinkList.add(mockMHDrinkA);
			mockMHDrinkList.add(mockMHDrinkB);
			
		mockBreweryA = new BreweryObject();
			mockBreweryA.breweryId = 1;
			mockBreweryA.name = "Long Trail Brewing Co.";
			mockBreweryA.town = "Bridgewater Corners";
			mockBreweryA.description = "A classic vermont brewery durka durka mohammed jihad";
			mockBreweryA.rating = 4.0;
			mockBreweryA.dateEstablished = 1792;
			mockBreweryA.xcoord = 1.2f;
			mockBreweryA.ycoord = 2.4f;
			mockBreweryA.tapList = new ArrayList<Drink>();
				mockBreweryA.tapList.add(mockLTDrinkA);
			mockBreweryA.fullList = new ArrayList<Drink>();
				mockBreweryA.fullList.add(mockLTDrinkA);
				mockBreweryA.fullList.add(mockLTDrinkB);		
				
		mockBreweryB = new BreweryObject();
			mockBreweryB.breweryId = 2;
			mockBreweryB.name = "Magic Hat Brewing Co.";
			mockBreweryB.town = "South Burlington";
			mockBreweryB.description = "With their magic hats they make some fine ass beer shnarf shnarf";
			mockBreweryB.rating = 3.5;
			mockBreweryB.dateEstablished = 1994;
			mockBreweryB.xcoord = 4732.2f;
			mockBreweryB.ycoord = 234.1f;
			mockBreweryB.tapList = new ArrayList<Drink>();
				mockBreweryB.tapList.add(mockMHDrinkA);
			mockBreweryB.fullList = new ArrayList<Drink>();
				mockBreweryB.fullList.add(mockMHDrinkA);
				mockBreweryB.fullList.add(mockMHDrinkB);
				
		mockBreweryList = new ArrayList<BreweryObject>();
			mockBreweryList.add(mockBreweryA);
			mockBreweryList.add(mockBreweryB);
							
		mockEventsA = new EventsObject();
			mockEventsA.eventId = 1;
			mockEventsA.name = "Magic Trail Gathering";
			try {
				mockEventsA.startDate = df.parse("11/07/2015");
			} catch (ParseException e2) {
				e2.printStackTrace();
			}
			mockEventsA.startTime = "18:00:00";
			mockEventsA.distance = 6.0;
			mockEventsA.hostedBy = "Magic Hat Brewing Co.";
			mockEventsA.townName = "Burlington";
			mockEventsA.petFriendly = true;
			mockEventsA.familyFriendly = false;
		
		mockEventsB = new EventsObject();
			mockEventsB.eventId = 2;
			mockEventsB.name = "RampageFest 2015";
			try {
				mockEventsB.startDate = df.parse("02/17/2016");
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			mockEventsB.startTime = "22:30:00";
			mockEventsB.distance = 14.2;
			mockEventsB.hostedBy = "Alchemist Brewing Co.";
			mockEventsB.townName = "Bloodsport";
			mockEventsB.petFriendly = false;
			mockEventsB.familyFriendly = true;
		
		mockMusicGuests = new ArrayList<String>();
		mockMusicGuests.add("Slayer");
		mockMusicGuests.add("Mastodon");
		mockMusicGuests.add("Wax Tailor");
		
		mockEventA = new EventObject();
			mockEventA.eventId = 1;
			mockEventA.name = "Magic Trail Gathering";
			try {
				mockEventA.startDate = df.parse("11/07/2015");
				mockEventA.endDate = df.parse("11/07/2015");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			mockEventA.startTime = "18:00:00";
			mockEventA.endTime = "23:00:00";
			mockEventA.price = 18.99;
			mockEventA.townName = "Burlington";
			mockEventA.hostedBy = "Magic Hat Brewing Co.";
			mockEventA.distance = 13.2;
			mockEventA.xcoord = 5.2f;
			mockEventA.ycoord = 98.5f;
			mockEventA.brewersThere = new ArrayList<BreweriesObject>();
				mockEventA.brewersThere.add(mockBreweriesA);
				mockEventA.brewersThere.add(mockBreweriesB);
			mockEventA.musicalGuests = new ArrayList<String>();
				mockEventA.musicalGuests = mockMusicGuests;		
				
		mockEventList = new ArrayList<EventObject>();
			mockEventList.add(mockEventA);
			
		mockEventsList = new ArrayList<EventsObject>();
			mockEventsList.add(mockEventsA);
			mockEventsList.add(mockEventsB);
	}
	
	public List<BreweriesObject> getBreweriesList() {
		return mockBreweriesList;
	}
	
	public List<BreweryObject> getBreweryList() {
		return mockBreweryList;
	}
	
	public List<EventsObject> getEventsList() {
		return mockEventsList;
	}
	
	public List<EventObject> getEventList() {
		return mockEventList;
	}
	
	public BreweryObject getBreweryObj(int id) {
		Optional<BreweryObject> toRet = mockBreweryList
								.stream()
								.filter(b -> b.breweryId == id)
								.findFirst();
		return toRet.get();
	}
	
	public EventObject getEventObj(int id) {
		Optional<EventObject> toRet = mockEventList
										.stream()
										.filter(e -> e.eventId == id)
										.findFirst();
		return toRet.get();
	}
	
	
}
