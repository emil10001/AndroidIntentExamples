package io.ejf.intentexamples;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import io.ejf.intentexamples.utils.Logger;

/**
 * Created by ejf3 on 11/4/15.
 */
public class MainReceiver extends BroadcastReceiver {
    private static final Logger log = new Logger("MainReceiver");

    @Override
    public void onReceive(Context context, Intent intent) {
        log.i("received broadcast");
    }
}
