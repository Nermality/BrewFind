package coldcoffee.brewfind.api.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import coldcoffee.brewfind.api.Objects.BrewFindToken;
import coldcoffee.brewfind.api.Objects.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
	
		@Query("{'u_name' : ?0}")
		public User searchByUname(String u_name);
	
}
