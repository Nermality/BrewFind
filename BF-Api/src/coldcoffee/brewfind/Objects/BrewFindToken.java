package coldcoffee.brewfind.Objects;

import java.util.Date;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BrewFindToken extends BrewFindObject {
	
	// 1 = User
	// 2 = Brewery
	// 3+ = Admin
	public int access;
	
	// Username of token owner encoded in Base64
	public String token;
	
	// Timestamp
	public long stamp;
	
	public int getAccess() {
		return access;
	}

	public void setAccess(int access) {
		this.access = access;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getStamp() {
		return stamp;
	}

	public void setStamp(long stamp) {
		this.stamp = stamp;
	}

	// Fresh, java-side constructor (creating a timestamp)
	public BrewFindToken(int access, String token) {
		this.access = access;
		this.token = token;
		this.stamp = new Date().getTime();
	}
	
	// Constructor for POJO creation from MongoDB
	@PersistenceConstructor
	public BrewFindToken(int access, String token, long stamp) {
		this.access = access;
		this.token = token;
		this.stamp = stamp;
	}
	
	
	
}
