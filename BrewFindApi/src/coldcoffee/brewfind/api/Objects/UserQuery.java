package coldcoffee.brewfind.api.Objects;

public class UserQuery {
	
	public BrewFindToken token;
	public User user;
	
	public BrewFindToken getToken() {
		return token;
	}
	public void setToken(BrewFindToken token) {
		this.token = token;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
