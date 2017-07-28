package pritam.com.finalexam;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Pritam on 6/27/16.
 */
public class InBoxAdapter extends ArrayAdapter {
    Context context;
    int resource;
    List obj;//List<Message> objects

    public InBoxAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context=context;
        this.obj=objects;
        this.resource=resource;
    }

    /*@Override
    public int getCount() {
        return obj.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView=inflater.inflate(R.layout.inbox_view,parent,false);
        }
        Message message = obj.get(position);

        TextView sender = (TextView) convertView.findViewById(R.id.sender);
        ImageView isRead = (ImageView) convertView.findViewById(R.id.isRead);

        sender.setText(message.getSender());
        if (message.isRead())
            isRead.setImageResource(android.R.drawable.presence_invisible);





        return convertView;
    }*/
}
