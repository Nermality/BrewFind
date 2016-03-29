package brewfindvt.managers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brewfindvt.objects.Brewery;
import brewfindvt.objects.Drink;
import brewfindvt.objects.EventSummary;
import brewfindvt.objects.Rating;
import brewfindvt.objects.UntappdObject;

/**
 * Created by user on 2/2/2016.
 */
public class CacheManager {

    private static CacheManager instance;

    private int[] versions;
    private Map<Integer, Brewery> breweries;
    private Map<Integer, Bitmap> b_logos;
    private Map<LatLng, Integer> coords;
    private Map<String, List<EventSummary>> eventMap;
    private List<EventSummary> allEvents;
    private Map<Integer, Rating> ratings;
    private Map<Integer, List<Drink>> drinks;

    private CacheManager() {
        versions = new int[50];
        breweries = new HashMap<>();
        b_logos = new HashMap<>();
        coords = new HashMap<>();
        eventMap = new HashMap<>();
        allEvents = new ArrayList<>();
        ratings = new HashMap<>();
        drinks = new HashMap<>();
    }

    public static CacheManager getInstance() {
        if(instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }

    public Map<Integer, Brewery> getBreweries() { return this.breweries; }

    public Map<Integer, Rating> getRatings() { return this.ratings; }

    public Rating getRating(int brewNum) { return this.ratings.get(brewNum); }

    public Map<Integer, List<Drink>> getDrinks() { return this.drinks; }

    public List<Drink> getDrinks(int brewNum) { return this.drinks.get(brewNum); }

    public Map<String, List<EventSummary>> getEventMap() {
        return eventMap;
    }

    public List<EventSummary> getAllEvents() {
        return allEvents;
    }

    public Map<Integer, Bitmap> getB_logos() {
        return b_logos;
    }

    public Bitmap getLogo(int brewNum) { return b_logos.get(brewNum); }

    public Map<LatLng, Integer> getCoords() { return this.coords; }

    public void updateVersions(int[] vers) {
        versions = vers;
    }

    public void updateBreweries(List<Brewery> brews) {
        for(Brewery b : brews) {
            this.breweries.put(b.b_breweryNum, b);
        }

        this.coords.clear();
        for(Brewery b : breweries.values()) {
            LatLng toAdd = new LatLng(b.b_lat, b.b_long);
            this.coords.put(toAdd, b.b_breweryNum);
        }

        // TODO: init versions
    }

    public void updateEvents(Map<String, List<EventSummary>> events) {
        this.eventMap = events;
        for(List<EventSummary> l : events.values()) {
            allEvents.addAll(l);
        }
    }


    public void updateImages(List<Brewery> brews) {
        for (Brewery b : brews) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL("http://ec2-52-38-43-166.us-west-2.compute.amazonaws.com/img/breweries/"+b.b_breweryNum+"/brewery_profile_pic.jpg").getContent());
                this.b_logos.put(b.b_breweryNum,bitmap);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateFromUntappdObject(int brewNum, UntappdObject got) {
        this.ratings.put(brewNum, new Rating(got.getU_rating(), got.getU_ratingCount()));
        this.drinks.put(brewNum, got.getU_drinkList());
        return;
    }



}
