package coldcoffee.brewfind.api.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import coldcoffee.brewfind.api.Objects.BrewFindToken.UserType;

@Document(collection="users")
public class User {
	
	@Id
	public long u_id;
	
	public String u_name;
	public String u_pass;
	
	public String u_firstName;
	public String u_lastName;
	
	public UserType u_access;
	public BrewFindToken u_curToken;
	
	public User() 
	{}

	public String getU_name() {
		return u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

	public String getU_pass() {
		return u_pass;
	}

	public void setU_pass(String u_pass) {
		this.u_pass = u_pass;
	}

	public String getU_firstName() {
		return u_firstName;
	}

	public void setU_firstName(String u_firstName) {
		this.u_firstName = u_firstName;
	}

	public String getU_lastName() {
		return u_lastName;
	}

	public void setU_lastName(String u_lastName) {
		this.u_lastName = u_lastName;
	}

	public void setU_curToken(BrewFindToken u_curToken) {
		this.u_curToken = u_curToken;
	}
	
	public BrewFindToken getU_curToken() {
		return u_curToken;
	}
	
	public void setU_access(UserType type) {
		this.u_access = type;
	}
	
	public UserType getU_access() {
		return u_access;
	}
	
	@PersistenceConstructor
	public User(long id, String u_name, String u_pass, String u_access) {
		super();
		this.u_id = id;
		this.u_name = u_name;
		this.u_pass = u_pass;
		this.u_access = UserType.valueOf(u_access);
	}
	
	@Override
	public String toString() {
		return "User: [ uname = " + u_name 
					+ ", pass = " + u_pass 
					+ ", firstName = " + u_firstName 
					+ ", lastName = " + u_lastName + "]";
	}

}
