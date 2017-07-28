package pritam.com.madweather;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WeatherDisplay extends AppCompatActivity {
    public static LinearLayout imaggeDisplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weather_display);
        imaggeDisplayer = (LinearLayout) findViewById(R.id.imageDisplayer);

        Weather weather = (Weather) getIntent().getExtras().getSerializable("OBJECT");
        String cityName = getIntent().getExtras().getString("CITY");
        boolean isImp =getIntent().getBooleanExtra("isImp",false);
        getSupportActionBar().setTitle(cityName);

        TextView temp = (TextView) findViewById(R.id.temp);
        TextView humidity = (TextView) findViewById(R.id.humidity);
        TextView pressure = (TextView) findViewById(R.id.pressure);
        TextView wText = (TextView) findViewById(R.id.weather);
        if(isImp)
            temp.setText(weather.getTemp()+" Celsius");
        else
            temp.setText(weather.getTemp()+" Farenheit");
        humidity.setText(weather.getHumidity()+"%");
        pressure.setText(weather.getPressure()+"hPa");

        /*//Test Code

        ArrayList<WeatherDetails> t = new ArrayList<WeatherDetails>();
        t.add(new WeatherDetails("Hi","11d"));
        t.add(new WeatherDetails("Hello","10d"));
        t.add(new WeatherDetails("Wow","04n"));

        weather.setWeatherDetails(t);


        //Delete Above!!*/

        ArrayList<WeatherDetails> wDetails = weather.getWeatherDetails();
        String weatherString = "";
        if (wDetails != null) {
            for (int i = 0; i < wDetails.size(); i++) {
                weatherString += wDetails.get(i).getDescription()+",";
                new GetImage(WeatherDisplay.this).execute(wDetails.get(i).getIcons());
            }
            weatherString=weatherString.substring(0,weatherString.length()-1);
        }
        wText.setText(weatherString);


    }


}
