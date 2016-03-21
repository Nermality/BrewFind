package coldcoffee.brewfind.Services;


import coldcoffee.brewfind.Objects.Drink;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/15/2016.
 */
@Service
public class DrinkService {

    private Client client;

    private final String utEndpoint = "https://api.untappd.com/v4";
    private final String utClientId = "277D314CC1A21FF02D88D60CDA74D3BF4B848E2C";
    private final String utClintSecret = "B85DCA33495CE957FA89A91352013F4E4105AB3D";
    private final String utToken = "04092770C9B837C4EC61906C97E6B9D735867E7F";

    public DrinkService() {
        client = ClientBuilder.newClient();
    }

    public List<Drink> getBreweryDrinks(int id) throws IOException {

         String response = client.target(utEndpoint)
                .path("brewery/info/"+id)
                .queryParam("access_token", utToken)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);

        if(response == null) { return null; }

        List<Drink> drinkList = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        JsonNode responseNode = root.path("response");
        JsonNode breweryNode = responseNode.path("brewery");
        if(breweryNode == null) {
            return null;
        }

        JsonNode beerNode = breweryNode.path("beer_list");
        JsonNode beerListNode = beerNode.path("items");

        for(JsonNode n : beerListNode) {

            JsonNode beer = n.path("beer");
            Drink toAdd = new Drink();

            toAdd.setD_name(beer.path("beer_name").asText());
            toAdd.setD_label(beer.path("beer_label").asText());
            toAdd.setD_style(beer.path("beer_style").asText());
            toAdd.setD_abv(beer.path("beer_abv").asDouble());
            toAdd.setD_ibu(beer.path("beer_ibu").asInt());
            toAdd.setD_description(beer.path("beer_description").asText());
            toAdd.setD_inProduction(beer.path("is_in_production").asInt());
            toAdd.setD_rating(beer.path("rating_score").asDouble());

            drinkList.add(toAdd);
        }

        return drinkList;
    }

}
