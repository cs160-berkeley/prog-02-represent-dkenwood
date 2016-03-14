package com.example.skylights.represent;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Wearable;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by skylights on 3/4/2016.
 */
public class DisplayCurrLocActivity extends AppCompatActivity {

//    List<String> barbara = Arrays.asList("Barbara Boxer", "Democrat", "@boxer It's time to put our brightest minds to work.");
//    List<String> dianne = Arrays.asList("Dianne Repubstein", "Republican", "@dianne Well, I've decided to switch parties.");
//    List<String> mary = Arrays.asList("Mary Lookalike", "Democrat", "@mary This is my first official tweet.");

//    protected GoogleApiClient mGoogleApiClient;
//    protected static final String TAG = "main-activity";
//    protected static final String ADDRESS_REQUESTED_KEY = "address-request-pending";
//    protected static final String LOCATION_ADDRESS_KEY = "location-address";
//    protected Location mLastLocation;

//    protected static JSONArray results = new JSONArray();

    protected static JSONObject jsnobject = new JSONObject();
    protected static boolean success2 = false;

    private static final String BASE_URL = "https://congress.api.sunlightfoundation.com/legislators/locate?";
    private static final String KEY = "35091875a8c64617919981778a2ac9fd";

//    protected static List<RepInfo> people = new ArrayList<RepInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_currloc_screen);

        Intent intent = getIntent();
        String address = intent.getStringExtra("the_address");
        String zip = intent.getStringExtra("the_zip");
        String county = intent.getStringExtra("the_county");

        TextView the_address = (TextView) findViewById(R.id.address);
        the_address.setText(address);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            String urlzip = BASE_URL + "zip=" + zip + "&apikey=" + KEY;
            new DownloadWebpageTask().execute(urlzip);
        }

        List<RepInfo> result = new ArrayList<RepInfo>();

        try {
            while (!success2) {
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            JSONArray RESULTS = jsnobject.getJSONArray("results");

            final int n = RESULTS.length();
            for (int i = 0; i < n; ++i) {
                RepInfo single_rep = new RepInfo();

                final JSONObject person = RESULTS.getJSONObject(i);

                String first_name = person.getString("first_name");
                String last_name = person.getString("last_name");
                String senator_name = first_name + " " + last_name;
                String senator_party;
                String senator_handle;

                if (person.getString("party").equals("D")) {
                    senator_party = "Democrat";
                } else if (person.getString("party").equals("I")) {
                    senator_party = "Independent";
                } else {
                    senator_party = "Republican";
                }

                String senator_house = person.getString("chamber");

                senator_handle = "@" + person.getString("twitter_id") + ":";

                String photo_id = person.getString("bioguide_id");

                single_rep.rep_name = senator_name;
                single_rep.party = senator_party;
                single_rep.tweet = senator_handle;
                single_rep.house = senator_house;
                single_rep.photo = photo_id;
                single_rep.county = county;
                result.add(single_rep);
            }
        } catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }

//        try {
//            getRepsFromZip(zip);
//            Thread.sleep(3000);
//        } catch(Exception e) {
//            Log.e("ERROR", e.getMessage(), e);
//        }
//
//        List<RepInfo> dying = SunlightRestClient.getThemPlease();

//        try {
//            System.out.println("eeyore");
//            final int n = results.length();
//            String first_name = "";
//            String last_name = "";
//            String full_name = "";
//            String senator_name = "";
//            String senator_party = "";
//            String senator_handle = "";
//            for (int i = 0; i < n; ++i) {
//                RepInfo single_rep = new RepInfo();
//
//                final JSONObject person = results.getJSONObject(i);
//
//                first_name = person.getString("first_name");
//                last_name = person.getString("last_name");
//                full_name = first_name + " " + last_name;
//                senator_name = full_name;
//
//                if (person.getString("party").equals("D")) {
//                    senator_party = "Democrat";
//                } else if (person.getString("party").equals("I")) {
//                    senator_party = "Independent";
//                } else {
//                    senator_party = "Republican";
//                }
//
//                senator_handle = "@" + person.getString("twitter_id") + ":";
//
//                String photo_id = person.getString("bioguide_id");
//
//                single_rep.rep_name = senator_name;
//                single_rep.party = senator_party;
//                single_rep.tweet = senator_handle;
//                single_rep.photo = photo_id;
//                people.add(single_rep);
//            }
//        } catch(Exception e) {
//            Log.e("ERROR", e.getMessage(), e);
//        }

        RecyclerView recList = (RecyclerView) findViewById(R.id.the_repList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        RepAdapter ca = new RepAdapter(result);
        recList.setAdapter(ca);
        success2 = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getRepsFromZip(String zipcode) throws JSONException {

        RequestParams params = new RequestParams();
        params.put("zip", zipcode);
        params.put("apikey", "35091875a8c64617919981778a2ac9fd");

        SunlightRestClient.get("legislators/locate", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                List<RepInfo> result = new ArrayList<RepInfo>();
                try {
                    JSONArray RESULTS = response.getJSONArray("results");
//                    System.out.println(RESULTS);

                    final int n = RESULTS.length();
                    for (int i = 0; i < n; ++i) {
                        RepInfo single_rep = new RepInfo();

                        final JSONObject person = RESULTS.getJSONObject(i);

                        String first_name = person.getString("first_name");
                        String last_name = person.getString("last_name");
                        String full_name = first_name + " " + last_name;
                        String senator_name = full_name;
                        String senator_party;
                        String senator_handle;

                        if (person.getString("party").equals("D")) {
                            senator_party = "Democrat";
                        } else if (person.getString("party").equals("I")) {
                            senator_party = "Independent";
                        } else {
                            senator_party = "Republican";
                        }

                        senator_handle = "@" + person.getString("twitter_id") + ":";

                        String photo_id = person.getString("bioguide_id");

                        single_rep.rep_name = senator_name;
                        single_rep.party = senator_party;
                        single_rep.tweet = senator_handle;
                        single_rep.photo = photo_id;
//                        System.out.println(senator_name);
//                        System.out.println(senator_party);
//                        single_rep.photo = "https://theunitedstates.io/images/congress/225x275/" + photo_id + ".jpg
                        result.add(single_rep);
                    }

                    SunlightRestClient.thereps = result;

                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage(), e);
                }
//                SunlightRestClient.thereps = result;

//                System.out.println(SunlightRestClient.thereps.size());
//                System.out.println("UGHHHHHHHHHHH");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
            }
        });

    }

//    public void getRepsFromZip(String zipcode) throws JSONException {
//
//        System.out.println(zipcode + " testing");
//        RequestParams params = new RequestParams();
//        params.put("zip", zipcode);
//        params.put("apikey", "35091875a8c64617919981778a2ac9fd");
//
//        SunlightRestClient.get("legislators/locate", params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // If the response is JSONObject instead of expected JSONArray
//                try {
//                    results = response.getJSONArray("results");
//                    System.out.println("ugh why");
//
//                } catch (Exception e) {
//                    Log.e("ERROR", e.getMessage(), e);
//                }
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
//            }
//        });
//
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mGoogleApiClient.disconnect();
//    }
//
//    @Override
//    public void onConnected(Bundle bundle) {}
//
//    @Override
//    public void onConnectionSuspended(int i) {}
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connResult) {}

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
                    success2 = true;
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
