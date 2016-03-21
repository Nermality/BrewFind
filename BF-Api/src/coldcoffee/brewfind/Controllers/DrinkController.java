package coldcoffee.brewfind.Controllers;

import coldcoffee.brewfind.Objects.BrewFindObject;
import coldcoffee.brewfind.Objects.BrewFindResponse;
import coldcoffee.brewfind.Objects.Brewery;
import coldcoffee.brewfind.Objects.Drink;
import coldcoffee.brewfind.Services.BreweryService;
import coldcoffee.brewfind.Services.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.util.List;

/**
 * Created by user on 3/15/2016.
 */
@Component
@Path("/drink")
public class DrinkController {

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private BreweryService breweryService;

    @GET
    @Produces("application/json")
    @Path("/{brewId}")
    public BrewFindResponse getDrinksForBrewery(@PathParam("brewId") int brewId) {

        Brewery toFind = breweryService.findBrewery(brewId);

        List<? extends BrewFindObject> toRet;
        try {
            toRet = drinkService.getBreweryDrinks(toFind.getB_utId());
        } catch(IOException e) {
            e.printStackTrace();
            return new BrewFindResponse(16, "IOException getting drinks");
        }

        if(toRet == null) {
            return new BrewFindResponse(10, "Null response from Untappd");
        }

        return new BrewFindResponse(0, "OK", (List<BrewFindObject>)toRet);
    }
}
