package coldcoffee.brewfind.Services;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

import coldcoffee.brewfind.Objects.Brewery;
import coldcoffee.brewfind.Objects.EventSummary;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EventService {

	@Autowired
	public BreweryService breweryService;

	private final int minuteRefreshTimer = 5;

	private com.google.api.services.calendar.Calendar CALENDAR;
	private CalendarList eventList;
	private Date lastEventPull;
		
	public EventService() {

		try {
			CALENDAR = GoogleCalendarService.getCalendarService();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		lastEventPull = new Date(System.currentTimeMillis());
	}

	private boolean needsRefresh(){
		long diff = ((new Date().getTime()/60000) - (lastEventPull.getTime()/60000));

		return (diff > minuteRefreshTimer);
	}

	public Map<Integer, List<EventSummary>> getEvents() throws IOException {
		Map<Integer, List<EventSummary>> toRet = new HashMap<>();
		DateTime now = new DateTime(System.currentTimeMillis());

		try {
			if(eventList == null || needsRefresh()) {
				eventList =  CALENDAR.calendarList().list().execute();
			}

			for(CalendarListEntry entry : eventList.getItems()) {

				String name = entry.getSummary();
				if((name.equals("Birthdays")) || name.equals("Holidays in United States") || name.contains("..")) {
					continue;
				}

				Integer num = breweryService.findBrewNum(name);
				if(num == -1){
					//Brewery does not exist in database return -1
				}
				String id = entry.getId();
				List<Event> eList = CALENDAR.events()
						.list(id)
						.setTimeMin(now)
						.execute()
						.getItems();
				List<EventSummary> summ = parseGoogleEvents(eList, name);
				toRet.put(num, summ);
			}
		} catch(Exception e) {
			throw e;
		}

		return toRet;
	}

	private List<EventSummary> parseGoogleEvents(List<Event> events, String brewery) {
		List<EventSummary> toRet = new ArrayList<>();

		for(Event e : events) {
			EventSummary toAdd = new EventSummary(e.getSummary());
			// FUCKING TERNARY OPERATORS DON'T WORK
			String desc = e.getDescription();
			if(desc == null) {
				desc = "No description available";
			}
			toAdd.setDescription(desc);

			if(e.getExtendedProperties() != null) {
				Map<String, String> props = e.getExtendedProperties().getShared();
				for (String s : props.keySet()) {
					switch (s) {
						case "isFamFriendly":
							toAdd.setFamFriendly(Boolean.valueOf(props.get(s)));
							break;
						case "isPetFriendly":
							toAdd.setPetFriendly(Boolean.valueOf(props.get(s)));
							break;
						case "ticketCost":
							toAdd.setTicketCost(Double.valueOf(props.get(s)));
							break;
						case "isOutdoor":
							toAdd.setOutdoor(Boolean.valueOf(props.get(s)));
							break;
						case "atBreweryLocation":
							toAdd.setAtBreweryLocation(Boolean.valueOf(props.get(s)));
						default:
							System.out.println("Unrecognized field " + s);

					}

				}
			}
			String location = e.getLocation();
			if(location == null) {
				location = "TBD";
			}

			DateTime d = e.getStart().getDateTime();
			Date date = new Date ();
			date.setTime(d.getValue());
			java.util.Calendar calendar  = java.util.Calendar.getInstance();
			calendar.setTime(date);
			toAdd.setDay(calendar.get(java.util.Calendar.DAY_OF_MONTH));
			toAdd.setMonth(calendar.get(java.util.Calendar.MONTH));
			toAdd.setYear(calendar.get(java.util.Calendar.YEAR));
			toAdd.setLocation(location);
			toAdd.setBreweryName(brewery);
			toAdd.setHtmlLink(e.getHtmlLink());
			toAdd.setId(e.getId());
			toAdd.setStartDate(e.getStart().getDateTime().toString());
			toAdd.setEndDate(e.getEnd().getDateTime().toString());
			toRet.add(toAdd);

		}

		return toRet;
	}

	public boolean addNewEvent(EventSummary toAdd) {

		Event newEvent = new Event()
				.setSummary(toAdd.getName())
				.setLocation(toAdd.getLocation())
				.setDescription(toAdd.getDescription());

		// 03/12/2016 12:00 AM
		SimpleDateFormat sdf = new SimpleDateFormat();
		Date start = null;
		Date end = null;
		try {
			start = sdf.parse(toAdd.getStartDate());
			end = sdf.parse(toAdd.getEndDate());
		} catch(ParseException e) {
			e.printStackTrace();
		}
		if(start != null) {
			DateTime ds = new DateTime(start);
			EventDateTime eds = new EventDateTime()
					.setDateTime(ds)
					.setTimeZone("America/New_York");
			newEvent.setStart(eds);
		}
		if(end != null) {
			DateTime de = new DateTime(end);
			EventDateTime ede = new EventDateTime()
					.setDateTime(de)
					.setTimeZone("America/New_York");
			newEvent.setEnd(ede);
		}

		Event.ExtendedProperties ext = new Event.ExtendedProperties();
		Map<String, String> props = new HashMap<>();
		props.put("isFamFriendly", toAdd.getFamFriendly().toString());
		props.put("isPetFriendly", toAdd.getPetFriendly().toString());
		props.put("ticketCost", toAdd.getTicketCost().toString());
		props.put("isOutdoor", toAdd.getOutdoor().toString());
		props.put("atBreweryLocation", toAdd.isAtBreweryLocation().toString());
		ext.setShared(props);
		newEvent.setExtendedProperties(ext);
		// recurrence would be cool

		String cId = "";
		try{
			if(eventList == null || needsRefresh()) {
				eventList =  CALENDAR.calendarList().list().execute();
			}
			for(CalendarListEntry e : eventList.getItems()) {
				if(e.getSummary().equals(toAdd.getBreweryName())) {
					cId = e.getId();
				}
			}
			if(cId.equals("")) {
				return false;
			} else {
				CALENDAR.events().insert(cId, newEvent).execute();
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}

		// should it return a list of events? like, a fresh pull?
		// should it return a boolean? idk idk idk
		return true;
	}
	
	// add event
	// remove
	// edit
	// find calendar
	// find event -db
	// find event -calendar
	
	
	
}
