package pritam.com.madweather;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pritam on 6/9/16.
 */
public class Weather implements Serializable{
    String name,temp,humidity,pressure;
    ArrayList<WeatherDetails> weatherDetails;

    public void setWeatherDetails(ArrayList<WeatherDetails> weatherDetails) {
        this.weatherDetails = weatherDetails;
    }

    public ArrayList<WeatherDetails> getWeatherDetails() {
        return weatherDetails;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getTemp() {
        return temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }




    @Override
    public String toString() {
        return "Weather{" +
                "name='" + name + '\'' +
                ", temp='" + temp + '\'' +
                ", humidity='" + humidity + '\'' +
                ", pressure='" + pressure + '\'' +
                ", WeatherDetails='"+weatherDetails+'\'' +
                '}';
    }
}
