package io.ejf.intentexamples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityOne extends AppCompatActivity {
    private TextView tv;
    private Button launchActivityButton;
    private Button launchServiceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        launchActivityButton = (Button) findViewById(R.id.launch_activity_two);
        launchServiceButton = (Button) findViewById(R.id.launch_service);

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
        if (intent.hasExtra("EXTRA_CONTENT"))
            tv.setText(tv.getText() + " " + intent.getExtras().getString("EXTRA_CONTENT"));
    }

    private void launchActivity() {
        Intent intent = new Intent(this, ActivityTwo.class);
        startActivity(intent);
    }
}
