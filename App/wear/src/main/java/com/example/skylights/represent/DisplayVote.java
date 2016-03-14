package com.example.skylights.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skylights on 3/6/2016.
 */
public class DisplayVote extends Activity {

    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {
                WatchToPhoneService.sendMessage("/94704", "Good job!", getBaseContext());
                Toast.makeText(DisplayVote.this, "Changed location!", Toast.LENGTH_SHORT).show();
                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                startService(sendIntent);
            }
        });

        if (extras != null) {
            String county = intent.getStringExtra("county");
            TextView state = (TextView) findViewById(R.id.the_state);
            TextView county_name = (TextView) findViewById(R.id.the_county);
            county_name.setText(county);

            if (county.equals("Alameda County")) {
                state.setText(getString(R.string.ca));
            }
            if (county.equals("Philadelphia County")) {
                state.setText(getString(R.string.phil));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
}
