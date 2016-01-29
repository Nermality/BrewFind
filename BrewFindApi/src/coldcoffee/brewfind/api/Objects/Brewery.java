package coldcoffee.brewfind.api.Objects;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="breweries")
public class Brewery extends BrewFindObject {

		@Id
		public String b_id;
		
		public String b_name;
		
		public String addr1;		
		public String addr2;
		public String city;
		public String state;
		public String zip;
	
		
		public String phone;
		public String email;
		public String url;
		
		//public String b_description;
		
		//public Boolean hasTours;
		//public Boolean hasFood;
		
		public int b_breweryNum;
		//public int b_version;
		//public String b_admin
		
		//public String b_logoImage;
		//public Double b_rating;
		
		//public Set<Drink> b_drinkList;
	
		//facebook
	
		public int getB_breweryNum() {
			return b_breweryNum;
		}

		public void setB_breweryNum(int b_breweryNum) {
			this.b_breweryNum = b_breweryNum;
		}

		public String getB_id() {
			return b_id;
		}

		public String getB_name() {
			return b_name;
		}

		public void setB_name(String b_name) {
			this.b_name = b_name;
		}

		public String getAddr1() {
			return addr1;
		}

		public void setAddr1(String addr1) {
			this.addr1 = addr1;
		}

		public String getAddr2() {
			return addr2;
		}

		public void setAddr2(String addr2) {
			this.addr2 = addr2;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getZip() {
			return zip;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
		
		public Brewery() 
		{}
		
		// On creation of a brewery the brewery can provide the following. 
		//Other information will be entered at another point.
 
		public Brewery(int b_breweryNum){
			this.b_breweryNum = b_breweryNum;
		}
		
		@PersistenceConstructor
		public Brewery(String b_name){
			this.b_name = b_name;
		}
		
		@Override
		public String toString() {
			return "Brewery: [ name = " + b_name 
							 + ", Phone = " + phone 
							 + ", Email = " + email
							 + ", Website = " + url + "]";
		}
		
}
