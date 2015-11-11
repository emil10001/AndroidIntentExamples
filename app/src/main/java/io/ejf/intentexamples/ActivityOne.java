package io.ejf.intentexamples;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.ejf.intentexamples.aviary.AviaryLaunch;

public class ActivityOne extends AppCompatActivity {
    private static final int MY_DATA_CHECK_CODE = 3;
    private TextView tv;
    private Button launchActivityButton;
    private Button launchServiceButton;
    private Button checkTtsButton;
    private Button aviaryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        launchActivityButton = (Button) findViewById(R.id.launch_activity_two);
        launchServiceButton = (Button) findViewById(R.id.launch_service);
        checkTtsButton = (Button) findViewById(R.id.check_tts_data);
        aviaryButton = (Button) findViewById(R.id.aviary_button);

        tv = (TextView) findViewById(R.id.activity_one_text);

        launchActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity();
            }
        });
        launchServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchService();
            }
        });
        checkTtsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkIntent = new Intent();
                checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
                startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
            }
        });
        aviaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAviary();
            }
        });

    }

    private void launchAviary() {
        startActivity(new Intent(this, AviaryLaunch.class));
    }

    private void launchService() {
        Intent intent = new Intent(this, MainService.class);
        if (MainService.serviceRunning)
            intent.putExtra("KILL","");
        startService(intent);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.EXTRA_CONTENT))
            tv.setText(tv.getText() + " " + intent.getExtras().getString(Constants.EXTRA_CONTENT));
    }

    private void launchActivity() {
        Intent intent = new Intent(this, ActivityTwo.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode != TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(
                        TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }

}
