package brewfindvt.objects;

/**
 * Created by user on 2/2/2016.
 */
public class BrewFindToken {

    public int access;
    public String token;
    public long stamp;

    public BrewFindToken(int access, String token, long stamp) {
        this.access = access;
        this.token = token;
        this.stamp = stamp;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getStamp() {
        return stamp;
    }

    public void setStamp(long stamp) {
        this.stamp = stamp;
    }
}
