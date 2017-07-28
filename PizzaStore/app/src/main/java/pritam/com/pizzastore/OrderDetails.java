package pritam.com.pizzastore;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OrderDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);


        float orderTotal= (float) 6.50;
        //Log.d("Demo","Text Test Knock Knock");
        /*SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_APPEND); //PreferenceManager.getDefaultSharedPreferences(this); //
        String toppings = sharedPreferences.getString("TOPPINGS","TestDefaultValue");*/
        ArrayList<String> toppings = (ArrayList<String>) getIntent().getSerializableExtra(MainActivity.TOPPINGS_LIST);
        orderTotal+=(toppings.size()*1.50);
        boolean isDelivery = getIntent().getBooleanExtra(MainActivity.HOME_DELIVERY, false);
        String toppingsList=toppings.toString().substring(1,toppings.toString().length()-1);
        Log.d("Demo",toppingsList);
        TextView toppingList = (TextView) findViewById(R.id.toppingsAdded);
        TextView orderTotalFinal = (TextView) findViewById(R.id.orderTotal);
        TextView toppingCharges = (TextView) findViewById(R.id.toppingCharges);
        TextView basePrice = (TextView) findViewById(R.id.basePrice);
        TextView deliveryCharges = (TextView) findViewById(R.id.deliveryCharges);
        basePrice.setText(6.50+"$");
        toppingCharges.setText((toppings.size()*1.50)+"$");
        if(isDelivery)
        {
            orderTotal+=2;
            deliveryCharges.setText(2.00+"$");
        }
        else
            deliveryCharges.setText(0.00+"$");
        orderTotalFinal.setText(orderTotal+"$");
        toppingList.setText(toppingsList);


        findViewById(R.id.finishButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
