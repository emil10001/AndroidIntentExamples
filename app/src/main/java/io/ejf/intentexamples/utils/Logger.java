package io.ejf.intentexamples.utils;

import android.util.Log;

/**
 * This is a simple wrapper for the built-in Android logging utility.
 * This version is a little smarter, in that it allows formatted strings
 * to be passed in along with arguments. It also gets initialized with the
 * TAG, to reduce redundant bits of code.
 *
 */
public class Logger {
    private final String TAG;
    private Logger(){
        TAG = "Logger";
    }

    public Logger(String TAG) {
        this.TAG = TAG;
    }

    public void v (String arg1, Object...arg2){
        if (null != arg2)
            Log.v(TAG, String.format(arg1, arg2));
        else
            Log.v(TAG, arg1);
    }

    public void d (String arg1, Object...arg2){
        if (null != arg2)
            Log.d(TAG, String.format(arg1, arg2));
        else
            Log.d(TAG, arg1);
    }

    public void i (String arg1, Object...arg2){
        if (null != arg2)
            Log.i(TAG, String.format(arg1, arg2));
        else
            Log.i(TAG, arg1);
    }

    public void w (String arg1, Exception e, Object...arg2){
        String formattedLog;
        if (null != arg2)
            formattedLog = String.format(arg1, arg2);
        else
            formattedLog = arg1;

        if (null != e)
            Log.w(TAG, formattedLog, e);
        else
            Log.w(TAG, formattedLog);
    }

    public void e (String arg1, Exception e, Object...arg2){
        String formattedLog;
        if (null != arg2)
            formattedLog = String.format(arg1, arg2);
        else
            formattedLog = arg1;

        if (null != e)
            Log.e(TAG, formattedLog, e);
        else
            Log.e(TAG, formattedLog);
    }

}
