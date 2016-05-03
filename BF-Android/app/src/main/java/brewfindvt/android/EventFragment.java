package brewfindvt.android;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

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

    private ImageView _isPet;
    private ImageView _isFamily;
    private ImageView _hasFee;
    private ImageView _isOutside;

    Button _dateButton;
    Button _locationButton;

    private Button _descriptionInfoButton;
    private ExpandableRelativeLayout _descriptionLayout;
    private LinearLayout _descriptionInfoBox;

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

        _descriptionInfoButton = (Button) getActivity().findViewById(R.id.descriptionInfoButton);
        _descriptionLayout = (ExpandableRelativeLayout) getActivity().findViewById(R.id.descriptionInfoView);
        _descriptionInfoBox = (LinearLayout) getActivity().findViewById(R.id.descriptionInfoBox);

        _dateButton = (Button) getActivity().findViewById(R.id.calendarButton);
        _locationButton = (Button) getActivity().findViewById(R.id.mapButton);

        _isFamily = (ImageView) getActivity().findViewById(R.id.isFamily);
        _isFamily.setOnClickListener(this);
        _isPet = (ImageView) getActivity().findViewById(R.id.isPet);
        _isPet.setOnClickListener(this);
        _isOutside = (ImageView) getActivity().findViewById(R.id.isOutside);
        _isOutside.setOnClickListener(this);
        _hasFee = (ImageView) getActivity().findViewById(R.id.hasFee);
        _hasFee.setOnClickListener(this);

        _descriptionInfoButton.setOnClickListener(this);
        _dateButton.setOnClickListener(this);
        _locationButton.setOnClickListener(this);

        if(newEvent != null) {
            populateEvent();
        }
        populateContactInfo();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.descriptionInfoButton:
                _descriptionLayout.toggle();
                break;
            case R.id.calendarButton:
                goToCalendar();
            case R.id.mapButton:
                goToMap();
            case R.id.isFamily:
                makeLegendToast(newEvent.getFamFriendly(), "family friendly.");
                break;
            case R.id.isPet:
                makeLegendToast(newEvent.getPetFriendly(), "pet friendly.");
                break;
            case R.id.isOutside:
                makeLegendToast(newEvent.getOutdoor(), "is outside.");
                break;
            case R.id.hasFee:
                makeLegendToastFee(newEvent.getTicketCost());
                break;
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
        setDate();

        setLegendColor(_isFamily, newEvent.getFamFriendly());
        setLegendColor(_isPet, newEvent.getPetFriendly());
        setLegendColor(_isOutside, newEvent.getOutdoor());
        setCostLegend (_hasFee,newEvent.getTicketCost());
        //Add ticket calc
    }

    public void makeLegendToast(Boolean b, String s) {
        String toast;
        if(b == null || !b) {
            toast = "This event does not have " + s;
        } else {
            toast = "This event is " + s;
        }
        Toast.makeText(getActivity().getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
    }

    public void makeLegendToastFee(double c) {
        String toast;
        if(c == 0) {
            toast = "This event does not have a fee";
        } else {
            toast = "Entry cost is $" + c;
        }
        Toast.makeText(getActivity().getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
    }

    public void setDate() {
        SimpleDateFormat sdfParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
        SimpleDateFormat timeOfDay = new SimpleDateFormat("h:mm a");
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
                String time= timeOfDay.format(cal.getTime());
                _startDate.setText("Begin:\n"+ cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.YEAR)+
                        "   "+time);
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
                String time= timeOfDay.format(cal.getTime());
                _endDate.setText("End:\n"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.YEAR)+
                        "    "+time);
            }
        }
    }

    public void populateContactInfo() {
        _descriptionInfoBox.removeAllViews();

        if(newEvent.getDescription() != null) {
            TextView description = new TextView(getActivity().getApplicationContext());
            description.setText(newEvent.getDescription());
            description.setTextSize(18f);
            description.setTextColor(Color.WHITE);
            _descriptionInfoBox.addView(description);
        }

    }
        public void setLegendColor(ImageView i, Boolean b) {
        if(b == null) {
            i.getDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        } else if (b) {
            i.getDrawable().setColorFilter(getActivity().getResources().getColor(R.color.LegIconGreen), PorterDuff.Mode.MULTIPLY);
        } else {
            i.getDrawable().setColorFilter(getActivity().getResources().getColor(R.color.LegIconRed), PorterDuff.Mode.MULTIPLY);
        }
    }


    public void setCostLegend(ImageView i, double c) {
        if (c == 0) {
            i.getDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        } else if(c < 5.01) {
            i.setImageResource(R.mipmap.li_money1);
            i.getDrawable().setColorFilter(getActivity().getResources().getColor(R.color.LegIconGreen), PorterDuff.Mode.MULTIPLY);
        } else if(c < 15.01) {
            i.setImageResource(R.mipmap.li_money2);
            i.getDrawable().setColorFilter(getActivity().getResources().getColor(R.color.LegIconGreen), PorterDuff.Mode.MULTIPLY);
        } else {
            i.setImageResource(R.mipmap.li_money3);
            i.getDrawable().setColorFilter(getActivity().getResources().getColor(R.color.LegIconGreen), PorterDuff.Mode.MULTIPLY);
        }

    }
}
