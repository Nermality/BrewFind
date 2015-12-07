package coldcoffee.brewfind.api.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users")
public class User {
	
	@Id
	public int u_id;
	
	public String u_name;
	public String u_pass;
	
	public String u_firstName;
	public String u_lastName;
	
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

	@PersistenceConstructor
	public User(String u_name, String u_pass) {
		super();
		this.u_name = u_name;
		this.u_pass = u_pass;
	}
	
	@Override
	public String toString() {
		return "User: [ uname = " + u_name 
					+ ", pass = " + u_pass 
					+ ", firstName = " + u_firstName 
					+ ", lastName = " + u_lastName + "]";
	}

}
