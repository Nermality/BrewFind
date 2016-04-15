package brewfindvt.android;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import brewfindvt.objects.EventSummary;

/**
 * Created by user on 2/28/2016.
 */
public class EventFragment extends Fragment implements View.OnClickListener {

    EventSummary newEvent;

    TextView _name;
    TextView _host;
    TextView _startDate;
    TextView _endDate;
    TextView _locationText;
    TextView _description;

    Button _dateButton;
    Button _locationButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _name = (TextView) getActivity().findViewById(R.id.eventName);
        _host = (TextView) getActivity().findViewById(R.id.eventHost);
        _startDate = (TextView) getActivity().findViewById(R.id.startDate);
        _endDate = (TextView) getActivity().findViewById(R.id.endDate);
        _locationText = (TextView) getActivity().findViewById(R.id.locationText);
        _description = (TextView) getActivity().findViewById(R.id.descriptionText);

        _dateButton = (Button) getActivity().findViewById(R.id.calendarButton);
        _locationButton = (Button) getActivity().findViewById(R.id.mapButton);

        _dateButton.setOnClickListener(this);
        _locationButton.setOnClickListener(this);

        if(newEvent != null) {
            populateEvent();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.calendarButton:
                goToCalendar();
            case R.id.mapButton:
                goToMap();
        }
    }

    public void goToCalendar() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newEvent.getHtmlLink()));
        startActivity(intent);
    }

    public void goToMap() {
        String url = "http://reddit.com";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void setNewEvent(EventSummary e) {
        this.newEvent = e;
    }

    public void populateEvent() {
        _name.setText(newEvent.getName());
        _host.setText("Hosted by: " + newEvent.getBreweryName());
        _locationText.setText(newEvent.getLocation());
        if(_locationText.getText().equals("TBD")) {
            _locationButton.setEnabled(false);
        } else {
            _locationButton.setEnabled(true);
        }
        if(newEvent.getHtmlLink() == null || newEvent.getHtmlLink() == "") {
            _dateButton.setEnabled(false);
        } else {
            _dateButton.setEnabled(true);
        }
        _description.setText(newEvent.getDescription());
        setDate();
    }

    public void setDate() {
        SimpleDateFormat sdfParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
        Calendar cal = Calendar.getInstance();
        Date date;

        if(newEvent.getStartDate() == null) {
            _startDate.setText("Starting: TBD");
        } else {
            Date tempStart;

            try {
                date = sdfParser.parse(newEvent.getStartDate());
              //  tempStart = sdfParser.parse(newEvent.getStartDate());
                cal.setTime(date);
            } catch (Exception e) {
                tempStart = null;
                Toast.makeText(getActivity().getApplicationContext(), "There was an issue parsing dates, we're sorry", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            if(cal == null) {
                _startDate.setText("Starting: TBD");
            } else {

                _startDate.setText(cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.YEAR)+"\n"+
                                    cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));
            }
         //   sdfParser.format(tempStart)
        }
        if(newEvent.getEndDate() == null) {
            _endDate.setText("Ending: None specified");
        } else {
            Date tempEnd;
            try {
                date = sdfParser.parse(newEvent.getEndDate());
                cal.setTime(date);
            } catch (Exception e) {
                e.printStackTrace();
                tempEnd = null;
                Toast.makeText(getActivity().getApplicationContext(), "There was an issue parsing dates, we're sorry", Toast.LENGTH_SHORT).show();
            }

            if(cal == null) {
                _endDate.setText("Ending: None specified");
            } else {
                _endDate.setText(cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.YEAR)+"\n"+
                        cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));
            }
        }
    }
}
