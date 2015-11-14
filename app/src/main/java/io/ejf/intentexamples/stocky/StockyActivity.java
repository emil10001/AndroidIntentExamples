package io.ejf.intentexamples.stocky;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import io.ejf.intentexamples.Constants;
import io.ejf.intentexamples.utils.Hasher;
import io.ejf.intentexamples.utils.Logger;

/**
 * Created by ejf3 on 11/13/15.
 */
public class StockyActivity extends Activity {
    private static final Logger log = new Logger("StockyActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String caller = Hasher.hashIt("stocky_app" + getCallingPackage());
        Intent sIntent = new Intent();

        if (Constants.STOCKY_RECEIVER_ACTION.equals(intent.getAction())
                && intent.hasExtra(Constants.CLIENT_ID)
                && intent.hasExtra(Constants.CLIENT_SECRET)) {

            Bundle extras = intent.getExtras();
            String clientId = extras.getString(Constants.CLIENT_ID);

            // this is a really basic way to do security,
            // this probably would not cut it for a finance app
            if (!caller.equals(clientId)) {
                log.w("bad caller");
                setResult(RESULT_CANCELED, sIntent);
                finish();
                return;
            }

            // hardcoded information, I'm not building a full app here
            sIntent.putExtra(Constants.VALUE, 9830.00);
            sIntent.putExtra(Constants.INVESTED, 10000.00);
            setResult(RESULT_OK, sIntent);
        } else {
            setResult(RESULT_CANCELED, sIntent);
            log.w("received unknown intent");
        }

        finish();
    }

}
