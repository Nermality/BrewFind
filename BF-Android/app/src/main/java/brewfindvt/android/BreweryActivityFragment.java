package brewfindvt.android;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import brewfindvt.adapter.BreweryListViewAdapter;
import brewfindvt.managers.CacheManager;
import brewfindvt.objects.Brewery;

/**
 * Created by Nermality on 2/5/2016.
 */
public class BreweryActivityFragment extends Fragment
        implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public  Map<Integer, Brewery> brewMap;
    public String lastError;
    Fragment _breweryFragment;
    private CacheManager cacheManager;
    public  ListView listView;
    public  List<Brewery> brewItems = new ArrayList<Brewery>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _breweryFragment = new BreweryFragment();
        cacheManager = CacheManager.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.brewery_activity_fragment, container, false);
        ListView lv = (ListView) view.findViewById(R.id.listView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Brewery toGo = brewItems.get(position);
                ((MainActivity) getActivity()).makeBreweryPage(toGo);
            }


        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        populatePage();
        super.onActivityCreated(savedInstanceState);
    }

    public void populatePage(){
        BreweryListViewAdapter adapter = new BreweryListViewAdapter(getActivity(),
                R.layout.brew_view, brewItems);
        if(adapter != null) {
            adapter.clear();
        }
        brewMap = cacheManager.getBreweries();
        brewItems.addAll(cacheManager.getBreweries().values());

        Comparator<Brewery> brewCompare = new Comparator<Brewery>() {
            @Override
            public int compare(Brewery a, Brewery b) {
                String ac = a.getB_name();
                String bc = b.getB_name();

                if(ac.startsWith("The")) {
                    ac = ac.substring(4);
                }
                if(b.getB_name().startsWith("The")) {
                    bc = bc.substring(4);
                }

                return ac.compareTo(bc);
            }
        };
        Collections.sort(brewItems, brewCompare);

        listView = (ListView) getActivity().findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    public void onListItemClick(View v, Brewery b) {
        Intent i= new Intent("brewfindvt.android.BreweryFragment");
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = mapper.writeValueAsString(b);
            i.putExtra("brewery",jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        startActivity(i);
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        lastError = "Failure - onConnectionFailed. No location to display";
        Toast.makeText(getContext(), lastError, Toast.LENGTH_LONG).show();
    }
}

