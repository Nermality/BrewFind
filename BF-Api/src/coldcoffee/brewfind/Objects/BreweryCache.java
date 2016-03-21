package coldcoffee.brewfind.Objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BreweryCache {

	// BreweryNum -> Brewery
	private static Map<Integer, Brewery> brewMap;
	
	// BreweryNum -> VersionNum
	private static Map<Integer, Integer> versionMap;
	
	public BreweryCache() {
		brewMap = new HashMap<Integer, Brewery>();
		versionMap = new HashMap<Integer, Integer>();
	}
	
	// initialize map
	public void init(List<Brewery> list) {
		
		for( Brewery b : list) {
			brewMap.put(b.getB_breweryNum(), b);
			versionMap.put(b.getB_breweryNum(), b.getB_version());
		}
	}
	
	// newly updated brewery to be put into map
	// add brewery if not already in list
	public void update(Brewery toUpdate) {
		
		if(versionMap.containsKey(toUpdate.getB_breweryNum())) {
			int curVer = versionMap.get(toUpdate.getB_breweryNum());
			if(curVer >= toUpdate.getB_version()) {
				return;
			}
		}
		
		brewMap.put(toUpdate.getB_breweryNum(), toUpdate);
		versionMap.put(toUpdate.getB_breweryNum(), toUpdate.getB_version());
	}
	
	public Brewery findByNumber(int brewNum) {
		
		if(brewMap.containsKey(brewNum)) {
			return brewMap.get(brewNum);
		}
		
		return null;
	}
	
	public void removeByNumber(int brewNum) {
		
		brewMap.remove(brewNum);
		versionMap.remove(brewNum);
	}
	
	public List<Brewery> updateReturnList(List<Version> clientVersion) {
		return null;
	}
	
	
	
	
}
