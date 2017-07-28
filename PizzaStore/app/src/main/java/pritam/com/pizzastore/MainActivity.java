package pritam.com.pizzastore;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> toppings;
    LinearLayout toppingList1,toppingList2;
    ProgressBar toppingProgress;
    final static String TOPPINGS_LIST="TOPPINGS";
    final static String HOME_DELIVERY="DELIVERY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

       // getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);


        toppingList1 = (LinearLayout) findViewById(R.id.toppingList1);
        toppingList2 = (LinearLayout) findViewById(R.id.toppingList2);
        toppingProgress = (ProgressBar) findViewById(R.id.toppingsProgress);

        toppingProgress.setMax(100);
        toppingProgress.setProgress(0);
        toppings = new ArrayList<String>();
        findViewById(R.id.addToppings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.addToppingTitle).setItems(R.array.toppingList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (toppings.size() == 10)
                            Toast.makeText(getApplicationContext(), "Cannot add more Toppings", Toast.LENGTH_SHORT).show();
                        else {

                            ImageButton toppingButton = new ImageButton(MainActivity.this);
                            toppingButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    v.setVisibility(View.GONE);
                                    if(v.getId()<5)
                                        toppingList1.removeView(v);
                                    else
                                        toppingList2.removeView(v);
                                    toppings.remove(v.getId());
                                    updateImageButtonIds(v.getId());
                                    toppingProgress.setProgress(toppings.size()*10);

                                }
                            });
                            toppingButton.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                            toppingButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            toppingButton.setPadding(8, 8, 8, 8);

                            toppingButton.setId(toppings.size());
                            switch (which) {
                                case 0:
                                    toppingButton.setBackgroundResource(R.drawable.bacon);
                                    toppings.add("Bacon");
                                    break;
                                case 1:
                                    toppingButton.setBackgroundResource(R.drawable.cheese);
                                    toppings.add("Cheese");
                                    break;
                                case 2:
                                    toppingButton.setBackgroundResource(R.drawable.garlic);
                                    toppings.add("Garlic");
                                    break;
                                case 3:
                                    toppingButton.setBackgroundResource(R.drawable.green_pepper);
                                    toppings.add("Green Pepper");
                                    break;
                                case 4:
                                    toppingButton.setBackgroundResource(R.drawable.mushroom);
                                    toppings.add("Mashroom");
                                    break;
                                case 5:
                                    toppingButton.setBackgroundResource(R.drawable.olives);
                                    toppings.add("Olives");
                                    break;
                                case 6:
                                    toppingButton.setBackgroundResource(R.drawable.onion);
                                    toppings.add("Onions");
                                    break;
                                case 7:
                                    toppingButton.setBackgroundResource(R.drawable.red_pepper);
                                    toppings.add("Red Pepper");
                                    break;
                                case 8:
                                    toppingButton.setBackgroundResource(R.drawable.tomato);
                                    toppings.add("Tomato");
                                    break;

                            }
                            if (toppings.size() <= 5)
                                toppingList1.addView(toppingButton);
                            else if (toppings.size() <= 10)
                                toppingList2.addView(toppingButton);

                            toppingProgress.setProgress(toppings.size()*10);
                        }
                    }
                });

                builder.show();
            }
        });

        findViewById(R.id.removeToppings).setOnClickListener(new View.OnClickListener() {
            // dialogListenr For AlertDiaolog
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            toppingList1.removeAllViews();
                            toppingList2.removeAllViews();
                            toppings.clear();
                            toppingProgress.setProgress(0);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });


        findViewById(R.id.checkoutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences =MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("TOPPINGS",toppings.toString());
                editor.commit();

                CheckBox isDeliverySelected = (CheckBox) findViewById(R.id.homeDelivery);
                Intent intent = new Intent(MainActivity.this,OrderDetails.class);
                intent.putExtra(TOPPINGS_LIST,toppings);
                intent.putExtra(HOME_DELIVERY,isDeliverySelected.isChecked());
                startActivity(intent);

            }
        });


        CheckBox loadPrevOrder = (CheckBox) findViewById(R.id.prevOrderCheckBox);
        loadPrevOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    SharedPreferences sharedPreferences = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                    String toppingList=sharedPreferences.getString("TOPPINGS","NO VALUE");
                    Log.d("Demo","-"+toppingList+"-");
                    if(toppingList.equals("NO VALUE")|| toppingList.equals("[]"))
                        Toast.makeText(getApplicationContext(),"No previous Orders to load",Toast.LENGTH_SHORT).show();
                    else
                    {
                        toppingList1.removeAllViews();
                        toppingList2.removeAllViews();
                        toppings.clear();
                        toppingProgress.setProgress(0);
                        toppings = new ArrayList<String>();
                        String toppingString = toppingList.substring(1,toppingList.length()-1);
                        Log.d("Demo",toppingString);
                        for(String str: toppingString.split(","))
                        {
                            ImageButton toppingButton = new ImageButton(MainActivity.this);
                            toppingButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    v.setVisibility(View.GONE);
                                    if(v.getId()<5)
                                        toppingList1.removeView(v);
                                    else
                                        toppingList2.removeView(v);
                                    toppings.remove(v.getId());
                                    updateImageButtonIds(v.getId());
                                    toppingProgress.setProgress(toppings.size()*10);

                                }
                            });
                            toppingButton.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                            toppingButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            toppingButton.setPadding(8, 8, 8, 8);

                            toppingButton.setId(toppings.size());
                            Log.d("Demo",str);
                            switch (str.trim()) {
                                case "Bacon":
                                    toppingButton.setBackgroundResource(R.drawable.bacon);
                                    toppings.add("Bacon");
                                    break;
                                case "Cheese":
                                    toppingButton.setBackgroundResource(R.drawable.cheese);
                                    toppings.add("Cheese");
                                    break;
                                case "Garlic":
                                    toppingButton.setBackgroundResource(R.drawable.garlic);
                                    toppings.add("Garlic");
                                    break;
                                case "Green Pepper":
                                    toppingButton.setBackgroundResource(R.drawable.green_pepper);
                                    toppings.add("Green Pepper");
                                    break;
                                case "Mashroom":
                                    toppingButton.setBackgroundResource(R.drawable.mushroom);
                                    toppings.add("Mashroom");
                                    break;
                                case "Olives":
                                    toppingButton.setBackgroundResource(R.drawable.olives);
                                    toppings.add("Olives");
                                    break;
                                case "Onions":
                                    toppingButton.setBackgroundResource(R.drawable.onion);
                                    toppings.add("Onions");
                                    break;
                                case "Red Pepper":
                                    toppingButton.setBackgroundResource(R.drawable.red_pepper);
                                    toppings.add("Red Pepper");
                                    break;
                                case "Tomato":
                                    toppingButton.setBackgroundResource(R.drawable.tomato);
                                    toppings.add("Tomato");
                                    break;

                            }
                            if (toppings.size() <= 5)
                                toppingList1.addView(toppingButton);
                            else if (toppings.size() <= 10)
                                toppingList2.addView(toppingButton);

                            toppingProgress.setProgress(toppings.size()*10);
                        }
                    }
                }



            }
        });



        // dialogListenr For AlertDiaolog
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };






        /* Code for adding images
        ImageView imageView = new ImageButton(this);






        imageView.setBackgroundResource(R.drawable.bacon);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(8, 8, 8, 8);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.toppingList1);

        linearLayout.addView(imageView);
        imageView = new ImageButton(this);
        imageView.setBackgroundResource(R.drawable.cheese);

        imageView.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(8, 8, 8, 8);
        linearLayout.addView(imageView);
        imageView = new ImageButton(this);
        imageView.setBackgroundResource(R.drawable.red_pepper);

        imageView.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(8, 8, 8, 8);
        linearLayout.addView(imageView);
        imageView = new ImageButton(this);
        imageView.setBackgroundResource(R.drawable.green_pepper);

        imageView.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(8, 8, 8, 8);
        linearLayout.addView(imageView);

        imageView = new ImageButton(this);
        imageView.setBackgroundResource(R.drawable.onion);

        imageView.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(8, 8, 8, 8);
        linearLayout.addView(imageView);*/


    }



    private void updateImageButtonIds(int id) {
        /*LinearLayout toppingList1 = (LinearLayout) findViewById(R.id.toppingList1);
        LinearLayout toppingList2 = (LinearLayout) findViewById(R.id.toppingList2);*/

        if(id<toppings.size())
        {
            for(int i=id;i<toppings.size();i++) {
                ImageButton tempButton = (ImageButton) findViewById(i+1);
                if(i+1<5)
                    toppingList1.removeView(findViewById(i+1));
                else
                    toppingList2.removeView(findViewById(i+1));
                tempButton.setId(i);
                Log.d("Demo","New id is "+i);
                if(i<5)
                    toppingList1.addView(tempButton);
                else
                    toppingList2.addView(tempButton);

            }

        }
    }


}


