package io.ejf.intentexamples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityOne extends AppCompatActivity {
    private TextView tv;
    private Button launchIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        launchIntent = (Button) findViewById(R.id.launch_intent);
        tv = (TextView) findViewById(R.id.activity_one_text);

        launchIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchIntent();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = getIntent();
        if (intent.hasExtra("EXTRA_CONTENT"))
            tv.setText(tv.getText() + " " + intent.getExtras().getString("EXTRA_CONTENT"));
    }

    private void launchIntent() {
        Intent intent = new Intent(this, ActivityTwo.class);
        startActivity(intent);
    }
}
