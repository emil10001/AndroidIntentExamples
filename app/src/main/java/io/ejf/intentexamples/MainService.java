package io.ejf.intentexamples;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ejf3 on 11/3/15.
 */
public class MainService extends Service{
    private static final String TAG = "MainService";
    public static boolean serviceRunning = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "starting service");
        serviceRunning = true;
        if (intent.hasExtra("KILL"))
            stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        Log.i(TAG, "stopping service");
        serviceRunning = false;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
