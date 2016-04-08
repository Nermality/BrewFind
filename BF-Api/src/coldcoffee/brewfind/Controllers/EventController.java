package coldcoffee.brewfind.Controllers;

import coldcoffee.brewfind.Objects.EventResponse;
import coldcoffee.brewfind.Objects.EventSummary;
import coldcoffee.brewfind.Services.EventService;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2/23/2016.
 */
@Component
@Path("event")
public class EventController {

    @Autowired
    EventService eventService;

    Gson gson;

    @GET
    @Produces("application/json")
    public EventResponse getEvents() {

        Map<Integer, List<EventSummary>> toRet;

        try {
            toRet = eventService.getEvents();
        } catch (Exception e) {
            e.printStackTrace();
            return new EventResponse(1, "FAILURE", null);
        }

        return new EventResponse(0, "OK", toRet);
    }
}
