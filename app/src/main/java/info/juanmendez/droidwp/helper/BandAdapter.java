package info.juanmendez.droidwp.helper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import info.juanmendez.droidwp.BandItemView;
import info.juanmendez.droidwp.BandItemView_;
import info.juanmendez.droidwp.model.Band;

/**
 * Created by Juan on 2/14/2015.
 */
@EBean
public class BandAdapter extends BaseAdapter
{
    List<Band> bands;

    @RootContext
    Context context;

    public void setList( List<Band> bands )
    {
        this.bands = bands;
    }

    @Override
    public int getCount() {
        return bands.size();
    }

    @Override
    public Band getItem(int position) {
        return bands.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BandItemView bandView;

        if (convertView == null) {
            bandView = BandItemView_.build(context);
        } else {
            bandView = (BandItemView) convertView;
        }

        bandView.bind(getItem(position));

        return bandView;
    }
}
