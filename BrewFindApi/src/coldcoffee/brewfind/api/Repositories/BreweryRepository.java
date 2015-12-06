package coldcoffee.brewfind.api.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import coldcoffee.brewfind.api.Objects.BreweryObject;

public interface BreweryRepository extends MongoRepository<BreweryObject, String> {

}
