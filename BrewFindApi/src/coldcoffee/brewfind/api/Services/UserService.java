package coldcoffee.brewfind.api.Services;

import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coldcoffee.brewfind.api.Objects.BrewFindResponse;
import coldcoffee.brewfind.api.Objects.BrewFindToken;
import coldcoffee.brewfind.api.Objects.User;
import coldcoffee.brewfind.api.Repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	public UserRepository userRepository;
	
	/**
	 * Retrieves a user from the database
	 * @param uname - username of the user to retrieve
	 * @return - user matching the username
	 */
	public User findUser(String uname) {
		return userRepository.searchByUname(uname);
	}
	
	/**
	 * Saves a new token to a user object
	 * @param user - User getting the token
	 * @param tok - Token to associate with the user
	 * @return - The user object saved to DB
	 */
	public User saveNewToken(User user, BrewFindToken tok) {
		
		user.setU_curToken(tok);
		return userRepository.save(user);
	
	}
	
	/**
	 * Verifies that a token is legitimate
	 * @param tok - Token to check
	 * @return true if valid, else false. 
	 */
	public boolean checkToken(BrewFindToken tok) {
		
		// The 'token' field on BrewFindToken contains the username base64 encoded
		String uname = Base64.decodeAsString(tok.token);
		
		// Make sure the user exists
		User u = findUser(uname);
		if(u == null) {
			return false;
		}
		
		// Make sure the user has a token associated with it
		BrewFindToken uTok = u.getU_curToken();	
		if(uTok == null) {
			return false;
		}
		
		// Check that the token matches
		if((tok.access == uTok.access) && (tok.stamp == uTok.stamp) && (tok.token.equals(uTok.token))) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Saves a user to the database
	 * @param u - User to save
	 * @return - User saved to DB
	 */
	public User saveUser(User u) {
		userRepository.save(u);
		return findUser(u.u_name);
	}
	
	/**
	 * Removes a user from the database
	 * Currently doesn't function...stupid MongoRepository
	 * @param u - User to delete
	 */
	public void deleteUser(User u) {
		userRepository.delete(u);
	}
	/**
	 * Updates user token with new timestamp
	 * @param tok token with new timestamp
	 * @return 
	 */
	public BrewFindResponse updateToken(BrewFindToken tok)
	{
		// The 'token' field on BrewFindToken contains the username base64 encoded
		String uname = Base64.decodeAsString(tok.token);
				
		// Make sure the user exists
		User u = findUser(uname);
		if(u == null) {
		return new BrewFindResponse(2,"User not found, token update failed");
		}
		u.setU_curToken(tok);
		userRepository.save(u);
		return new BrewFindResponse(0, "ok");
	}
	
}
