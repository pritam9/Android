package pritam.com.inclass03;

import java.io.Serializable;

/**
 * Created by Pritam on 5/31/16.
 */
public class Student implements Serializable{
    private String name;
    private String email_id;
    private String favourite_language;

    public Student(String name, String email_id, String favourite_language) {
        this.name = name;
        this.email_id = email_id;
        this.favourite_language = favourite_language;
    }

    public String getName() {
        return name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getFavourite_language() {
        return favourite_language;
    }
}
