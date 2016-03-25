package brewfindvt.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import brewfindvt.managers.ApiManager;
import brewfindvt.managers.CacheManager;
import brewfindvt.objects.BrewFindResponse;
import brewfindvt.objects.Brewery;
import brewfindvt.objects.EventSummary;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity
        implements
            NavigationView.OnNavigationItemSelectedListener {

    Fragment _mapActivityFragment;
    Fragment _brewFragment;
    Fragment _eventActivityFragment;
    Fragment _eventFragment;
    Fragment _brewActivityFragment;
    Fragment _mapFragment;
    CacheManager _cacheManager;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        _brewActivityFragment = new BreweryActivityFragment();
        _mapActivityFragment = new MapActivityFragment();
        _brewFragment = new BreweryFragment();
        _eventFragment = new EventFragment();
        _eventActivityFragment = new EventActivityFragment();
        _mapFragment = new MapActivityFragment();
        _cacheManager = CacheManager.getInstance();
        dialog = new ProgressDialog(this);
        populateCache();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Map) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, _mapActivityFragment).commit();
        } else if (id == R.id.nav_Breweries) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, _brewActivityFragment).commit();

        } else if (id == R.id.nav_Events) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, _eventActivityFragment).commit();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public CacheManager getCacheManager() {
        return _cacheManager;
    }

    public void makeBreweryPage(Brewery b) {
        ((BreweryFragment)_brewFragment).setNewBrew(b);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, _brewFragment)
                .addToBackStack(null)
                .commit();
    }

    public void makeEventPage(EventSummary e) {
        ((EventFragment)_eventFragment).setNewEvent(e);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, _eventFragment)
                .addToBackStack(null)
                .commit();
    }

    public void populateCache(){
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(true);
        dialog.show();
        dialog.setMessage("Loading breweries....");

        ApiManager.getBreweries(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    dialog.setMessage("Loading breweries....");

                    JSONArray brews = response.getJSONArray("rObj");
                    ArrayList<Brewery> breweries = new ArrayList<>();
                    ObjectMapper mapper = new ObjectMapper();
                    for (int i = 0; i < brews.length(); i++) {
                        Brewery toAdd = mapper.readValue(brews.getString(i), Brewery.class);
                        breweries.add(toAdd);
                    }
                    _cacheManager.updateBreweries(breweries);
                    _cacheManager.updateImages(breweries);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, _brewActivityFragment).commit();
                    Toast.makeText(getApplicationContext(), "HOLY SHIT WHAT", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.hide();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable e) {
                Toast.makeText(getApplicationContext(), "Failed to get breweries", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, java.lang.Throwable throwable, org.json.JSONArray errorResponse) {
                Toast.makeText(getApplicationContext(), "Failed to get breweries", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, java.lang.Throwable throwable, org.json.JSONObject errorResponse) {
                Toast.makeText(getApplicationContext(), "Failed to get breweries", Toast.LENGTH_LONG).show();
            }
        });

        ApiManager.getEvents(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    dialog.show();
                    dialog.setMessage("Loading Events");
                    JSONObject eventMap = response.getJSONObject("eventMap");
                    Map<String, List<EventSummary>> toIns = new HashMap<>();
                    ObjectMapper mapper = new ObjectMapper();
                    Iterator<String> itr = eventMap.keys();
                    while (itr.hasNext()) {
                        String cal = itr.next();
                        List<EventSummary> events = mapper.readValue(eventMap.getJSONArray(cal).toString(), new TypeReference<List<EventSummary>>() {
                        });
                        toIns.put(cal, events);
                    }
                    _cacheManager.updateEvents(toIns);
                    Toast.makeText(getApplicationContext(), "HOLY SHIT EVENTS", Toast.LENGTH_LONG).show();
                    dialog.hide();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable e) {
                Toast.makeText(getApplicationContext(), "Failed to get events", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, java.lang.Throwable throwable, org.json.JSONArray errorResponse) {
                Toast.makeText(getApplicationContext(), "Failed to get events", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, java.lang.Throwable throwable, org.json.JSONObject errorResponse) {
                Toast.makeText(getApplicationContext(), "Failed to get events", Toast.LENGTH_LONG).show();
            }
        });
    }


}
