package brewfindvt.adapter;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import brewfindvt.android.R;
import brewfindvt.managers.CacheManager;
import brewfindvt.objects.Brewery;

public class BreweryListViewAdapter extends ArrayAdapter<Brewery> {
    private CacheManager cacheManager;
    public Map<Integer, Bitmap> imageMap;


    Context context;

    public BreweryListViewAdapter(Context context, int resourceId,
                                  List<Brewery> items) {
        super(context, resourceId, items);
        this.context = context;
        this.cacheManager = CacheManager.getInstance();
        this.imageMap = cacheManager.getB_logos();
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtName;
        TextView txtAddr;
        TextView txtTown;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Brewery brewItem = getItem(position);

        if(this.imageMap == null || this.imageMap.isEmpty()) {
            this.imageMap = this.cacheManager.getB_logos();
        }

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.brew_view, null);
            holder = new ViewHolder();
            holder.txtTown = (TextView) convertView.findViewById(R.id.town);
            holder.txtAddr = (TextView) convertView.findViewById(R.id.addr);
            holder.txtName = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtTown.setText(brewItem.getB_city() + ", VT");
        holder.txtAddr.setText(brewItem.getB_addr1());
        holder.txtName.setText(brewItem.getB_name());
        holder.imageView.setImageBitmap(imageMap.get(brewItem.getB_breweryNum()));
        return convertView;
    }
}