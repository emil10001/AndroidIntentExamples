package io.ejf.intentexamples.aviary;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;

import io.ejf.intentexamples.R;

public class AviaryLaunch extends AppCompatActivity {
    private static final String TAG = "AviaryLaunch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aviary_launch);
        ((Button)findViewById(R.id.launch_aviary)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAviary();
            }
        });
    }

    private void launchAviary() {
        if (!isAviaryInstalled()) {
            tryInstallAvairy();
            return;
        }

        String destFileName = "startup_unicorn.jpg";
        File file = resToFile(R.drawable.startup_unicorn, destFileName);
        Log.d(TAG, "image file: " + file.getPath());
        Uri uri = Uri.fromFile(file);

        // https://developers.aviary.com/docs/android/setup-guide#nosdk
        Intent newIntent = new Intent( "aviary.intent.action.EDIT" );
        newIntent.setDataAndType(uri, "image/*");
        newIntent.putExtra("app-id", getPackageName());
        startActivity(newIntent);
    }

    private boolean isAviaryInstalled(){
        // https://developers.aviary.com/docs/android/setup-guide#nosdk
        Intent intent = new Intent( "aviary.intent.action.EDIT" );
        intent.setType("image/*");
        List<ResolveInfo> list = getPackageManager()
                .queryIntentActivities( intent, PackageManager.MATCH_DEFAULT_ONLY );
        return list.size() > 0;
    }

    private void tryInstallAvairy() {
        // https://developers.aviary.com/docs/android/setup-guide#nosdk
        Intent intent = new Intent( Intent.ACTION_VIEW );
        intent.setData(Uri.parse("market://details?id=com.aviary.android.feather"));
        startActivity(intent);
    }

    // http://stackoverflow.com/a/9730829/974800
    private File resToFile(int resourceID, String filename) {
        File file = new File(getExternalFilesDir(null), filename);
        if(file.exists()) {
            return file;
        }

        InputStream is;
        FileOutputStream fos;
        try {
            is = getResources().openRawResource(resourceID);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            fos = new FileOutputStream(file);
            fos.write(buffer);
            fos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

}
