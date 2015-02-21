package info.juanmendez.droidwp.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Juan on 2/14/2015.
 */
public class Musician {

    //required by Jackson Jr.
    public Musician()
    {
    }

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
