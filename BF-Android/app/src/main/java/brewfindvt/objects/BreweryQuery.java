package brewfindvt.objects;

import java.util.List;

/**
 * Created by user on 2/2/2016.
 */
public class BreweryQuery {

    public BrewFindToken token;
    public List<Brewery> list;

    public BreweryQuery(List<Brewery> list) {
        this.list = list;
        token = null;
    }

    public List<Brewery> getList() {
        return list;
    }

    public void setList(List<Brewery> list) {
        this.list = list;
    }

    public BrewFindToken getToken() {
        return token;
    }

    public void setToken(BrewFindToken token) {
        this.token = token;
    }
}
