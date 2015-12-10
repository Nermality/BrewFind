package coldcoffee.brewfind.api.Objects;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="brewery")
public class Brewery {

		@Id
		public int id;
		
		public String b_description;
	
		public String b_name;
		public String b_street;
		public String b_city;
		public String b_state;
		public String b_zip;
		
		public String b_phone;
		public String b_email;
		public String b_url;
		
		public Boolean hasTours;
		public Boolean hasFood;
		
		public String b_logoImage;
		public Double b_rating;
		
		public Set<Drink> b_tapList;
		public Set<Drink> b_drinkList;
		
		public Set<Event> b_eventList;	
		
		public Boolean b_active=false;
		//facebook
		
		public Brewery() 
		{}

		public String getB_name() {
			return b_name;
		}

		public void setB_name(String b_name) {
			this.b_name = b_name;
		}

		public String getB_street() {
			return b_street;
		}
		
		public void setB_street(String b_street) {
			this.b_street = b_street;
		}
		
		public String getB_city() {
			return b_city;
		}

		public void setB_city(String b_city) {
			this.b_city = b_city;
		}
		public String getB_state() {
			return b_state;
		}
		
		public void setB_state(String b_state) {
			this.b_state = b_state;
		}
		
		public String getB_zip() {
			return b_zip;
		}
		
		public void setB_zip(String b_zip) {
			this.b_zip = b_zip;
		}
		
		public String getB_phone() {
			return b_phone;
		}
		
		public void setB_phone(String b_phone) {
			this.b_phone = b_phone;
		}
		
		public String getB_email() {
			return b_email;
		}

		public void setB_email(String b_email) {
			this.b_email = b_email;
		}
		
		public String getB_url() {
			return b_url;
		}

		public void setB_url(String b_url) {
			this.b_url = b_url;
		}
		
		public Boolean getHasTours() {
			return hasTours;
		}

		public void setHasTours(Boolean hasTours) {
			this.hasTours = hasTours;
		}
		
		public Boolean getHasFood() {
			return hasFood;
		}

		public void setHasFood(Boolean hasFood) {
			this.hasFood = hasFood;
		}
		
		public String getB_logoImage() {
			return b_logoImage;
		}

		public void setB_logoImage(String b_logoImage) {
			this.b_logoImage = b_logoImage;
		}
		
		public double getB_rating() {
			return b_rating;
		}

		public void setB_rating(double b_rating) {
			this.b_rating = b_rating;
		}
		
		public Set<Drink> getB_tapList() {
			return b_tapList;
		}

		public void setB_tapList(Set<Drink> b_tapList) {
			this.b_tapList = b_tapList;
		}
		
		public Set<Drink> getB_drinkList() {
			return b_drinkList;
		}

		public void setB_drinkList(Set<Drink> b_drinkList) {
			this.b_drinkList = b_drinkList;
		}
		
		public Set<Event> getB_eventList() {
			return b_eventList;
		}

		public void setB_eventList(Set<Event> b_eventList) {
			this.b_eventList = b_eventList;
		}
		
		public Boolean getB_active() {
			return b_active;
		}
		
		public void setB_active(Boolean b_active) {
			this.b_active=b_active;
		}
		// On creation of a brewery the brewery can provide the following. 
		//Other information will be entered at another point.
		@PersistenceConstructor
		public Brewery(String b_name,
					   String b_street,
					   String b_city,
					   String b_state,
					   String b_zip,
					   String b_phone,
					   String b_email,
					   String b_url,
					   Boolean hasTours,
					   Boolean hasFood) {
			super();
			this.b_name   = b_name;
			this.b_street = b_street;
			this.b_city   = b_city;
			this.b_state  = b_state;
			this.b_zip    = b_zip;
			this.b_phone  = b_phone;
			this.b_email  = b_email;
			this.b_url    = b_url;
			this.hasTours = hasTours;
			this.hasFood  = hasFood;
		}
		
		@Override
		public String toString() {
			return "Brewery: [ bname = " + b_name 
							 + ", Street = " + b_street 
							 + ", City = " + b_city 
							 + ", State = " + b_state
							 + ", Zip = " + b_zip 
							 + ", Phone = " + b_phone 
							 + ", Email = " + b_email
							 + ", Website = " + b_url
							 + ", Tours = " + hasTours 
							 + ", Food = " + hasFood + "]";
		}
		
}
