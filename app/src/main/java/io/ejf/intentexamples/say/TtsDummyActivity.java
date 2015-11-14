package io.ejf.intentexamples.say;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import io.ejf.intentexamples.Constants;
import io.ejf.intentexamples.utils.Logger;

/**
 * Created by ejf3 on 11/8/15.
 */
public class TtsDummyActivity extends Activity {
    private static final Logger log = new Logger("TtsDummyActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) &&  "text/plain".equals(type)) {
            Intent sIntent = new Intent(this, TtsService.class);
            sIntent.putExtra(Constants.SAY_TEXT, intent.getStringExtra(Intent.EXTRA_TEXT));
            startService(sIntent);
        } else {
            log.w("received unknown intent");
        }

        finish();
    }

}
