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
        holder.month.setText(eventItem.getMonth());
        holder.day.setText(eventItem.getDay());
        holder.year.setText(eventItem.getYear());

        return convertView;
    }
}
