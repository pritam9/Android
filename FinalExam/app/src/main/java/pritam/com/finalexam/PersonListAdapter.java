package pritam.com.finalexam;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pritam on 6/28/16.
 */
public class PersonListAdapter extends ArrayAdapter {
    Context context;
    int resource;
    List<Person> objects;
    public PersonListAdapter(Context context, int resource, List objects) {
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
        Person person = objects.get(position);

        TextView person_name = (TextView) convertView.findViewById(R.id.person_name);
        TextView expenses = (TextView) convertView.findViewById(R.id.expenses);
        TextView noOfItems = (TextView) convertView.findViewById(R.id.noOfItems);
        int gifts=0;
        if(person.getMyGifts()!=null)
            gifts=person.getMyGifts().size();

        //ImageView isRead = (ImageView) convertView.findViewById(R.id.isRead);

        person_name.setText(person.getName());
        expenses.setText(person.getExpenses()+"/"+person.getBudgetLimit()+"$");
        noOfItems.setText(gifts+" gifts bought");
        if (person.getExpenses()==person.getBudgetLimit())
            expenses.setTextColor(Color.GREEN);
        else if(person.getExpenses()==0)
            expenses.setTextColor(Color.GRAY);
        else
            expenses.setTextColor(Color.RED);





        return convertView;
    }


}
