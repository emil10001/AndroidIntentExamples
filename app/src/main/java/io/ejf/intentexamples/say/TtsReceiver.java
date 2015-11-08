package io.ejf.intentexamples.say;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import io.ejf.intentexamples.Constants;

/**
 * Created by ejf3 on 11/7/15.
 */
public class TtsReceiver extends BroadcastReceiver  {
    private static final String TAG = "TtsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "received broadcast");
        Intent sIntent = new Intent(context, TtsService.class);

        if (Constants.SAY_RECEIVER_ACTION.equals(intent.getAction())
                && intent.hasExtra(Constants.SAY_TEXT)) {
            sIntent.putExtra(Constants.SAY_TEXT, intent.getStringExtra(Constants.SAY_TEXT));
            context.startService(sIntent);
        } else {
            Log.w(TAG, "received unknown intent");
        }
    }

}
