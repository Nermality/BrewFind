package coldcoffee.brewfind.api.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import coldcoffee.brewfind.api.Objects.Brewery;

public interface BreweryRepository extends MongoRepository<Brewery, String> {
	@Query("{'b_name' : ?0}")
	public List<Brewery> searchByBname(String b_name);
	
}
