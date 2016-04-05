package brewfindvt.android;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import brewfindvt.adapter.BreweryListViewAdapter;
import brewfindvt.adapter.EventListViewAdapter;
import brewfindvt.managers.CacheManager;
import brewfindvt.objects.Brewery;
import brewfindvt.objects.EventSummary;

/**
 * Created by user on 2/19/2016.
 */
public class EventActivityFragment extends Fragment {

    ListView eventList;
    Spinner sortByList;

    CacheManager _cacheManager;
    Map<String, List<EventSummary>> eventMap;
    List<EventSummary> allEvents;

    // FRAGMENT OVERRIDES

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _cacheManager = CacheManager.getInstance();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        sortByList = (Spinner) getActivity().findViewById(R.id.sortByList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.eventSortBy, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortByList.setAdapter(adapter);

        eventList = (ListView) getActivity().findViewById(R.id.eventList);
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                EventSummary toGo = allEvents.get(position);
                ((MainActivity) getActivity()).makeEventPage(toGo);
            }
        });

        populatePage();
        super.onActivityCreated(savedInstanceState);
    }

    public void populatePage(){
        eventMap = _cacheManager.getEventMap();
        allEvents = _cacheManager.getAllEvents();
        for (int i = 0; i < allEvents.size(); i++) {
            EventListViewAdapter adapter = new EventListViewAdapter(getActivity(),
                    R.layout.event_view, allEvents);
            eventList.setAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_activity_fragment, container, false);
    }

}
