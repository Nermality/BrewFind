package coldcoffee.brewfind.api.Objects;

import java.util.List;

public class EventQuery {

	public List<Event> list;
	public BrewFindToken token;
	
	public List<Event> getList() {
		return list;
	}
	
	public BrewFindToken getToken() {
		return token;
	}
}
