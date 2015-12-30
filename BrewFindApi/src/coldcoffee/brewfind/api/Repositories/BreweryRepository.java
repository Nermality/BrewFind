package coldcoffee.brewfind.api.Repositories;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;

import coldcoffee.brewfind.api.Objects.Brewery;

@Repository
public interface BreweryRepository extends MongoRepository<Brewery, String> {
	
	@Query("{'b_name' : ?0}")
	public Brewery searchByBname(String b_name);
	
	
}
