package coldcoffee.brewfind.api.Objects;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="breweries")
public class Brewery extends BrewFindObject {

		@Id
		public String b_id;
		public int b_breweryNum;
		public int b_version;
		
		public String b_name;
		
		public String b_addr1;		
		public String b_addr2;
		public String b_city;
		public String b_state;
		public String b_zip;
	
		public String b_phone;
		public String b_email;
		public String b_url;
		
		public String b_description;
		
		public Boolean b_hasTours;
		public Boolean b_hasFood;
		
		//public xxxx b_hours??
		//public String b_admin
		
		public String b_logoImage;
		public Double b_rating;
		
		//public Set<Drink> b_drinkList;
	
		//facebook
		
		public int getB_breweryNum() {
			return b_breweryNum;
		}

		public void setB_breweryNum(int b_breweryNum) {
			this.b_breweryNum = b_breweryNum;
		}

		public int getB_version() {
			return b_version;
		}

		public void setB_version(int b_version) {
			this.b_version = b_version;
		}

		public String getB_name() {
			return b_name;
		}

		public void setB_name(String b_name) {
			this.b_name = b_name;
		}

		public String getB_addr1() {
			return b_addr1;
		}

		public void setB_addr1(String b_addr1) {
			this.b_addr1 = b_addr1;
		}

		public String getB_addr2() {
			return b_addr2;
		}

		public void setB_addr2(String b_addr2) {
			this.b_addr2 = b_addr2;
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

		public String getB_description() {
			return b_description;
		}

		public void setB_description(String b_description) {
			this.b_description = b_description;
		}

		public Boolean getB_hasTours() {
			return b_hasTours;
		}

		public void setB_hasTours(Boolean b_hasTours) {
			this.b_hasTours = b_hasTours;
		}

		public Boolean getB_hasFood() {
			return b_hasFood;
		}

		public void setB_hasFood(Boolean b_hasFood) {
			this.b_hasFood = b_hasFood;
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

		public String getB_id() {
			return b_id;
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
							 + ", Phone = " + b_phone 
							 + ", Email = " + b_email
							 + ", Website = " + b_url + "]";
		}
		
}
