package coldcoffee.brewfind.api.Objects;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="breweries")
public class Brewery extends BrewFindObject {

		@Id
		public String b_id;
		public int b_brewNum;
		public int b_version;

		public String b_description;
		public String b_name;
		
		/*
		public String b_street;
		public String b_city;
		public String b_state;
		public String b_zip;
		*/
		public String location;
		
		public String b_phone;
		public String b_email;
		public String b_url;
		
		public Boolean hasTours;
		public Boolean hasFood;
		
		public String b_logoImage;
		public Double b_rating;
		
		public Set<Drink> b_drinkList;
	
		//facebook
		
		public Brewery() 
		{}
		
		public int getB_version() {
			return b_version;
		}

		public void setB_version(int b_version) {
			this.b_version = b_version;
		}

		public String getB_description() {
			return b_description;
		}

		public void setB_description(String b_description) {
			this.b_description = b_description;
		}

		public String getB_name() {
			return b_name;
		}

		public void setB_name(String b_name) {
			this.b_name = b_name;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
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

		public Double getB_rating() {
			return b_rating;
		}

		public void setB_rating(Double b_rating) {
			this.b_rating = b_rating;
		}

		public Set<Drink> getB_drinkList() {
			return b_drinkList;
		}

		public void setB_drinkList(Set<Drink> b_drinkList) {
			this.b_drinkList = b_drinkList;
		}

		// On creation of a brewery the brewery can provide the following. 
		//Other information will be entered at another point.
		@PersistenceConstructor
		public Brewery(String b_name){
			this.b_name = b_name;
		}
		
		@Override
		public String toString() {
			return "Brewery: [ bname = " + b_name 
							 + ", Location = " + location
							 + ", Phone = " + b_phone 
							 + ", Email = " + b_email
							 + ", Website = " + b_url
							 + ", Tours = " + hasTours 
							 + ", Food = " + hasFood + "]";
		}
		
}
