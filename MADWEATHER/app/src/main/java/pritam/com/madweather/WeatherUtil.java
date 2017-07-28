package pritam.com.madweather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Pritam on 6/9/16.
 */
public class WeatherUtil {
    public static Object WeatherJSONParser;

    public static Weather parseWeather(String data) throws JSONException {
        JSONObject root= new JSONObject(data);
        JSONObject city =root.optJSONObject("city");
       // JSONObject root1 = new JSONObject("list");
        //Log.d("DemoList1",root1.toString());
        String code=root.getString("cod");
        if(code.equals("404"))
            return null;
        else {
            Log.d("Demo", data);
            Log.d("Demo", root.getJSONObject("city").getString("name"));
            JSONArray list = root.getJSONArray("list");
            JSONArray weatherIcons = list.getJSONObject(0).getJSONArray("weather");
            //Log.d("Demo",list.toString());

            Weather weather = new Weather();
            weather.setName(root.getJSONObject("city").getString("name"));
            weather.setTemp(list.getJSONObject(0).getJSONObject("main").getString("temp"));
            weather.setHumidity(list.getJSONObject(0).getJSONObject("main").getString("humidity"));
            ArrayList<WeatherDetails> weatherDetails = new ArrayList<>();
            for (int i=0;i<weatherIcons.length();i++)
            {
                if(i==3)
                    break;
                else
                {
                    WeatherDetails wDetails=new WeatherDetails();
                    wDetails.setIcons(weatherIcons.getJSONObject(i).getString("icon"));
                    wDetails.setDescription(weatherIcons.getJSONObject(i).getString("description"));
                    weatherDetails.add(wDetails);
                }
            }
            //weather.setIcons(weatherIcons.getJSONObject(0).getString("icon"));
            weather.setPressure(list.getJSONObject(0).getJSONObject("main").getString("pressure"));
            weather.setWeatherDetails(weatherDetails);
            //weather.setDescription(root.getJSONObject("city").getString("temp"));
        /*Contact c=new Contact();
        c.setName(root.getString("name"));
        c.setAge(root.getInt("age"));
        c.setAddress(root.getJSONObject("address").getString("streetAddress")+root.getJSONObject("address").getString("city"));
        JSONObject number1=root.getJSONArray("phoneNumber").getJSONObject(0);
        JSONObject number2=root.getJSONArray("phoneNumber").getJSONObject(1);

        c.setPhone(number1.getString("type")+":"+number1.getString("number")+"  "+number2.getString("type")+":"+number2.getString("number"));


        result.add(c);

        Log.d("Demo",root.toString());
*/


            // return result;

            return weather;
        }
    }
}
