package coldcoffee.brewfind.Services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.mongodb.MongoTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coldcoffee.brewfind.Objects.BrewFindObject;
import coldcoffee.brewfind.Objects.BrewFindResponse;
import coldcoffee.brewfind.Objects.BrewFindToken;
import coldcoffee.brewfind.Objects.Brewery;
import coldcoffee.brewfind.Objects.BreweryQuery;
import coldcoffee.brewfind.Objects.Version;
import coldcoffee.brewfind.Repositories.BreweryRepository;

@Service
public class BreweryService {

	@Autowired
	public BreweryRepository breweryRepository;
	
	private int curBrewMax = 0;
	
	public Brewery findBrewery(String bname) {
		return breweryRepository.searchByBname(bname);
	}

	public int findBrewNum(String bname) {
		if( breweryRepository.searchByBname(bname) == null){
			return -1;
		}
		return breweryRepository.searchByBname(bname).b_breweryNum;
	}

	public Brewery findBrewery(int breweryNum) {
		return breweryRepository.searchByBrewNum(breweryNum);
	}
	
	public BrewFindResponse addBreweryFromQuery(BreweryQuery query){
		
		BrewFindToken tok = query.getToken();
		
		//Check if token has proper privileges
		if(tok.getAccess() < 3) {
			return new BrewFindResponse(11, "User must be admin to add brewery");
		}
		
		//Retrieve Brewery out of Brewery Query
		Brewery newBrew = query.getList().get(0); 
		
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
			newBrew.setB_breweryNum(curMax);
			
			// add brewery to database
			newBrew = saveBrewery(newBrew);
			
			//return success message
			return new BrewFindResponse(0, "OK", newBrew);
		}
	}
	
	public BrewFindResponse updateBreweryFromQuery(BreweryQuery query) {
		
		BrewFindToken tok = query.getToken();
		
		//Check if token has proper privileges
		if(tok.getAccess() < 2) {
			return new BrewFindResponse(11, "User must be Brewer to edit brewery");
		}
		
		//Retrieve Brewery out of Brewery Query
		Brewery brewery = query.getList().get(0); 
		
		if(brewery == null){
			return new BrewFindResponse(10, "brewery failed to be created");
		}
	
		 Brewery oldb = findBrewery(brewery.getB_breweryNum());
		 
		//Check if brewery is in database
		if(oldb == null) {
			return new BrewFindResponse(8, "not found in system, insert failed");
		}
		else{
			//newBrew.setB_version(oldb.getB_version()+1);
			
			// TODO: SAFE UPDATE
			//update brewery in database
			brewery.b_id = oldb.b_id;
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
	
	
	//TODO change to instead compare to server cache
	/**
	 * Take the version list passed to it by the client and compare it to the server cache
	 * @param version list of version of breweries being used by client
	 * @return returns list of breweries to be updated with the new information
	 */
	public BrewFindResponse UpdateClientBreweryinfo(List<Version> version){
		List<BrewFindObject> breweries = new ArrayList<BrewFindObject>();
		int maxBrew = 0;
		for(Version v : version){
			
			//Check if brewery is in database if not found then will set it to be removed client side
			if(maxBrew < v.getV_brewNum()){
				maxBrew = v.getV_brewNum();
			}
			
			if(findBrewery(v.getV_brewNum()) == null) {
				Brewery b=new Brewery();
				//if not found it will create a new brewery with the b_num equaling the missing b_num 
				//and set it to v_num to -1
				b.setB_version(-1);
				b.setB_breweryNum(v.getV_brewNum());
				breweries.add(b);
			}
			
			//compare versions if not the same number update it
			else if(v.getV_verNum() != findBrewery(v.getV_brewNum()).getB_version()){
				breweries.add(findBrewery(v.getV_brewNum()));
			}
		}
		
		if(!breweryRepository.searchByBrewNumMax(maxBrew).isEmpty()) {
			List<Brewery> addBrews = breweryRepository.searchByBrewNumMax(maxBrew);
			breweries.addAll(addBrews);
		}
		
		//return list of breweries to update
		return new BrewFindResponse(14,"Updated list", breweries);
	}
	
	
	public List<Brewery> getList() throws MongoTimeoutException {
		List<Brewery> toRet = breweryRepository.findAll();
		if(toRet.isEmpty() || toRet == null){
			return null;
		}
		
		curBrewMax = toRet.stream()
			.max(new Comparator<Brewery>() {
				@Override
				public int compare(Brewery one, Brewery two) {
					return one.getB_breweryNum() - two.getB_breweryNum(); 
				}
			})
			.get().getB_breweryNum();
		
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
