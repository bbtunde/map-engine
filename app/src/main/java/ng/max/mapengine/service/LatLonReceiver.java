package ng.max.mapengine.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ng.max.mapengine.utils.Constants;

/**
 * Created by babatundedennis on 4/8/16.
 */
public class LatLonReceiver extends BroadcastReceiver {

    public static final String ACTION_SUCCESS = "SYNC_SUCCESS";
    public static final String ACTION_FAILURE = "SYNC_FAILURE";

    @Override
    public void onReceive(Context context, Intent intent) {
            Intent i = new Intent(context, GPlayService.class);
            context.startService(i);

            Log.e(Constants.TAG, "contact made");

    }


}

