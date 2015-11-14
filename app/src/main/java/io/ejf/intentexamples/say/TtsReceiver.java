package io.ejf.intentexamples.say;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import io.ejf.intentexamples.Constants;
import io.ejf.intentexamples.utils.Logger;

/**
 * Created by ejf3 on 11/7/15.
 */
public class TtsReceiver extends BroadcastReceiver  {
    private static final Logger log = new Logger("TtsReceiver");

    @Override
    public void onReceive(Context context, Intent intent) {
        log.i("received broadcast");
        Intent sIntent = new Intent(context, TtsService.class);

        if (Constants.SAY_RECEIVER_ACTION.equals(intent.getAction())
                && intent.hasExtra(Constants.SAY_TEXT)) {
            sIntent.putExtra(Constants.SAY_TEXT, intent.getStringExtra(Constants.SAY_TEXT));
            context.startService(sIntent);
        } else {
            log.w("received unknown intent");
        }
    }

}
