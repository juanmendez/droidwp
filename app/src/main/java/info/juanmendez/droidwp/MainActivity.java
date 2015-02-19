package info.juanmendez.droidwp;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import info.juanmendez.droidwp.helper.BandAdapter;
import info.juanmendez.droidwp.model.Band;
import info.juanmendez.droidwp.service.JsonReader;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

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
    public String bandsJson;

    @AfterViews
    void afterViews() {

        try {

            if( bandsJson != null )
            {
                jsonReader.readJson( bandsJson );
            }
            else
            {
                URI uri = new URI( ajax + "?action=droid_json_content&type=band-musician" );
                jsonReader.readJson( uri );
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @UiThread
    public void loadContent( List<Band> bands )
    {
        bandsJson = jsonReader.getJsonString();
        adapter.setList( bands );
        list.setAdapter( adapter );
    }

    /**
     * this method is for seeing the lifecycle, and is part of
     * http://www.vogella.com/tutorials/AndroidLifeCycle/article.html
     * @param methodName
     */
    private void notify(String methodName) {
        String name = this.getClass().getName();
        String[] strings = name.split("\\.");
        Notification noti = new Notification.Builder(this)
                .setContentTitle(methodName + " " + strings[strings.length - 1]).setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentText(name).build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify((int) System.currentTimeMillis(), noti);
    }

}
