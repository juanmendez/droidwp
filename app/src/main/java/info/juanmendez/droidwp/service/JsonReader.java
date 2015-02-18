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
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import info.juanmendez.droidwp.MainActivity;
import info.juanmendez.droidwp.model.Band;

/**
 * Created by Juan on 2/14/2015.
 */
@EBean
public class JsonReader {

    @RootContext
    MainActivity activity;

    @HttpsClient
    HttpClient httpsClient;

    @AfterInject
    void afterInject() {
    }

    @Background
    public void readJson(String url) {

        try {
            List<Band> bands = this._readJson( url );
            activity.loadContent( bands );
        } catch (ClientProtocolException e) {
            Log.e( MainActivity.tag, "1 " +  e.getClass() + " " + e.getMessage() );
        } catch (Exception e) {

            Log.e( MainActivity.tag, "2 " +  e.getClass() + " " +e.getMessage() );
        }
    }

    private List<Band> _readJson( String url ) throws Exception
    {
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpsClient.execute(httpget);
        ArrayList<Band> bands = new ArrayList<Band>();

        if( response.getStatusLine().getStatusCode() == 200 )
        {
            InputStream input = response.getEntity().getContent();
            StringWriter writer = new StringWriter();
            IOUtils.copy(input, writer);
            String theString = writer.toString();
            JSONArray jsonArray = new JSONArray(theString);
            JSONObject b;

            int i, len = jsonArray.length();

            for (i = 0; i < len; i++) {
                b = jsonArray.getJSONObject(i);
                bands.add(new Band(b));
            }
        }

        return bands;
    }
}

