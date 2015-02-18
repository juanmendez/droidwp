package info.juanmendez.droidwp.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Juan on 2/14/2015.
 */
public class Musician {
    String name;
    String image;

    Musician(JSONObject json) {
        try {
            name = json.getString("name");
            image = json.getString("image");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
