package pritam.com.madweather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements GetWeatherDetails.WeatherDisplayer {
    ProgressDialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.getWeatherButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText city = (EditText) findViewById(R.id.cityName);
                Switch metric = (Switch) findViewById(R.id.metricSwitch);
                String cityAddress=city.getText().toString();
                String isMetric= "Imperial";
                if(metric.isChecked())
                    isMetric="Metric";
                new GetWeatherDetails(MainActivity.this).execute(cityAddress,isMetric);
            }
        });
    }

    @Override
    public void displayWeather(Weather data) {
        pb.dismiss();
        if(data==null)
            Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
        else
        {
            Intent intent=new Intent(MainActivity.this,WeatherDisplay.class);
            Switch metric = (Switch) findViewById(R.id.metricSwitch);
            intent.putExtra("OBJECT",data);
            EditText city = (EditText) findViewById(R.id.cityName);
            String cityAddress=city.getText().toString();
            intent.putExtra("CITY",cityAddress);
            intent.putExtra("isImp",metric.isChecked());
            startActivity(intent);

            //Toast.makeText(MainActivity.this, "Data is -"+data.toString(), Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void startLoading() {
        pb=new ProgressDialog(MainActivity.this);
        pb.setMessage("Loading...");
        pb.setCancelable(false);
        pb.show();

    }
}
