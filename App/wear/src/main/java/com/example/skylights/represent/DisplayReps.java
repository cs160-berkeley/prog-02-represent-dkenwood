package com.example.skylights.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skylights on 3/5/2016.
 */
public class DisplayReps extends Activity {

    private List<Representative> mRepresentatives = new ArrayList<Representative>();
    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.representatives);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {
                WatchToPhoneService.sendMessage("/94704", "Good job!", getBaseContext());
                Toast.makeText(DisplayReps.this, "Changed location!", Toast.LENGTH_SHORT).show();
                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                startService(sendIntent);
            }
        });

        if (extras != null) {
            String zippy = intent.getStringExtra("zippy");

            if (zippy.equals("94704")) {
                addBerkeley();
            }
            if (zippy.equals("99999")) {
                addHogwarts();
            }

            final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
            pager.setAdapter(new GridPagerAdapter(this, mRepresentatives, getFragmentManager()));
        }
    }

    private void addBerkeley() {
        mRepresentatives.add(new Representative(R.drawable.demo_bg, getString(R.string.barbara), getString(R.string.democrat)));
        mRepresentatives.add(new Representative(R.drawable.repub_bg, getString(R.string.dianne), getString(R.string.republican)));
        mRepresentatives.add(new Representative(R.drawable.demo_bg, getString(R.string.mary), getString(R.string.democrat)));
    }

    private void addHogwarts() {
        mRepresentatives.add(new Representative(R.drawable.demo_bg, getString(R.string.hermione), getString(R.string.democrat)));
        mRepresentatives.add(new Representative(R.drawable.demo_bg, getString(R.string.fleur), getString(R.string.democrat)));
        mRepresentatives.add(new Representative(R.drawable.demo_bg, getString(R.string.cedric), getString(R.string.democrat)));
    }

    public static class Representative {
        private int mBackground;
        private String mName;
        private String mParty;

        public Representative(int imageResource, String name, String party) {
            mBackground = imageResource;
            mName = name;
            mParty = party;
        }

        public String getName(int page) {
            return mName;
        }

        public String getParty(int page) {
            return mParty;
        }

        public int getImageResource() {
            return mBackground;
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
