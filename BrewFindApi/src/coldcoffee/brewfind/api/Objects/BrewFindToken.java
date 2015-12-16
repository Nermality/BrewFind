package coldcoffee.brewfind.api.Objects;

import java.util.Date;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BrewFindToken {
	
	public int access;
	public String token;
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

	public BrewFindToken(int access, String token) {
		this.access = access;
		this.token = token;
		this.stamp = new Date().getTime();
	}
	
	@PersistenceConstructor
	public BrewFindToken(int access, String token, long stamp) {
		this.access = access;
		this.token = token;
		this.stamp = stamp;
	}
	
	
	
}
