package io.ejf.intentexamples.say;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;

import java.util.Locale;

import io.ejf.intentexamples.Constants;
import io.ejf.intentexamples.utils.Logger;

/**
 * Created by ejf3 on 11/7/15.
 */
public class TtsService extends Service implements TextToSpeech.OnInitListener {
    private static final Logger log = new Logger("TtsService");

    public static boolean serviceRunning = false;
    public volatile boolean ttsInitted = false;
    private TextToSpeech mTts;
    private String sayText;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log.i("starting service");
        serviceRunning = true;
        if (intent.hasExtra("KILL"))
            stopSelf();

        if (intent.hasExtra(Constants.SAY_TEXT)) {
            sayText = intent.getStringExtra(Constants.SAY_TEXT);
            if (ttsInitted)
                say();
            else
                mTts = new TextToSpeech(this, this);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        log.i("stopping service");
        serviceRunning = false;
        mTts.shutdown();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onInit(int status) {
        log.i("onInit " + status);
        if (status == TextToSpeech.SUCCESS) {
            ttsInitted = true;
            mTts.setLanguage(Locale.US);
            say();
        }
    }

    private void say() {
        mTts.speak(sayText, TextToSpeech.QUEUE_ADD, null, null);
    }

}
