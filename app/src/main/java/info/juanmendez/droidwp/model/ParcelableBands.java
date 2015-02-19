package info.juanmendez.droidwp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.androidannotations.annotations.EBean;

import java.util.List;

import info.juanmendez.droidwp.MainActivity;

/**
 * Created by Juan on 2/18/2015.
 */
@EBean
public class ParcelableBands implements Parcelable {

    private String jsonString = "";

    public ParcelableBands(){
        Log.e(MainActivity.tag, "parcelable bands created");
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public void setJsonString( Parcel in ){
        this.jsonString = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( jsonString );
    }
}