package coldcoffee.brewfind.api.Objects;

import java.util.List;

public class VersionQuery {

	public List<Version> list;
	public BrewFindToken token;
	
	public List<Version> getList() {
		return list;
	}
	
	public BrewFindToken getToken() {
		return token;
	}
}
