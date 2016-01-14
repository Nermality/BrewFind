package coldcoffee.brewfind.api.Services;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coldcoffee.brewfind.api.Objects.BrewFindObject;
import coldcoffee.brewfind.api.Objects.BrewFindQuery;
import coldcoffee.brewfind.api.Objects.BrewFindResponse;
import coldcoffee.brewfind.api.Objects.BrewFindToken;
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
	
	public BrewFindResponse addBreweryFromQuery(BrewFindQuery query){
		
		BrewFindToken tok = query.getToken();
		
		//Check if token has proper privileges
		if(tok.getAccess() < 3) {
			return new BrewFindResponse(11, "User must be admin to add brewery");
		}
		
		//Retrieve Brewery out of Brewery Query
		Brewery newBrew = (Brewery) query.getQList().get(0); 
		
		// Null check
		if(newBrew == null){
			return new BrewFindResponse(10, "brewery failed to be created");
		}
		
		//Check if brewery is in database
		if(findBrewery(newBrew.b_name) != null) {
			return new BrewFindResponse(7, "Brewery found in system, insert failed");
		}
		
		else {
			// newBrew.setB_version(0);
			
			// find current brew number
			int curMax = getCurBrewMax();
			if(curMax == 0) {
				return new BrewFindResponse(12, "Database failure");
			}
			curMax++;
			if(findBrewery(curMax) != null) {
				// TODO: Put this in the service and shit
			}
			newBrew.setB_brewNum(curMax);
			
			// add brewery to database
			newBrew = saveBrewery(newBrew);
			
			//return success message
			return new BrewFindResponse(0, "OK", newBrew);
		}
	}
	
	public BrewFindResponse updateBreweryFromQuery(BrewFindQuery query) {
		
		BrewFindToken tok = query.getToken();
		
		//Check if token has proper privileges
		if(tok.getAccess() < 2) {
			return new BrewFindResponse(11, "User must be Brewer to edit brewery");
		}
		
		//Retrieve Brewery out of Brewery Query
		@SuppressWarnings("unchecked")
		Brewery brewery = (Brewery)query.getQList().get(0); 
		
		if(brewery == null){
			return new BrewFindResponse(10, "brewery failed to be created");
		}
	
		 Brewery oldb = findBrewery(brewery.getB_brewNum());
		 
		//Check if brewery is in database
		if(oldb == null) {
			return new BrewFindResponse(8, "not found in system, insert failed");
		}
		else{
			//newBrew.setB_version(oldb.getB_version()+1);
			
			// TODO: SAFE UPDATE
			//update brewery in database
			saveBrewery(brewery);
			
			//return success message
			return new BrewFindResponse(0, "Ok", brewery);
		}
		
	}
	
	public BrewFindResponse deleteBreweryFromToken(BrewFindToken token, int brewNum) {
			
		//Retrieve Brewery out of Brewery Query
		Brewery remBrew = findBrewery(brewNum);
		
		if(remBrew == null){
			return new BrewFindResponse(10, "No brewery object found");
		}

		// remove information from brewery db
		deleteBrewery(remBrew);
					
		return new BrewFindResponse(0, "OK");
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
