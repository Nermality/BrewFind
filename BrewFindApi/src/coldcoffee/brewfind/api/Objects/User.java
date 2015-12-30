package coldcoffee.brewfind.api.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users")
public class User extends BrewFindObject {
	
	@Id
	public String u_id;
	
	public String u_name;
	public String u_pass;
	
	public String u_firstName;
	public String u_lastName;
	
	// 1 = User
	// 2 = Brewery
	// 3+ = Admin
	public Integer u_access;
	
	public BrewFindToken u_curToken;
	
	public User() 
	{}
	
	public String getU_id() {
		return this.u_id;
	}
	
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
	
	public void setU_access(int u_access) {
		this.u_access = u_access;
	}
	
	public int getU_access() {
		return u_access;
	}
	
	@PersistenceConstructor
	public User(String u_name, Integer u_access) {
		this.u_name = u_name;
		this.u_access = u_access;
	}
	
	@Override
	public String toString() {
		return "User: [ uname = " + u_name 
					+ ", pass = " + u_pass 
					+ ", firstName = " + u_firstName 
					+ ", lastName = " + u_lastName + "]";
	}

}
