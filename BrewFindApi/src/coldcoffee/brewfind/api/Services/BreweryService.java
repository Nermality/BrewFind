package coldcoffee.brewfind.api.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coldcoffee.brewfind.api.Objects.BrewFindObject;
import coldcoffee.brewfind.api.Objects.Brewery;
import coldcoffee.brewfind.api.Repositories.BreweryRepository;

@Service
public class BreweryService {

	@Autowired
	public BreweryRepository breweryRepository;
	
	public Brewery findBrewery(String bname) {
		return breweryRepository.searchByBname(bname);
	}
	
	public List<Brewery> getList(){
		return breweryRepository.findAll();
	}
	
	public Brewery saveBrewery(Brewery b) {
		breweryRepository.save(b);
		return findBrewery(b.b_name);
	}
	
	public void deleteBrewery(Brewery b) {
		breweryRepository.delete(b);
		
	}

	/**
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
	**/

}
