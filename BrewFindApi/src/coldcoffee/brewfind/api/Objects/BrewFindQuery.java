package coldcoffee.brewfind.api.Objects;

import java.util.List;

public class BrewFindQuery {
	
	public BrewFindToken token;
	public List<? extends BrewFindObject> qObj;
	
	public BrewFindToken getToken() {
		return token;
	}
	public List<? extends BrewFindObject> getQList() {
		return qObj;
	}
}
