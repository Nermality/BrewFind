package coldcoffee.brewfind.Controllers;

import coldcoffee.brewfind.Objects.EventQuery;
import coldcoffee.brewfind.Objects.EventResponse;
import coldcoffee.brewfind.Objects.EventSummary;
import coldcoffee.brewfind.Services.EventService;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
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

    Gson gson = new Gson();

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

    @POST
    @Produces("application/json")
    public EventResponse addEvent(String body) {

        EventQuery query = null;

        try {
            query = gson.fromJson(body, EventQuery.class);
        } catch(Exception e) {
            e.printStackTrace();
            return new EventResponse(2, "Invalid EventQuery Object", null);
        }

        if(query == null) {
            return new EventResponse(2, "Invalid EventQuery Object", null);
        }

        // CHECK THE TOKEN AUGHHH
        boolean eve;

        try {
            eve = eventService.addNewEvent(query.getEvent());
        } catch(Exception e) {
            e.printStackTrace();
            return new EventResponse(3, "FAILURE", null);
        }

        // Re-work event response - maybe make it an interface
        // SUCKSS UGH LIFE
        if(eve) {
            return new EventResponse(0, "OK", null);
        } else {
            return new EventResponse(4, "Failed to create object...somehow...", null);
        }

    }
}
