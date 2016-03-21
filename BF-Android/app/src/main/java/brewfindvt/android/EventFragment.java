package brewfindvt.android;

import android.app.usage.UsageEvents;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import brewfindvt.objects.EventSummary;

/**
 * Created by user on 2/28/2016.
 */
public class EventFragment extends Fragment {

    EventSummary newEvent;
    TextView name;
    TextView description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        name = (TextView) getActivity().findViewById(R.id.eventName);
        description = (TextView) getActivity().findViewById(R.id.eventDesc);

        if(newEvent != null) {
            populateEvent();
        }
    }

    public void setNewEvent(EventSummary e) {
        this.newEvent = e;
    }

    public void populateEvent() {
        name.setText(newEvent.getName());
        description.setText(newEvent.getDescription());
    }
}
