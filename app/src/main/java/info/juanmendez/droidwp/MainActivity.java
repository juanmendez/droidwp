package info.juanmendez.droidwp;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

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

    public static final String tag = "info.juanmendez.droidwp";


    @AfterViews
    void afterViews() {
        jsonReader.readJson("http://juanmendez.info/wp-admin/admin-ajax.php?action=droid_json_content&type=band-musician");
    }

    @UiThread
    public void loadContent( List<Band> bands ) {
        adapter.setList( bands );
        list.setAdapter( adapter );
    }

    @Override
    protected void onPause() {
        super.onPause();
        notify("onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        notify("onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        notify("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notify("onDestroy");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        notify("onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        notify("onSaveInstanceState");
    }

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
