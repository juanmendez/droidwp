package info.juanmendez.droidwp.service;

import com.fasterxml.jackson.jr.ob.JSON;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.HttpsClient;
import org.androidannotations.annotations.UiThread;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.util.List;

import info.juanmendez.droidwp.model.Band;


/**
 * Created by Juan on 2/14/2015.
 */
@EBean
public class JsonReader {

    private String jsonString = "";
    private List<Band> bands;

    @HttpsClient
    HttpClient httpsClient;

    JsonReaderHandler handler;

    @Background
    public void readJson(URI uri, JsonReaderHandler h ) {
        try {
            handler = h;
            _saveJson( uri );

            parse();
            onContentReady();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Background
    public void readJson( String json, JsonReaderHandler h  )
    {
        handler = h;
        jsonString = json;

        parse();
        onContentReady();
    }

    private void parse()
    {
        try {
           bands =  JSON.std.listOfFrom(Band.class, jsonString);

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @UiThread
    private void onContentReady()
    {
        if( bands != null )
        {
            handler.onContentReady( bands, jsonString );
        }

    }

    public interface JsonReaderHandler{
        void onContentReady( List<Band> bands, String jsonString );
    }
}

