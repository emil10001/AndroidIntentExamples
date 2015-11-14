package io.ejf.financemanagerdemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

import io.ejf.financemanagerdemo.utils.Hasher;
import io.ejf.financemanagerdemo.utils.Logger;

public class MainActivity extends AppCompatActivity {
    private static final Logger log = new Logger("MainActivity");

    public static final String CLIENT_ID = "CLIENT_ID";
    public static final String CLIENT_SECRET = "CLIENT_SECRET";
    public static final String STOCKY_RECEIVER_ACTION = "io.ejf.intentexamples.stocky";

    private static final int REQUEST_CODE = 1;

    private TextView stockInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stockInfo = (TextView) findViewById(R.id.stock_info);
        findViewById(R.id.stock_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStockInfo();
            }
        });
    }

    private void requestStockInfo() {
        Intent intent = new Intent(STOCKY_RECEIVER_ACTION);
        intent.putExtra(CLIENT_ID, Hasher.hashIt("stocky_app" + getPackageName()));
        intent.putExtra(CLIENT_SECRET, CLIENT_SECRET);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data ) {
        log.i("onActivityResult request: %d, result: %d", requestCode, resultCode);
        if (RESULT_OK != resultCode)
            return;

        Bundle extras = data.getExtras();
        String stockInfoString = "";

        double value = 0L;
        double invested = 0L;
        if (extras.containsKey("VALUE")) {
            value = extras.getDouble("VALUE");
            stockInfoString += "VALUE: $" + String.valueOf(value);
        }
        if (extras.containsKey("INVESTED")) {
            invested = extras.getDouble("INVESTED");
            stockInfoString += "\nINVESTED: $" + String.valueOf(invested);
        }
        double gains = value - invested;
        stockInfoString += "\nGAINS: $" + String.valueOf(gains);
        stockInfo.setText(stockInfoString);
    }

}
