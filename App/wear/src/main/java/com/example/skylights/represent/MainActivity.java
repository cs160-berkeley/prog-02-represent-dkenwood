package com.example.skylights.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
//        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
//            @Override
//            public void onLayoutInflated(WatchViewStub stub) {
//                mTextView = (TextView) stub.findViewById(R.id.text);
//            }
//        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            String rep_name = intent.getStringExtra("zippy");
            TextView lename = (TextView) findViewById(R.id.apptitle);
            lename.setText(rep_name);
//            String rep_name = intent.getStringExtra("REP_NAME");
//            Intent intent2 = new Intent(this, DisplayReps.class);
//            intent2.putExtra("rep_na", rep_name);
//            startActivity(intent2);
//            setContentView(R.layout.representatives);
//            TextView name = (TextView) findViewById(R.id.name);
//            name.setText(rep_name);
        }
    }
}
