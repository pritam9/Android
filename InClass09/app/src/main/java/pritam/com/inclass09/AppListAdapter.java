package pritam.com.inclass09;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Pritam on 6/14/16.
 */
public class AppListAdapter extends ArrayAdapter<App> {
    Context context;
    List<App> obApps;
    int resource;
    public AppListAdapter(Context context, int resource, List<App> objects) {
        super(context, resource, objects);
        this.context=context;
        obApps=objects;
        this.resource=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(resource,parent,false);
        }
        //Log.d("Demo",position+"-");
        TextView appName = (TextView) convertView.findViewById(R.id.appName);
        TextView appDev = (TextView) convertView.findViewById(R.id.appDeveloper);
        TextView appPrice = (TextView) convertView.findViewById(R.id.price);
        TextView appDate = (TextView) convertView.findViewById(R.id.releaseDate);
        TextView appCategory = (TextView) convertView.findViewById(R.id.category);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.appImage);
        ImageView isFav = (ImageView) convertView.findViewById(R.id.favStar);
        appName.setText(obApps.get(position).getApp_title());
        appDev.setText(obApps.get(position).getDeveloper_name());
        appPrice.setText("$"+obApps.get(position).getAnd_app_price());
        appDate.setText(obApps.get(position).getReleaseDate());
        appCategory.setText(obApps.get(position).getCategory());
        if (obApps.get(position).isFavourite())
            isFav.setImageResource(android.R.drawable.star_big_on);
        else
            isFav.setImageResource(android.R.drawable.star_big_off);
        Picasso.with(context).load(obApps.get(position).getLarge_photo_url()).into(imageView);
        //convertView.setBackgroundColor();

        return convertView;
    }
}
