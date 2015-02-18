package info.juanmendez.droidwp;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import info.juanmendez.droidwp.model.Band;

/**
 * Created by Juan on 2/14/2015.
 */
@EViewGroup(R.layout.band_layout)
public class BandItemView extends LinearLayout {
    @ViewById
    TextView band_text;

    @ViewById
    ImageView img;

    public BandItemView( Context context )
    {
        super(context);
    }

    public void bind( Band band ){
        band_text.setText( band.name );
        img.setScaleType( ImageView.ScaleType.CENTER_CROP );
        Picasso.with( getContext() ).load( band.image ).placeholder( R.drawable.placeholder ).tag( getContext() ).into( img );
    }
}
