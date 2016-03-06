package com.example.skylights.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by skylights on 3/5/2016.
 */
public class DisplayReps extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.representatives);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            String rep_name = intent.getStringExtra("zippy");
            TextView name = (TextView) findViewById(R.id.name);
            name.setText(rep_name);
        }
    }
}
