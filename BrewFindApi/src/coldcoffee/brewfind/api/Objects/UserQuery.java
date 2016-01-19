package coldcoffee.brewfind.api.Objects;

import java.util.List;

public class UserQuery {

	public List<User> list;
	public BrewFindToken token;
	
	public List<User> getList() {
		return list;
	}
	
	public BrewFindToken getToken() {
		return token;
	}
}
