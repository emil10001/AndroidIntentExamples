package io.ejf.intentexamples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ejf3 on 11/2/15.
 */
public class ActivityTwo extends AppCompatActivity {
    private Button launchActivityButton;
    private Button launchReceiverButton;
    private Button sayButton;
    private EditText sayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        launchActivityButton = (Button) findViewById(R.id.launch_activity_two);
        launchReceiverButton = (Button) findViewById(R.id.launch_broadcast);
        sayButton = (Button) findViewById(R.id.launch_say);
        sayText = (EditText) findViewById(R.id.say_text);

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
        sayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                say();
            }
        });
    }


    private void launchActivity() {
        Intent intent = new Intent(this, ActivityOne.class);
        intent.putExtra(Constants.EXTRA_CONTENT, "saw ActivityTwo");
                startActivity(intent);
    }

    private void launchReceiver(){
        Intent intent = new Intent();
        intent.setAction(Constants.MAIN_RECEIVER_ACTION);
        sendBroadcast(intent);
    }

    private void say() {
        if (null == sayText.getText()){
            Toast.makeText(this, "must have something to say", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Constants.SAY_RECEIVER_ACTION);
        intent.putExtra(Constants.SAY_TEXT, sayText.getText().toString());
        sendBroadcast(intent);
    }

}
