package info.juanmendez.droidwp.service;

import android.util.Log;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.HttpsClient;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import info.juanmendez.droidwp.MainActivity;
import info.juanmendez.droidwp.model.Band;

/**
 * Created by Juan on 2/14/2015.
 */
@EBean
public class JsonReader {

    private String jsonString = "";

    @RootContext
    MainActivity activity;

    @HttpsClient
    HttpClient httpsClient;

    @Background
    public void readJson(URI uri ) {
        try {
            _saveJson( uri );
            List<Band> bands = _parseJson();
            activity.loadContent( bands );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Background
    public void readJson( String json )
    {
        jsonString = json;
        List<Band> bands = _parseJson();
        activity.loadContent( bands );
    }

    public String getJsonString() {
        return jsonString;
    }

    private void _saveJson( URI uri ) throws Exception
    {
        HttpGet httpget = new HttpGet(uri.toString());
        HttpResponse response = httpsClient.execute(httpget);

        if( response.getStatusLine().getStatusCode() == 200 )
        {
            InputStream input = response.getEntity().getContent();
            StringWriter writer = new StringWriter();
            IOUtils.copy(input, writer);
            jsonString = writer.toString();
        }
    }

    private ArrayList<Band> _parseJson()
    {
        ArrayList<Band> bands = new ArrayList<Band>();
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(jsonString);

            JSONObject b;

            int i, len = jsonArray.length();

            for (i = 0; i < len; i++) {
                b = jsonArray.getJSONObject(i);
                bands.add(new Band(b));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bands;
    }
}

