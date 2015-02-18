package info.juanmendez.droidwp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan on 2/14/2015.
 */
public class Band {
    public String name;
    public String image;
    List<Musician> musicians;

    public Band(JSONObject json) {

        try {
            name = json.getString("name");
            image = json.getString("image");

            JSONArray ms = json.getJSONArray("musicians");

            musicians = new ArrayList<Musician>();
            int i, len = ms.length();

            for (i = 0; i < len; i++) {
                musicians.add(new Musician(ms.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
