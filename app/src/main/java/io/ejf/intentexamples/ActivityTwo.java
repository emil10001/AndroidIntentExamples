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
    private Button launchIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        launchIntent = (Button) findViewById(R.id.launch_intent);
        launchIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchIntent();
            }
        });
    }

    private void launchIntent() {
        Intent intent = new Intent(this, ActivityOne.class);
        intent.putExtra("EXTRA_CONTENT", "saw ActivityTwo");
        startActivity(intent);
    }
}
