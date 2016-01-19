package coldcoffee.brewfind.api.Repositories;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import coldcoffee.brewfind.api.Objects.Brewery;

@Repository
public interface BreweryRepository extends MongoRepository<Brewery, String> {
	
	@Query("{'b_name' : ?0}")
	public Brewery searchByBname(String b_name);	
	
	@Query("{'b_breweryNum' : ?0}")
	public Brewery searchByBrewNum(int b_breweryNum);

	@Query("{'b_breweryNum' : {$gt : ?0}}")
	public List<Brewery> searchByBrewNumMax(int b_breweryNum);
}
