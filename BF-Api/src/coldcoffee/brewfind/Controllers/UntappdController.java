package coldcoffee.brewfind.Controllers;

import coldcoffee.brewfind.Objects.BrewFindObject;
import coldcoffee.brewfind.Objects.BrewFindResponse;
import coldcoffee.brewfind.Objects.Brewery;
import coldcoffee.brewfind.Objects.UntappdObject;
import coldcoffee.brewfind.Services.BreweryService;
import coldcoffee.brewfind.Services.UntappdService;
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
@Path("/utwrapper")
public class UntappdController {

    @Autowired
    private UntappdService untappdService;

    @Autowired
    private BreweryService breweryService;

    @GET
    @Produces("application/json")
    @Path("/{brewId}")
    public BrewFindResponse getDrinksForBrewery(@PathParam("brewId") int brewId) {

        Brewery toFind = breweryService.findBrewery(brewId);
        UntappdObject toRet;

        try {
            toRet = untappdService.getBreweryDrinks(toFind.getB_utId());
        } catch(IOException e) {
            e.printStackTrace();
            return new BrewFindResponse(16, "IOException getting drinks");
        }

        if(toRet == null) {
            return new BrewFindResponse(10, "Null response from Untappd");
        }

        return new BrewFindResponse(0, "OK", toRet);
    }
}
