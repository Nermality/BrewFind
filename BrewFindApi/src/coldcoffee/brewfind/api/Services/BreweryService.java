package coldcoffee.brewfind.api.Services;

import java.util.Comparator;
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
	
	private int curBrewMax = 0;
	
	public Brewery findBrewery(String bname) {
		return breweryRepository.searchByBname(bname);
	}
	
	public Brewery findBrewery(int brewNum) {
		return breweryRepository.searchByBrewNum(brewNum);
	}
	
	public List<Brewery> getList(){
		List<Brewery> toRet = breweryRepository.findAll();
		if(toRet.isEmpty() || toRet == null){
			return null;
		}
		
		curBrewMax = toRet.stream()
			.max(new Comparator<Brewery>() {
				@Override
				public int compare(Brewery one, Brewery two) {
					return one.getB_brewNum() - two.getB_brewNum(); 
				}
			})
			.get().getB_brewNum();
		
		return toRet;
	}
	
	public Brewery saveBrewery(Brewery b) {
		breweryRepository.save(b);
		return findBrewery(b.b_name);
	}
	
	public void deleteBrewery(Brewery b) {
		breweryRepository.delete(b);
		
	}
	
	public int getCurBrewMax() {
		if(curBrewMax == 0) {
			getList();
		}
		return curBrewMax;
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
