package coldcoffee.brewfind.api.Services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

@Service
public class EventService {
	
	private Calendar CALENDAR;
		
	public EventService() {
	/*
		try {
			CALENDAR = GoogleCalendarService.getCalendarService();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/
	}
	
	public Events getTests(){
		try {
			return CALENDAR.events().list(" u8s6deaslpugrv0kk3fkgdi7tk@group.calendar.google.com").setMaxResults(10).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	// add event
	// remove
	// edit
	// find calendar
	// find event -db
	// find event -calendar
	
	
	
}
