package coldcoffee.brewfind.api.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import coldcoffee.brewfind.api.Objects.Brewery;

public interface BreweryRepository extends MongoRepository<Brewery, String> {

}
