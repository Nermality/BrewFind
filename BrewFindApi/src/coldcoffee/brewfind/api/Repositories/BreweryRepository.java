package coldcoffee.brewfind.api.Repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import coldcoffee.brewfind.api.Objects.Brewery;

@Repository
public interface BreweryRepository extends MongoRepository<Brewery, String> {
	
	@Query("{'b_name' : ?0}")
	public Brewery searchByBname(String b_name);	
}
