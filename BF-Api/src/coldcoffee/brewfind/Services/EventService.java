package coldcoffee.brewfind.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coldcoffee.brewfind.Objects.EventSummary;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import org.springframework.stereotype.Service;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;

@Service
public class EventService {
	
	private Calendar CALENDAR;
		
	public EventService() {

		try {
			CALENDAR = GoogleCalendarService.getCalendarService();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Map<String, List<EventSummary>> getEvents() throws IOException {
		Map<String, List<EventSummary>> toRet = new HashMap<>();
		DateTime now = new DateTime(System.currentTimeMillis());
		try {
			CalendarList cList =  CALENDAR.calendarList().list().execute();
			for(CalendarListEntry entry : cList.getItems()) {

				String name = entry.getSummary();
				if((name.equals("Birthdays")) || name.equals("Holidays in United States")) {
					continue;
				}

				String id = entry.getId();
				List<Event> eList = CALENDAR.events()
						.list(id)
						.setTimeMin(now)
						.execute()
						.getItems();
				List<EventSummary> summ = parseGoogleEvents(eList);
				toRet.put(name, summ);
			}
		} catch(Exception e) {
			throw e;
		}

		return toRet;
	}

	public List<EventSummary> parseGoogleEvents(List<Event> events) {
		List<EventSummary> toRet = new ArrayList<>();

		for(Event e : events) {
			EventSummary toAdd = new EventSummary(e.getSummary());
			// FUCKING TERNARY OPERATORS DON'T WORK
			String desc = e.getDescription();
			if(desc == null) {
				desc = "No description available";
			}
			toAdd.setDescription(desc);
			toAdd.setHtmlLink(e.getHtmlLink());
			toAdd.setId(e.getId());
			toRet.add(toAdd);
		}

		return toRet;
	}
	
	// add event
	// remove
	// edit
	// find calendar
	// find event -db
	// find event -calendar
	
	
	
}
