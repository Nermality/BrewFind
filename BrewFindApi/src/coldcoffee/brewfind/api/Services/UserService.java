package coldcoffee.brewfind.api.Services;

import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coldcoffee.brewfind.api.Objects.BrewFindToken;
import coldcoffee.brewfind.api.Objects.User;
import coldcoffee.brewfind.api.Repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	public UserRepository userRepository;
	
	public User test(){
		System.out.println("In test function");
		User toRet = userRepository.searchByUname("mork");
		if(toRet == null){
			System.out.println(userRepository.toString());
			System.out.println(userRepository.count());
			return null;
		}
		
		return toRet;
	}
	
	public User getUser(String uname) {
		return userRepository.searchByUname(uname);
	}
	
	public User saveNewToken(User user, BrewFindToken tok) {
		
		user.setU_curToken(tok);
		return userRepository.save(user);
	
	}
	
	public boolean checkToken(BrewFindToken tok) {
		
		String uname = Base64.decodeAsString(tok.token);
		User u = getUser(uname);
		if(u.getU_curToken() == tok){
			return true;
		}
		
		return false;
	}
	
	
}
