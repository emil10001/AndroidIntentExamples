package io.ejf.saylauncher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String SAY_TEXT = "say_text";
    public static final String SAY_RECEIVER_ACTION = "io.ejf.intentexamples.say";
    private Button sayButton;
    private EditText sayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sayButton = (Button) findViewById(R.id.launch_say);
        sayText = (EditText) findViewById(R.id.say_text);
        sayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                say();
            }
        });
    }

    private void say() {
        if (null == sayText.getText()){
            Toast.makeText(this, "must have something to say", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.setAction(SAY_RECEIVER_ACTION);
        intent.putExtra(SAY_TEXT, sayText.getText().toString());
        sendBroadcast(intent);
    }

}
