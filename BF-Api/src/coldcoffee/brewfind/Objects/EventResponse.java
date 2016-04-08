package coldcoffee.brewfind.Objects;

import com.google.api.services.calendar.model.*;
import com.google.api.services.calendar.model.Event;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 2/23/2016.
 */
public class EventResponse {

    public int status;
    public String message;
    public Map<Integer, List<EventSummary>> eventMap;

    public EventResponse(int s, String m, Map<Integer, List<EventSummary>> map) {
        status = s;
        message = m;
        eventMap = map;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<Integer, List<EventSummary>> getEventMap() {
        return eventMap;
    }

    public void setEventMap(Map<Integer, List<EventSummary>> eventMap) {
        this.eventMap = eventMap;
    }
}
