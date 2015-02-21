package info.juanmendez.droidwp;

import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import info.juanmendez.droidwp.helper.BandAdapter;
import info.juanmendez.droidwp.model.Band;
import info.juanmendez.droidwp.service.JsonReader;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity implements JsonReader.JsonReaderHandler {

    @ViewById
    ListView list;

    @Bean
    JsonReader jsonReader;

    @Bean
    BandAdapter adapter;

    @StringRes(R.string.ajax_url)
    String ajax;

    public static final String tag = "info.juanmendez.droidwp";

    @InstanceState
    public String jsonSource;

    @AfterViews
    void afterViews() {

        try {

            if( jsonSource != null )
            {
                jsonReader.readJson(jsonSource, this );
            }
            else
            {
                URI uri = new URI( ajax + "?action=droid_json_content&type=band-musician" );
                jsonReader.readJson( uri, this );
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onContentReady(List<Band> bands, String jsonString ) {

        jsonSource = jsonString;
        adapter.setList( bands );
        list.setAdapter( adapter );
    }
}
