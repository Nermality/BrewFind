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
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        EventSummary eventItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_brew, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtDesc.setText((eventItem.getDescription() == "No description available") ? "" : eventItem.getDescription());
        holder.txtTitle.setText(eventItem.getName());
        //todo Insert image for brewery obejct
        //holder.imageView.setImageResource(brewItem.getB_logoImage());
        // holder.imageView.setImageResource(brewItem.getB_logoImage());

        return convertView;
    }
}
