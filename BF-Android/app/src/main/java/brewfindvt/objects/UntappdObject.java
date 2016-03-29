package brewfindvt.objects;

import java.util.List;

/**
 * Created by user on 3/25/2016.
 */
public class UntappdObject {

    public String u_breweryName;
    public int u_ratingCount;
    public Double u_rating;
    public List<Drink> u_drinkList;

    public String getU_breweryName() {
        return u_breweryName;
    }

    public void setU_breweryName(String u_breweryName) {
        this.u_breweryName = u_breweryName;
    }

    public int getU_ratingCount() {
        return u_ratingCount;
    }

    public void setU_ratingCount(int u_ratingCount) {
        this.u_ratingCount = u_ratingCount;
    }

    public Double getU_rating() {
        return u_rating;
    }

    public void setU_rating(Double u_rating) {
        this.u_rating = u_rating;
    }

    public List<Drink> getU_drinkList() {
        return u_drinkList;
    }

    public void setU_drinkList(List<Drink> u_drinkList) {
        this.u_drinkList = u_drinkList;
    }
}
