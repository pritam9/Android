package pritam.com.inclass09;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pritam on 6/23/16.
 */
public class Post {
    String userId, username, email, pwd,favs;

    public String getFavs() {
        return favs;
    }

    public void setFavs(String favs) {
        this.favs = favs;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }

    public Post(){

    }

    @Override
    public String toString() {
        return "Post{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    public Post(String userId, String username, String title, String body) {
        this.userId = userId;
        this.username = username;
        this.email = title;
        this.pwd = body;
        this.favs="NoFavs";
    }

    public Map<String,String> toMap() {
        Map<String,String> postMap = new HashMap<String, String>();
        postMap.put("email",email);
        postMap.put("username", username);
        postMap.put("pwd",pwd);
        postMap.put("userId",userId);
        postMap.put("favs",favs);
        return postMap;
    }
}
