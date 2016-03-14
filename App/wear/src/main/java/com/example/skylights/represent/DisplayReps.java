package com.example.skylights.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skylights on 3/5/2016.
 */
public class DisplayReps extends Activity {

    private List<Representative> mRepresentatives = new ArrayList<Representative>();
    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;

    private static final String BASE_URL = "https://congress.api.sunlightfoundation.com/legislators/locate?";
    private static final String KEY = "35091875a8c64617919981778a2ac9fd";

    protected static JSONObject jsnobject = new JSONObject();
    protected static boolean success = false;

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
            System.out.println(zippy + " oh my god why doesn't this work anymore");

//            ConnectivityManager connMgr = (ConnectivityManager)
//                    getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//
//            if (networkInfo != null && networkInfo.isConnected()) {
//                String urlzip = BASE_URL + "zip=" + zippy + "&apikey=" + KEY;
//                new DownloadWebpageTask().execute(urlzip);
//            }
//
//            try {
//                while (!success) {
//                    Thread.sleep(50);
//                }
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//
//            try {
//                JSONArray RESULTS = jsnobject.getJSONArray("results");
//
//                final int n = RESULTS.length();
//                for (int i = 0; i < n; ++i) {
//                    final JSONObject person = RESULTS.getJSONObject(i);
//
//                    String first_name = person.getString("first_name");
//                    String last_name = person.getString("last_name");
//                    String senator_name = first_name + " " + last_name;
//                    String senator_party;
//                    int set_pic;
//
//                    if (person.getString("party").equals("D")) {
//                        senator_party = "Democrat";
//                        set_pic = R.drawable.demo_bg;
//                    } else if (person.getString("party").equals("I")) {
//                        senator_party = "Independent";
//                        set_pic = R.drawable.demo_bg;
//                    } else {
//                        senator_party = "Republican";
//                        set_pic = R.drawable.repub_bg;
//                    }
//
//                    mRepresentatives.add(new Representative(set_pic, senator_name, senator_party));
//                }
//            } catch(Exception e) {
//                Log.e("ERROR", e.getMessage(), e);
//            }
//
            if (zippy.equals("94704")) {
                addBerkeley();
            }
            if (zippy.equals("19111")) {
                addp();
            }

            final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
            pager.setAdapter(new GridPagerAdapter(this, mRepresentatives, getFragmentManager()));
        }
    }

    private void addBerkeley() {
        mRepresentatives.add(new Representative(R.drawable.demo_bg, getString(R.string.lee), getString(R.string.democrat)));
        mRepresentatives.add(new Representative(R.drawable.demo_bg, getString(R.string.barbara), getString(R.string.democrat)));
        mRepresentatives.add(new Representative(R.drawable.demo_bg, getString(R.string.dianne), getString(R.string.republican)));
    }

    private void addp() {
        mRepresentatives.add(new Representative(R.drawable.demo_bg, getString(R.string.b), getString(R.string.democrat)));
        mRepresentatives.add(new Representative(R.drawable.repub_bg, getString(R.string.p), getString(R.string.republican)));
        mRepresentatives.add(new Representative(R.drawable.demo_bg, getString(R.string.r), getString(R.string.democrat)));
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

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    String reslt = stringBuilder.toString();
                    jsnobject = new JSONObject(reslt);
                    success = true;
//                    JSONArray jsonArray = jsnobject.getJSONArray("results");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject explrObject = jsonArray.getJSONObject(i);
//                    }
                    return stringBuilder.toString();
                }
                finally {
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //  textView.setText(result);
        }
    }
}
