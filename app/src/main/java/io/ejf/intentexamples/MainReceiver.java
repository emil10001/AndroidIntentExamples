package io.ejf.intentexamples;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ejf3 on 11/4/15.
 */
public class MainReceiver extends BroadcastReceiver {
    private static final String TAG = "MainReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "received broadcast");
    }
}
