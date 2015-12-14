package coldcoffee.brewfind.api.Objects;

import java.util.Date;

public class BrewFindToken {
	
	public enum UserType {
		USER,
		BREWERY, 
		ADMIN
	}
	
	public BrewFindToken(String tok) {
		String atype = String.valueOf(tok.toLowerCase().charAt(0));
		switch(atype) {
		
			case "a": 
				access = UserType.ADMIN;
				break;
			case "b":
				access = UserType.BREWERY;
				break;
			case "u":
				access = UserType.USER;
				break;
				
			default: access = UserType.USER;
		}
		
		token = tok.substring(1);
	}
	
	public BrewFindToken(UserType type, String tok) {
		access = type;
		token = tok;
		stamp = new Date().getTime();
	}
	
	public UserType access;
	public String token;
	public long stamp;
}
