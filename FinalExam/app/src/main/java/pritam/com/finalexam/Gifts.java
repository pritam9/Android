package pritam.com.finalexam;

import java.io.Serializable;

/**
 * Created by Pritam on 6/28/16.
 */
public class Gifts implements Serializable{
    String id,gift,imageUrl;
    long price;

    public Gifts() {
    }

    public String getId() {
        return id;
    }

    public String getGift() {
        return gift;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public long getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
