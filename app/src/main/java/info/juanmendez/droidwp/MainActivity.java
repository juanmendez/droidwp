package info.juanmendez.droidwp;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import info.juanmendez.droidwp.helper.BandAdapter;
import info.juanmendez.droidwp.model.Band;
import info.juanmendez.droidwp.model.ParcelableBands;
import info.juanmendez.droidwp.service.JsonReader;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    @ViewById
    ListView list;

    @Bean
    JsonReader jsonReader;

    @Bean
    BandAdapter adapter;

    public static final String tag = "info.juanmendez.droidwp";

    @InstanceState
    int bands_id = 1;

    /**
     * when the app starts, savedInstanceState is null, so the
     * line below will create a new ParcelableBands
     * otherwise, it will be pulled out from its parcelable format.
     private void restoreSavedInstanceState_(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
         return ;
         }

         parcelableBands = savedInstanceState.getParcelable("parcelableBands");
     }
     */
    @InstanceState
    public ParcelableBands parcelableBands = new ParcelableBands();


    @AfterViews
    void afterViews() {

        try {

            if( !parcelableBands.getJsonString().isEmpty() )
            {
                jsonReader.readJson( parcelableBands.getJsonString() );
            }
            else
            {
                URI uri = new URI( "http://juanmendez.info/wp-admin/admin-ajax.php?action=droid_json_content&type=band-musician" );
                jsonReader.readJson( uri );
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @UiThread
    public void loadContent( List<Band> bands ) {

        parcelableBands.setJsonString( jsonReader.getJsonString() );
        adapter.setList( bands );
        list.setAdapter( adapter );
    }

    @Override
    protected void onPause() {
        super.onPause();
        //notify("onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //notify("onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //notify("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //notify("onDestroy");
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
