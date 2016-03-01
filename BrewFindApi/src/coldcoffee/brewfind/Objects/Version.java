package coldcoffee.brewfind.Objects;

public class Version extends BrewFindObject{
	
	public int brewNum;
	public int verNum;
	
	public int getV_brewNum(){
		return brewNum;
	}
	
	public void setV_brewNum(int brewNum){
		this.brewNum=brewNum;
	}
	
	public int getV_verNum(){
		return verNum;
	}
	
	public void setV_verNum(int verNum){
		this.verNum=verNum;
	}
}
