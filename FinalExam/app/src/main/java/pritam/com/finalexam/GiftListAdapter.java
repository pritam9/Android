package pritam.com.finalexam;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Pritam on 6/28/16.
 */
public class GiftListAdapter extends ArrayAdapter {
    Context context;
    int resource;
    List<Gifts> objects;
    public GiftListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    public int getCount() {
        return objects.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView=inflater.inflate(resource,parent,false);
        }
        Gifts gift = objects.get(position);

        TextView giftName = (TextView) convertView.findViewById(R.id.gift_name);
        TextView price = (TextView) convertView.findViewById(R.id.gift_price);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.giftImage);
        giftName.setText(gift.getGift());
        price.setText(gift.getPrice()+" $");
        Picasso.with(context).load(gift.getImageUrl()).into(imageView);

        return convertView;
    }


}
