package pritam.com.inclass09;

import java.io.Serializable;

/**
 * Created by Pritam on 6/14/16.
 */
public class App implements Serializable {
    int id;
    String app_title, developer_name, url, small_photo_url, large_photo_url, and_app_price, releaseDate,category;
    boolean isFavourite;

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        App other = (App) obj;
        if (id != other.id)
            return false;
        return true;

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }
}
