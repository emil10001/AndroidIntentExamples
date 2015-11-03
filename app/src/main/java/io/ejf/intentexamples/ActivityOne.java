package io.ejf.intentexamples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityOne extends AppCompatActivity {
    Button launchIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        launchIntent = (Button) findViewById(R.id.launch_intent);
        launchIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchIntent();
            }
        });
    }

    private void launchIntent() {
        Intent intent = new Intent(this, ActivityTwo.class);
        startActivity(intent);
    }
}
