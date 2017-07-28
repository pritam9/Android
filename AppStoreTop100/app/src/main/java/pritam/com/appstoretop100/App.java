package pritam.com.appstoretop100;

import java.io.Serializable;

/**
 * Created by Pritam on 6/12/16.
 */
public class App implements Serializable {
    int id;
    String app_title, developer_name, url, small_photo_url, large_photo_url, and_app_price, releaseDate;

    public void setId(int id) {
        this.id = id;
    }

    public void setApp_title(String app_title) {
        this.app_title = app_title;
    }

    public void setDeveloper_name(String developer_name) {
        this.developer_name = developer_name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSmall_photo_url(String small_photo_url) {
        this.small_photo_url = small_photo_url;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setLarge_photo_url(String large_photo_url) {
        this.large_photo_url = large_photo_url;
    }

    public void setAnd_app_price(String and_app_price) {
        this.and_app_price = and_app_price;
    }

    public int getId() {
        return id;
    }

    public String getApp_title() {
        return app_title;
    }

    public String getDeveloper_name() {
        return developer_name;
    }

    public String getUrl() {
        return url;
    }

    public String getSmall_photo_url() {
        return small_photo_url;
    }

    public String getLarge_photo_url() {
        return large_photo_url;
    }

    public String getAnd_app_price() {
        return and_app_price;
    }

    @Override
    public String toString() {
        return "App{" +
                "id=" + id +
                ", app_title='" + app_title + '\'' +
                ", developer_name='" + developer_name + '\'' +
                ", url='" + url + '\'' +
                ", small_photo_url='" + small_photo_url + '\'' +
                ", large_photo_url='" + large_photo_url + '\'' +
                ", and_app_price='" + and_app_price + '\'' +
                '}';
    }
}
