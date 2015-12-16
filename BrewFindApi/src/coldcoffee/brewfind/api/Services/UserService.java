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
	
	public User findUser(String uname) {
		return userRepository.searchByUname(uname);
	}
	
	public User saveNewToken(User user, BrewFindToken tok) {
		
		user.setU_curToken(tok);
		return userRepository.save(user);
	
	}
	
	public boolean checkToken(BrewFindToken tok) {
		
		String uname = Base64.decodeAsString(tok.token);
		User u = findUser(uname);
		if(u == null) {
			return false;
		}
		
		BrewFindToken uTok = u.getU_curToken();	
		if(uTok == null) {
			return false;
		}
		
		if((tok.access == uTok.access) && (tok.stamp == uTok.stamp) && (tok.token.equals(uTok.token))) {
			return true;
		}
		
		return false;
	}
	
	public User saveUser(User u) {
		userRepository.save(u);
		return findUser(u.u_name);
	}
	
	public void deleteUser(User u) {
		userRepository.delete(u);
	}
	
	
}
