package brewfindvt.adapter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Brewery brewItem = getItem(position);

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

        imageMap = cacheManager.getB_logos();
        holder.txtDesc.setText(brewItem.getB_addr1());
        holder.txtTitle.setText(brewItem.getB_name());
        holder.imageView.setImageBitmap(imageMap.get(brewItem.getB_breweryNum()));
        return convertView;
    }
}