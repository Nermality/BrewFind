package coldcoffee.brewfind.api.Objects;

import org.springframework.data.annotation.PersistenceConstructor;

import com.google.api.client.util.DateTime;

public class Event extends BrewFindObject {

	public int e_id;
	
	public String e_name;
	public int e_month;
	
	public int e_eventId;
	public String e_calendarId;
	
	public boolean e_isIndoor;
	public boolean e_isOutdoor;
	public boolean e_hasFood;
	public boolean e_famFriendly;
	public boolean e_petFriendly;
	
	@PersistenceConstructor
	public Event(int e_eventId, String e_calendarId) {
		this.e_eventId = e_eventId;
		this.e_calendarId = e_calendarId;
	}

	public int getE_month() {
		return e_month;
	}

	public void setE_month(int e_month) {
		this.e_month = e_month;
	}

	public String getE_name() {
		return e_name;
	}

	public void setE_name(String e_name) {
		this.e_name = e_name;
	}

	public int getE_eventId() {
		return e_eventId;
	}

	public void setE_eventId(int e_eventId) {
		this.e_eventId = e_eventId;
	}

	public String getE_calendarId() {
		return e_calendarId;
	}

	public void setE_calendarId(String e_calendarId) {
		this.e_calendarId = e_calendarId;
	}

	public boolean isE_isIndoor() {
		return e_isIndoor;
	}

	public void setE_isIndoor(boolean e_isIndoor) {
		this.e_isIndoor = e_isIndoor;
	}

	public boolean isE_isOutdoor() {
		return e_isOutdoor;
	}

	public void setE_isOutdoor(boolean e_isOutdoor) {
		this.e_isOutdoor = e_isOutdoor;
	}

	public boolean isE_hasFood() {
		return e_hasFood;
	}

	public void setE_hasFood(boolean e_hasFood) {
		this.e_hasFood = e_hasFood;
	}

	public boolean isE_famFriendly() {
		return e_famFriendly;
	}

	public void setE_famFriendly(boolean e_famFriendly) {
		this.e_famFriendly = e_famFriendly;
	}

	public boolean isE_petFriendly() {
		return e_petFriendly;
	}

	public void setE_petFriendly(boolean e_petFriendly) {
		this.e_petFriendly = e_petFriendly;
	}

	public int getE_id() {
		return e_id;
	}
}
