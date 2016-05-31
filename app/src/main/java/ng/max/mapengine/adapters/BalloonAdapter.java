package ng.max.mapengine.adapters;

/**
 * Created by babatundedennis on 4/11/16.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

import ng.max.mapengine.R;

public class BalloonAdapter implements InfoWindowAdapter {
    LayoutInflater inflater = null;
    Context context;

    private TextView textViewTitle;

    public BalloonAdapter(LayoutInflater inflater, Context context) {
        this.inflater = inflater;
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View v = inflater.inflate(R.layout.info_window_layout, null);
        if (marker != null) {
            textViewTitle = (TextView) v.findViewById(R.id.textViewTitle);
            textViewTitle.setText(marker.getTitle());

            TextView arrow_icon = (TextView) v.findViewById(R.id.arrow_icon);
            arrow_icon.setText(String.valueOf((char) 0xe907));
            arrow_icon.setTypeface(Typeface.createFromAsset(context.getAssets(), "icomoon.ttf"));
        }
        return (v);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return (null);
    }
}