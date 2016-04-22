package brewfindvt.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import brewfindvt.android.R;
import brewfindvt.objects.Brewery;
import brewfindvt.objects.EventSummary;

/**
 * Created by user on 2/28/2016.
 */
public class EventListViewAdapter extends ArrayAdapter<EventSummary> {

    Context context;

    public EventListViewAdapter(Context context, int resourceId,
                                  List<EventSummary> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView title;
        TextView brewery;
        TextView town;
        TextView month;
        TextView day;
        TextView year;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        EventSummary eventItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.event_view, null);
            holder = new ViewHolder();

            holder.title = (TextView) convertView.findViewById(R.id.eventName);
            holder.brewery = (TextView) convertView.findViewById(R.id.eventBrewery);
            holder.town = (TextView) convertView.findViewById(R.id.eventTown);
            holder.month = (TextView) convertView.findViewById(R.id.eventMonth);
            holder.day = (TextView) convertView.findViewById(R.id.eventDay);
            holder.year = (TextView) convertView.findViewById(R.id.eventYear);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.title.setText(eventItem.getName());
        holder.brewery.setText(eventItem.getBreweryName());
        holder.town.setText(eventItem.getLocation());
        String monthText = "";
        switch(eventItem.getMonth()) {
            case 1:
                monthText = "JAN";
                break;
            case 2:
                monthText = "FEB";
                break;
            case 3:
                monthText = "MAR";
                break;
            case 4:
                monthText = "APR";
                break;
            case 5:
                monthText = "MAY";
                break;
            case 6:
                monthText = "JUN";
                break;
            case 7:
                monthText = "JUL";
                break;
            case 8:
                monthText = "AUG";
                break;
            case 9:
                monthText = "SEP";
                break;
            case 10:
                monthText = "OCT";
                break;
            case 11:
                monthText = "NOV";
                break;
            case 12:
                monthText = "DEC";
                break;
        }

        holder.month.setText(monthText);
        holder.day.setText(String.valueOf(eventItem.getDay()));
        holder.year.setText(String.valueOf(eventItem.getYear()));

        return convertView;
    }
}
