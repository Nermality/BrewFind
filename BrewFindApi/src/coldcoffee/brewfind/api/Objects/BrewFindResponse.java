package coldcoffee.brewfind.api.Objects;

public class BrewFindResponse {

	// Status 0 = All good
	// Status 1 = User exists in db
	// Status 2 = User not found in db
	// Status 3 = Incorrect pw
	// Status 4 = No token found
	// Status 5 = Token mismatch
	// Describe more statuses here...
	
	public int status;
	public String description;
	public Object rObj = null;
	
	public BrewFindResponse(int s, String d) {
		status = s;
		description = d;
	}
	
	public BrewFindResponse(int s, String d, Object o) {
		status = s;
		description = d;
		rObj = o;
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
	public Object getrObj() {
		return rObj;
	}
	public void setrObj(Object rObj) {
		this.rObj = rObj;
	}
}
