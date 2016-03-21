package coldcoffee.brewfind.Objects;

public class Drink extends BrewFindObject {
	
	public String d_name;
	public String d_style;
	public String d_description;
	public String d_label;
	public Double d_abv;
	public int d_ibu;
	public int d_inProduction;
	public Double d_rating;

	public String getD_name() {
		return d_name;
	}

	public void setD_name(String d_name) {
		this.d_name = d_name;
	}

	public String getD_style() {
		return d_style;
	}

	public void setD_style(String d_style) {
		this.d_style = d_style;
	}

	public String getD_description() {
		return d_description;
	}

	public void setD_description(String d_description) {
		this.d_description = d_description;
	}

	public String getD_label() {
		return d_label;
	}

	public void setD_label(String d_label) {
		this.d_label = d_label;
	}

	public Double getD_abv() {
		return d_abv;
	}

	public void setD_abv(Double d_abv) {
		this.d_abv = d_abv;
	}

	public int getD_ibu() {
		return d_ibu;
	}

	public void setD_ibu(int d_ibu) {
		this.d_ibu = d_ibu;
	}

	public int getD_inProduction() {
		return d_inProduction;
	}

	public void setD_inProduction(int d_inProduction) {
		this.d_inProduction = d_inProduction;
	}

	public Double getD_rating() {
		return d_rating;
	}

	public void setD_rating(Double d_rating) {
		this.d_rating = d_rating;
	}
}
