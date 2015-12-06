package coldcoffee.brewfind.api.Objects;

import java.util.Set;

import org.springframework.data.annotation.Id;

public class BreweryObject {

		@Id
		public static int id;
		
		public static String b_description;
	
		public static String b_name;
		public static String b_street;
		public static String b_state;
		public static String b_city;
		public static String b_zip;
		
		public static String b_phone;
		public static String b_url;
		public static String b_email;
		
		public static boolean hasTours;
		public static boolean hasFood;
		
		public static String b_logoImage;
		public static Double b_rating;
		
		public static Set<DrinkObject> b_tapList;
		public static Set<DrinkObject> b_drinkList;
		
		public static Set<EventObject> b_eventList;	
		//facebook
}
