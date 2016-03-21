package brewfindvt.android;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import brewfindvt.managers.CacheManager;
import brewfindvt.objects.Brewery;

/**
 * Created by user on 2/5/2016.
 */
public class MapActivityFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnInfoWindowClickListener {

    private SeekBar seekBar;
    private TextView seekText;
    private RadioButton rBrewery;
    private RadioButton rEvent;
    private MapFragment mapFrag;
    private GoogleMap map;

    private String lastError;
    private Location lastLocation;
    private LatLngBounds curBounds;
    private GoogleApiClient apiClient;

    private CacheManager cacheManager;
    private Map<Integer, Brewery> cacheBrews;
    private Map<LatLng, Integer> cacheCoords;
    private List<Marker> breweryMarkers;
    private List<Marker> eventMarkers;

    /////////////////////
    // FRAGMENT OVERRIDES
    /////////////////////

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(apiClient == null) {
            apiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        apiClient.connect();

        cacheManager = CacheManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.map_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cacheBrews = cacheManager.getBreweries();
        cacheCoords = cacheManager.getCoords();

        seekBar = (SeekBar) getActivity().findViewById(R.id.distanceSlider);
        seekText = (TextView) getActivity().findViewById(R.id.distanceText);
        rBrewery = (RadioButton) getActivity().findViewById(R.id.rBrewery);
        rEvent = (RadioButton) getActivity().findViewById(R.id.rEvent);

        rBrewery.toggle();
        rBrewery.setOnClickListener(rListener);
        rEvent.setOnClickListener(rListener);

        mapFrag = MapFragment.newInstance();
        getActivity().getFragmentManager().beginTransaction()
                .add(R.id.mapHolster, mapFrag).commit();

        mapFrag.getMapAsync(this);

        seekText.setText("Search distance: 5 miles");
        seekBar.setProgress(0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;
            int min = 5;

            @Override
            public void onProgressChanged(SeekBar bar, int progressValue, boolean fromUser) {
                progress = (progressValue + min);
            }

            @Override
            public void onStartTrackingTouch(SeekBar bar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar bar) {
                if(map != null && lastLocation != null) {
                    refocusMap(progress);
                    if(rBrewery.isChecked()) {
                        markPins(curBounds, breweryMarkers);
                    } else {
                        markPins(curBounds, eventMarkers);
                    }
                }
                seekText.setText("Search distance: " + progress + " miles");
            }
        });
    }

    ////////////////
    // MAP FUNCTIONS
    ////////////////

    @Override
    public void onMapReady(GoogleMap newMap) {

        newMap.setMyLocationEnabled(true);
        map = newMap;
        map.setOnInfoWindowClickListener(this);
        initPins();

        if(lastLocation != null) {
            refocusMap(5);
            markPins(curBounds, breweryMarkers);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        LatLng loc = marker.getPosition();
        Brewery toGo = cacheBrews.get(cacheCoords.get(loc));
        ((MainActivity)getActivity()).makeBreweryPage(toGo);
    }

    public void getPinsReady() {
        if(rBrewery.isChecked()) {
            for(Marker be : eventMarkers) {
                be.setVisible(false);
            }
            for(Marker bb : breweryMarkers) {
                bb.setVisible(true);
            }
        } else {
            for(Marker eb : breweryMarkers) {
                eb.setVisible(false);
            }
            for(Marker ee : eventMarkers) {
                ee.setVisible(true);
            }
        }

    }

    public void initPins() {
        ArrayList<Marker> bMarks = new ArrayList<>();
        for(LatLng l : cacheCoords.keySet()) {
            Brewery b = cacheBrews.get(cacheCoords.get(l));
            bMarks.add(map.addMarker(new MarkerOptions()
                    .position(l)
                    .title(b.getB_name())
                    .snippet(b.getB_city())));
        }
        breweryMarkers = bMarks;

        // Make list of event pins
        eventMarkers = new ArrayList<>();
        // Hide the right set of pins

        getPinsReady();
    }

    public void markPins(LatLngBounds bounds, List<Marker> markers) {
        for(Marker m : markers) {
            if(bounds.contains(m.getPosition())) {
                m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            } else {
                m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }
        }
    }

    public LatLngBounds getBounds(LatLng center, Double radius) {
        LatLng sw = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 225);
        LatLng ne = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 45);
        return new LatLngBounds(sw, ne);
    }

    public void refocusMap(int rad) {
        LatLng loc = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        Double radius = (rad * 1609.34);
        curBounds = getBounds(loc, radius);
        CameraUpdate newBounds = CameraUpdateFactory.newLatLngBounds(curBounds, 2);
        map.moveCamera(newBounds);
    }

    ////////////////
    // API OVERRIDES
    ////////////////

    @Override
    public void onConnected(Bundle bundle) {
        Location last = LocationServices.FusedLocationApi.getLastLocation(apiClient);
        if(last != null) {
            lastLocation = last;
            if(map != null) {
                refocusMap(5);
                markPins(curBounds, breweryMarkers);
            }
        } else {
            lastError = "Failure in onConnected(), no location to display";
            Toast.makeText(getContext(), lastError, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        lastError = "Failure - onConnectionFailed. No location to display";
        Toast.makeText(getContext(), lastError, Toast.LENGTH_LONG).show();
    }

    public View.OnClickListener rListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.rBrewery:
                    getPinsReady();
                    Toast.makeText(getContext(), "Brewery selected", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rEvent:
                    getPinsReady();
                    Toast.makeText(getContext(), "Event selected", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onDestroyView() {
        apiClient.disconnect();
        super.onStop();
    }
}
