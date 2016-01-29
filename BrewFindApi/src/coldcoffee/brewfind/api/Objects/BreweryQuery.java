package coldcoffee.brewfind.api.Objects;

import java.util.List;

public class BreweryQuery {

	public List<Brewery> list;
	public BrewFindToken token;
	
	public List<Brewery> getList() {
		return list;
	}
	
	public BrewFindToken getToken() {
		return token;
	}
}
