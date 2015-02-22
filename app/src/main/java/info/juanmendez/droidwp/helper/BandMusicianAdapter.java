package info.juanmendez.droidwp.helper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import info.juanmendez.droidwp.BandItemView;
import info.juanmendez.droidwp.BandItemView_;
import info.juanmendez.droidwp.MusicianItemView;
import info.juanmendez.droidwp.MusicianItemView_;
import info.juanmendez.droidwp.model.Band;
import info.juanmendez.droidwp.model.ListItemInterface;
import info.juanmendez.droidwp.model.Musician;

/**
 * Created by Juan on 2/14/2015.
 */
@EBean
public class BandMusicianAdapter extends BaseAdapter
{
    List<ListItemInterface> items;

    @RootContext
    Context context;

    public void setList( List<Band> bands )
    {
        items = new ArrayList<ListItemInterface>();

        for( Band band: bands )
        {
            items.add( band );

            for(Musician musician: band.getMusicians() )
            {
                items.add( musician );
            }
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ListItemInterface getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return(2);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Band ) {
            return(0);
        }

        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (getItemViewType(position) == 0) {

            BandItemView bandView;

            if (convertView == null) {
                bandView = BandItemView_.build(context);
            } else {
                bandView = (BandItemView) convertView;
            }

            bandView.bind(getItem(position));

            return bandView;
         }
        else
        if (getItemViewType(position) == 1) {

            MusicianItemView view;

            if (convertView == null) {
                view = MusicianItemView_.build(context);
            } else {
                view = (MusicianItemView) convertView;
            }

            view.bind(getItem(position));

            return view;
        }

        return null;
    }

}
