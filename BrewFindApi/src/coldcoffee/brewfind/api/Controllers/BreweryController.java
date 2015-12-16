package coldcoffee.brewfind.api.Controllers;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import coldcoffee.brewfind.api.Configuration.SpringMongoConfig;
import coldcoffee.brewfind.api.Objects.Brewery;
import coldcoffee.brewfind.api.Objects.Drink;
import coldcoffee.brewfind.api.Objects.User;
import coldcoffee.brewfind.api.Repositories.BreweryRepository;


@Component
@Path("brewery")
public class BreweryController {
	
	private ApplicationContext ctx;
	private BreweryRepository breweryRepo;
	
	public BreweryController() {
		ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		breweryRepo = ctx.getBean(BreweryRepository.class);
	}
	
	@Path("test")
	@POST
	public String testShit(String update) {
		
		return update;
		
	}
	
	@Path("/new")
	@PUT
	public String addBrewery(
			@NotNull @HeaderParam("bname") 	  String bname,
			@NotNull @HeaderParam("bstreet")  String bstreet,
			@NotNull @HeaderParam("bcity")    String bcity,
			@NotNull @HeaderParam("bstate")   String bstate,
			@NotNull @HeaderParam("bzip")     String bzip,
			@NotNull @HeaderParam("bphone")   String bphone,
			@NotNull @HeaderParam("bemail")   String bemail,
			@NotNull @HeaderParam("burl")     String burl,
			@NotNull @HeaderParam("tours")    Boolean tours,
			@NotNull @HeaderParam("food")	  Boolean food,
			@NotNull @HeaderParam("bkey")	  String bkey){
		
		//QUESTION
		//Limit by name or name and location
		// PROS: brewery with more than one branch easily implemented
		// CONS: Small typos may create multiple instances of breweries, management relocates 
		if(!breweryRepo.searchByBname(bname).isEmpty()) {
			return "Brewery already exists in database";
		}
		else if(breweryRepo.findOne(bkey).getB_active()==true) {
			return "Brewery registry key used. Contact an administrator";
		}
		else {
		Brewery toIns = new Brewery(bname,
									bstreet,
									bcity,
									bstate,
									bzip,
									bphone,
									bemail,
									burl,
									tours,
									food);
		breweryRepo.save(toIns);
		breweryRepo.findOne(bkey).setB_active(true);
		return "OK";
		}
	}
	
	@Path("/update")
	@PUT
	public String updateBrewery(
			@NotNull @HeaderParam("bname") 	  String bname,
			@NotNull @HeaderParam("bstreet")  String bstreet,
			@NotNull @HeaderParam("bcity")    String bcity,
			@NotNull @HeaderParam("bstate")   String bstate,
			@NotNull @HeaderParam("bzip")     String bzip,
			@NotNull @HeaderParam("bphone")   String bphone,
			@NotNull @HeaderParam("bemail")   String bemail,
			@NotNull @HeaderParam("burl")     String burl,
			@NotNull @HeaderParam("tours")    Boolean tours,
			@NotNull @HeaderParam("food")	  Boolean food){
		 
		if(breweryRepo.searchByBname(bname).isEmpty()) {
			return "Brewery not found";
		}
		
		Brewery toIns = new Brewery(bname,
									bstreet,
									bcity,
									bstate,
									bzip,
									bphone,
									bemail,
									burl,
									tours,
									food);
		breweryRepo.save(toIns);
		
		return "OK";
	}

	/**
	@Path("/updateTap")
	@PUT
	public String updateTap(
			@NotNull @HeaderParam("bname") 	  String bname,
			@NotNull @HeaderParam("tapList")  Set<Drink> btapList) {
		
		if(breweryRepo.searchByBname(bname).isEmpty()) {
			return "Brewery not found";
		}
		
		Brewery toIns = new Brewery();
		toIns=breweryRepo.findOne(bname);
		toIns.setB_tapList(btapList);
		breweryRepo.save(toIns);
		
		return "Ok";
		
	}
	
	
	@Path("/updateDrink")
	@PUT
	public String updateDrink(
			@NotNull @HeaderParam("bname") 	  String bname,
			@NotNull @HeaderParam("tapList")  Set<Drink> bDrinkList) {
		
		if(breweryRepo.searchByBname(bname).isEmpty()) {
			return "Brewery not found";
		}
		
		Brewery toIns = new Brewery();
		toIns=breweryRepo.findOne(bname);
		toIns.setB_drinkList(bDrinkList);
		breweryRepo.save(toIns);
		
		return "Ok";
		
	}
	**/
}
