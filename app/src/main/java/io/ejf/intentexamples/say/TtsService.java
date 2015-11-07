package io.ejf.intentexamples.say;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Locale;

import io.ejf.intentexamples.Constants;

/**
 * Created by ejf3 on 11/7/15.
 */
public class TtsService extends Service implements TextToSpeech.OnInitListener {
    private static final String TAG = "TtsService";
    public static boolean serviceRunning = false;
    public volatile boolean ttsInitted = false;
    private TextToSpeech mTts;
    private String sayText;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "starting service");
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
        Log.i(TAG, "stopping service");
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
        Log.i(TAG, "onInit " + status);
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
