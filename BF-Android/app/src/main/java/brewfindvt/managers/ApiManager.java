package brewfindvt.managers;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import brewfindvt.objects.Version;
import cz.msebera.android.httpclient.Header;

/**
 * Created by user on 2/1/2016.
 */
public class ApiManager {

    private static final String BASE_API = "http://52.35.37.107:8080";
    private static final String UNTAPPD_ENDPOINT = "/utwrapper";
    private static final String BREWERY_ENDPOINT = "/brewery";
    private static final String USER_ENDPOINT = "/user";
    private static final String EVENT_ENDPOINT = "/event";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getBreweries(AsyncHttpResponseHandler handler) {
        client.get((BASE_API + BREWERY_ENDPOINT), handler);
    }

    public static void updateBreweries(List<Version> current, AsyncHttpResponseHandler handler) {
        client.post((BASE_API + BREWERY_ENDPOINT + "/update"), handler);
    }

    public static void getUntappdInfoForBrewery(int brewNum, AsyncHttpResponseHandler handler) {
        client.get((BASE_API + UNTAPPD_ENDPOINT + "/" + brewNum), handler);
    }

    public static void getEvents(AsyncHttpResponseHandler handler) {
        //client.get((BASE_API + EVENT_ENDPOINT), handler);
        // FOR TESTING:
        client.get("http://10.0.0.162:8080/event", handler);
    }

    public static void authUser(String uname, String pwd, AsyncHttpResponseHandler handler) {
        client.addHeader("uname", uname);
        client.addHeader("pass", pwd);
        client.get((BASE_API + USER_ENDPOINT), handler);

        client.removeAllHeaders();
    }

    public static void getUser() {}

    public static void addUser() {}

    public static void deleteUser() {}
}
