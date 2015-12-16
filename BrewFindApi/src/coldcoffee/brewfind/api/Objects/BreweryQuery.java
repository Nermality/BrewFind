package coldcoffee.brewfind.api.Objects;

import java.util.Set;

public class BreweryQuery {

	public BrewFindToken token;
	public Brewery brew;
	
	public BrewFindToken getToken() {
		return token;
	}
	public void setToken(BrewFindToken token) {
		this.token = token;
	}
	public Brewery getBrewery() {
		return brew;
	}
	public void setBrewery(Brewery brew) {
		this.brew = brew;
	}

	
}
