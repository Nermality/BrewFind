package coldcoffee.brewfind.api.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import coldcoffee.brewfind.api.Objects.User;

public interface UserRepository extends MongoRepository<User, String>{
	
		@Query("{'u_name' : ?0}")
		public List<User> searchByUname(String u_name);
		
		
}
