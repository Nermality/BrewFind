package brewfindvt.objects;

/**
 * Created by user on 3/25/2016.
 */
public class Rating {
    public int count;
    public Double rating;

    public Rating(Double rating, int count) {
        this.rating = rating;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
