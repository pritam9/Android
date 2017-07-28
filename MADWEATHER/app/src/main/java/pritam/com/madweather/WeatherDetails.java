package pritam.com.madweather;

import java.io.Serializable;

/**
 * Created by Pritam on 6/9/16.
 */
public class WeatherDetails implements Serializable{
    String description,icons;



    public void setIcons(String icons) {
        this.icons = icons;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getIcons() {
        return icons;
    }

    @Override
    public String toString() {
        return "WeatherDetails{" +
                "description='" + description + '\'' +
                ", icons='" + icons + '\'' +
                '}';
    }
}
