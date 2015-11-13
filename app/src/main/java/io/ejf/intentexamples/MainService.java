package io.ejf.intentexamples;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import io.ejf.intentexamples.utils.Logger;

/**
 * Created by ejf3 on 11/3/15.
 */
public class MainService extends Service{
    private static final Logger log = new Logger("MainService");

    public static boolean serviceRunning = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log.i("starting service");
        serviceRunning = true;
        if (intent.hasExtra("KILL"))
            stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        log.i("stopping service");
        serviceRunning = false;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
