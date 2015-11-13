package io.ejf.intentexamples.aviary;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
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
import io.ejf.intentexamples.utils.Logger;

public class AviaryLaunch extends AppCompatActivity {
    private static final Logger log = new Logger("AviaryLaunch");
    // Storage Permissions
    // http://stackoverflow.com/a/33288986/974800
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

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

        // Need to ask user for permissions in Marshmallow (API Level 23)
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            verifyStoragePermissions();
    }

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * http://stackoverflow.com/a/33288986/974800
     */
    private void verifyStoragePermissions() {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    /**
     * This method is available for any Activity, and the implementation
     * is copied and modified from Aviary's documentation.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data ) {
        log.i("onActivityResult request: %d, result: %d", requestCode, resultCode);
        Bundle extras = data.getExtras();
        Uri mImageUri = data.getData();
        // comes back with 12 as the resultCode, no idea why,
        // must be something Aviary implemented, but did not document
        if( resultCode != RESULT_CANCELED ) {
            switch( requestCode ) {
                case 1:
                    // output image path
                    log.i("returned URI: %s", mImageUri.getPath());
                    File file = new File(getExternalFilesDir(null), "startup_unicorn_edited.jpg");
                    if (null != extras) {
                        for (String key : extras.keySet())
                            log.i("extra %s: %s", key, extras.get(key));

                        // image has been changed by the user?
                        boolean changed = extras.getBoolean("bitmap-changed");
                        if (changed)
                            saveFile(mImageUri, file);
                    }
                    break;
            }
        }
    }

    private void launchAviary() {
        if (!isAviaryInstalled()) {
            tryInstallAvairy();
            return;
        }

        String destFileName = "startup_unicorn.jpg";
        File file = resToFile(R.drawable.startup_unicorn, destFileName);
        log.d("image file: %s", file.getPath());
        Uri uri = Uri.fromFile(file);

        // https://developers.aviary.com/docs/android/setup-guide#nosdk
        Intent newIntent = new Intent( "aviary.intent.action.EDIT" );
        newIntent.setDataAndType(uri, "image/*");
        newIntent.putExtra("app-id", getPackageName());

        startActivityForResult( newIntent, 1 );
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
        File outFile = new File(getExternalFilesDir(null), filename);
        if(outFile.exists())
            return outFile;

        try {
            InputStream is = getResources().openRawResource(resourceID);
            write(is, outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outFile;
    }

    private File saveFile(Uri uri, File outFile) {
        File inFile = new File(uri.toString());

        try {
            InputStream is = new FileInputStream(inFile);
            write(is, outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inFile;
    }

    private void write(InputStream is, File outFile) throws IOException {
        FileOutputStream os = new FileOutputStream(outFile);
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        os.write(buffer);
        os.close();
        is.close();
    }
}
