package coldcoffee.brewfind.api.Objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BreweryCache {

	// BreweryNum -> ( VersionNum -> Brewery )
	private static Map<Integer, HashMap<Integer, Brewery>> cacheMap;
	
	public BreweryCache() {
		cacheMap = new HashMap<Integer, HashMap<Integer, Brewery>>();
	}
	
	public Map<Integer, HashMap<Integer, Brewery>> getInstance() {
		return cacheMap;
	}
	
	public void init(List<Brewery> list) {
		// initialize map
	}
	
	public void update(Brewery toUpdate) {
		// newly updated brewery to be put into map
		// add brewery if not already in list
	}
	
	public Brewery findByNumber(int brewNum) {
		return null;
	}
	
	public void removeByNumber(int brewNum) {
		
	}
	
	public List<Brewery> updateReturnList(List<Version> clientVersion) {
		return null;
	}
	
	
	
	
}
