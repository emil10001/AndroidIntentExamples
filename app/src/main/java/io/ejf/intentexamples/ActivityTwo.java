package io.ejf.intentexamples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by ejf3 on 11/2/15.
 */
public class ActivityTwo extends AppCompatActivity {
    private Button launchActivityButton;
    private Button launchReceiverButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        launchActivityButton = (Button) findViewById(R.id.launch_activity_two);
        launchReceiverButton = (Button) findViewById(R.id.launch_broadcast);

        launchActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity();
            }
        });
        launchReceiverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchReceiver();
            }
        });
    }

    private void launchActivity() {
        Intent intent = new Intent(this, ActivityOne.class);
        intent.putExtra("EXTRA_CONTENT", "saw ActivityTwo");
        startActivity(intent);
    }

    private void launchReceiver(){
        Intent intent = new Intent();
        intent.setAction("io.ejf.intentexamples");
        sendBroadcast(intent);
    }
}
