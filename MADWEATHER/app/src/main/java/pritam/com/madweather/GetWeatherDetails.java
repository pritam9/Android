package pritam.com.madweather;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Pritam on 6/9/16.
 */
public class GetWeatherDetails extends AsyncTask<String, Void, Weather> {
    WeatherDisplayer weatherDisplayer;

    public GetWeatherDetails(MainActivity mainActivity) {
        this.weatherDisplayer = mainActivity;
    }

    @Override
    protected Weather doInBackground(String... params) {
        try {
            String encodedUrl=getURL(params[0],params[1]);
            Log.d("Demo",encodedUrl);
            URL url = new URL(encodedUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statuscode = con.getResponseCode();
            if (statuscode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    sb.append(line);
                    line = reader.readLine();
                }
                Log.d("Demo", sb.toString());

                return WeatherUtil.parseWeather(sb.toString());
            }
            else Log.d("Demo","Error!");
        }catch (MalformedURLException a){
            a.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Weather weather) {
        super.onPostExecute(weather);
        weatherDisplayer.displayWeather(weather);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        weatherDisplayer.startLoading();
    }

    private String getURL(String param, String param1) {

        String url ="http://api.openweathermap.org/data/2.5/forecast/city?";
        url+="q="+param+"&units="+param1+"&APPID=c98d19bc77fc4754c3e43237e60d9194";
        return url;
    }

    public interface WeatherDisplayer{
        public void displayWeather(Weather weather);
        public void startLoading();
    }
}
