package coldcoffee.brewfind.api.Objects;

import java.util.ArrayList;
import java.util.List;

public class BrewFindResponse {

	// Status 0 = All good
	// Status 1 = User exists in db
	// Status 2 = User not found in db
	// Status 3 = Incorrect pw
	// Status 4 = No token found
	// Status 5 = Token mismatch
	// Status 6 = Password functionality failure
	// Status 7 = Brewery exists in db
	// Status 8 = Brewery not found in db
	// Status 9 = query is null
	// Status 10 = objectis null
	// Describe more statuses here...
	
	public int status;
	public String description;
	public List<BrewFindObject> rObj;
	
	public BrewFindResponse(int s, String d) {
		status = s;
		description = d;
	}
	
	public BrewFindResponse(int s, String d, List<BrewFindObject> r) {
		status = s;
		description = d;
		rObj = r;
	}
	
	public BrewFindResponse(int s, String d, BrewFindObject o) {
		status = s;
		description = d;
		rObj = new ArrayList<BrewFindObject>();
		rObj.add(o);
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<BrewFindObject> getRet() {
		return rObj;
	}
	public void setrObj(List<BrewFindObject> ret) {
		this.rObj = ret;
	}
}
