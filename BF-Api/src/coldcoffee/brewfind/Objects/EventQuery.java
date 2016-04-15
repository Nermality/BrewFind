package coldcoffee.brewfind.Objects;

/**
 * Created by user on 4/12/2016.
 */
public class EventQuery {

    public BrewFindToken token;
    public EventSummary event;

    public BrewFindToken getToken() {
        return token;
    }

    public void setToken(BrewFindToken token) {
        this.token = token;
    }

    public EventSummary getEvent() {
        return event;
    }

    public void setEvent(EventSummary event) {
        this.event = event;
    }
}
