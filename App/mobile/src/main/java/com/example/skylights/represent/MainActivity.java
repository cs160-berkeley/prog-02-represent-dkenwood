package com.example.skylights.represent;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.preference.PreferenceActivity;
import android.service.carrier.CarrierMessagingService;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.wearable.Wearable;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.json.*;
import com.loopj.android.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import io.fabric.sdk.android.Fabric;

//public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
//        LocationListener {
    public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.

    public final static String EXTRA_ZIP = "com.skylights.represent.MESSAGE";

    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected LocationRequest mLocationRequest;
    protected LatLng mLatlong;
    protected static String address;
    protected static String zipcd;
    protected static String mCounty;
    protected static String zlatlng;

    protected static JSONObject jsnobject1 = new JSONObject();
    protected static JSONObject jsnobject2 = new JSONObject();

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private ResultReceiver mResultReceiver;

    protected static boolean success3 = false;
    protected static boolean success4 = false;
    protected static boolean success5 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mGoogleApiClient == null) {
            // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addApi(Wearable.API)  // used for data layer API
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(AppIndex.API).build();
        }

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                        .setFastestInterval(1 * 1000); // 1 second, in milliseconds

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

        return super.onOptionsItemSelected(item);
    }

    public void searchZip(View view) {
        EditText editZip = (EditText) findViewById(R.id.enterzip);
        String entered_zip = editZip.getText().toString();
        String tcounty = "";

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            String urlzip = "https://maps.googleapis.com/maps/api/geocode/json?components=postal_code:" + entered_zip;
            new DownloadWebpageTask().execute(urlzip);
        }

        try {
            while (!success4) {
                Thread.sleep(30);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            JSONArray RESULTS = jsnobject1.getJSONArray("results");
            JSONObject res = RESULTS.getJSONObject(0);
            JSONObject wantlatlng = res.getJSONObject("geometry");
            JSONObject loc = wantlatlng.getJSONObject("location");
            String lat = loc.getString("lat");
            String lng = loc.getString("lng");
            zlatlng = lat + "," + lng;
            JSONArray wantzip = res.getJSONArray("address_components");
            final int components_l = wantzip.length() - 1;
            for (int i = 0; i < components_l; ++i) {
                JSONObject compo = wantzip.getJSONObject(i);
                if ((compo.getJSONArray("types").get(0)).equals("administrative_area_level_2")) {
                    tcounty = compo.getString("long_name");
                }
            }
        } catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }

        if (tcounty.equals("")) {
            ConnectivityManager connMgr2 = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo2 = connMgr2.getActiveNetworkInfo();

            if (networkInfo2 != null && networkInfo2.isConnected()) {
                String urlzip = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + zlatlng + "&key=AIzaSyD9aqGJb_TNkjwWLnGx6GIBjvkFS2yH47c";
                new DownloadWebpageTask2().execute(urlzip);
            }

            try {
                while (!success5) {
                    Thread.sleep(30);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            try {
                JSONArray RESULTS = jsnobject2.getJSONArray("results");
                JSONObject res = RESULTS.getJSONObject(0);
                JSONArray wantcounty = res.getJSONArray("address_components");
                final int components_l = wantcounty.length() - 1;
                for (int i = 0; i < components_l; ++i) {
                    JSONObject compo = wantcounty.getJSONObject(i);
                    if ((compo.getJSONArray("types").get(0)).equals("administrative_area_level_2")) {
                        tcounty = compo.getString("long_name");
                    }
                }
            } catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
            }
        }

        Intent sendIntent = new Intent(this, PhoneToWatchService.class);
        sendIntent.putExtra("zip", entered_zip);
        startService(sendIntent);

        Intent intent = new Intent(MainActivity.this, DisplayZipActivity.class);
        intent.putExtra("zip_code", entered_zip);
        intent.putExtra("the_county", tcounty);
        success4 = false;
        startActivity(intent);
    }

    public void useCurrLoc(View view) {
//        Intent sendIntent = new Intent(this, PhoneToWatchService.class);
//        sendIntent.putExtra("curr_zip_code", currLoc);
//        startService(sendIntent);

        Intent intent = new Intent(this, DisplayCurrLocActivity.class);
        intent.putExtra("the_address", address);
        intent.putExtra("the_zip", zipcd);
        intent.putExtra("the_county", mCounty);
        success3 = false;
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

//    @Override
//    public void onConnected(Bundle connectionHint) {
//        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
//                mGoogleApiClient);
//        if (mLastLocation != null) {
//            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
//            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
//        }
//    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.i("Nope", "Location services connected.");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            Log.i("Nope", "bloooooogy");
            handleNewLocation(mLastLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("Nope", "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("Nope", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void handleNewLocation(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        mLatlong = new LatLng(currentLatitude, currentLongitude);
        try {
            getGeocoding(mLatlong);
        } catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }

//        mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
//        mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
//        lati = mLatitudeText.toString();
//        longi = mLongitudeText.toString();
//        Double lat = Double.parseDouble(lati);
//        Double lng = Double.parseDouble(longi);
    }

//    public void getRepsFromZip() throws JSONException {
//        String first_name;
//        String last_name;
//        String full_name;
//
//        RequestParams params = new RequestParams();
//        params.put("zip", entered_zip);
//        params.put("apikey", "35091875a8c64617919981778a2ac9fd");
//        SunlightRestClient.get("legislators/locate", params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // If the response is JSONObject instead of expected JSONArray
//                try {
//                    JSONArray RESULTS = response.getJSONArray("results");
////                    System.out.println(RESULTS);
//
//                    final int n = RESULTS.length();
//                    for (int i = 0; i < n; ++i) {
//                        final JSONObject person = RESULTS.getJSONObject(i);
//
//                        first_name = person.getString("first_name");
//                        last_name = person.getString("last_name");
//                        full_name = first_name + " " last_name;
//                        senator_name = full_name;
//
//                        if (person.getString("party").equals("D") {
//                            senator_party = "Democrat";
//                        } else {
//                            senator_party = "Republican";
//                        }
//
//                        senator_handle = person.getString("twitter_id");
//
//
//                    }
//
//                } catch(Exception e) {
//                    Log.e("ERROR", e.getMessage(), e);
//                }
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
//                // Pull out the first event on the public timeline
////                JSONObject firstEvent = timeline.get(0);
////                String tweetText = firstEvent.getString("text");
////
////                // Do something with the response
////                System.out.println(tweetText);
//            }
//        });
//    }

//
    protected void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
        startService(intent);
    }

//    public void getRepsFromZip(String zipcode) throws JSONException {
//
//        RequestParams params = new RequestParams();
//        params.put("zip", zipcode);
//        params.put("apikey", "35091875a8c64617919981778a2ac9fd");
//
//        SunlightRestClient.get("legislators/locate", params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // If the response is JSONObject instead of expected JSONArray
//                List<RepInfo> result = new ArrayList<RepInfo>();
//                try {
//                    JSONArray RESULTS = response.getJSONArray("results");
////                    System.out.println(RESULTS);
//
//                    final int n = RESULTS.length();
//                    for (int i = 0; i < n; ++i) {
//                        RepInfo single_rep = new RepInfo();
//
//                        final JSONObject person = RESULTS.getJSONObject(i);
//
//                        first_name = person.getString("first_name");
//                        last_name = person.getString("last_name");
//                        full_name = first_name + " " + last_name;
//                        senator_name = full_name;
//
//                        if (person.getString("party").equals("D")) {
//                            senator_party = "Democrat";
//                        } else if (person.getString("party").equals("I")) {
//                            senator_party = "Independent";
//                        } else {
//                            senator_party = "Republican";
//                        }
//
//                        String senator_house = person.getString("chamber");
//
//                        senator_handle = "@" + person.getString("twitter_id") + ":";
//
//                        String photo_id = person.getString("bioguide_id");
//
//                        single_rep.rep_name = senator_name;
//                        single_rep.party = senator_party;
//                        single_rep.tweet = senator_handle;
//                        single_rep.house = senator_house;
//                        single_rep.photo = photo_id;
////                        System.out.println(senator_name);
////                        System.out.println(senator_party);
////                        single_rep.photo = "https://theunitedstates.io/images/congress/225x275/" + photo_id + ".jpg
//                        result.add(single_rep);
//                    }
//
//                    SunlightRestClient.thereps = result;
//
//                } catch (Exception e) {
//                    Log.e("ERROR", e.getMessage(), e);
//                }
////                SunlightRestClient.thereps = result;
//
////                System.out.println(SunlightRestClient.thereps.size());
////                System.out.println("UGHHHHHHHHHHH");
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
//            }
//        });
//
//    }

    public void getGeocoding(LatLng latlong) throws JSONException {

        System.out.println("Why aren't you working anymore");
        String lat = Double.toString(latlong.latitude);
        String lng = Double.toString(latlong.longitude);
        String combo = lat + "," + lng;
        RequestParams params = new RequestParams();
        params.put("latlng", combo);
        params.put("result_type", "street_address");
        params.put("key", "AIzaSyD9aqGJb_TNkjwWLnGx6GIBjvkFS2yH47c");

        GeocodingRestClient.get("geocode/json", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    JSONArray RESULTS = response.getJSONArray("results");
                    JSONObject res = RESULTS.getJSONObject(0);
                    address = res.getString("formatted_address");
                    JSONArray wantzip = res.getJSONArray("address_components");
                    final int components_l = wantzip.length() - 1;
                    for (int i = 0; i < components_l; ++i) {
                        JSONObject compo = wantzip.getJSONObject(i);
                        if ((compo.getJSONArray("types").get(0)).equals("administrative_area_level_2")) {
                            mCounty = compo.getString("long_name");
                        }
                        if ((compo.getJSONArray("types").get(0)).equals("postal_code")) {
                            zipcd = compo.getString("long_name");
                        }
                    }
                    success3 = true;
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage(), e);
                }
//                SunlightRestClient.thereps = result;

//                System.out.println(SunlightRestClient.thereps.size());
//                System.out.println("UGHHHHHHHHHHH");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
//                    JSONArray RESULTS = response.getJSONArray("results");
//                    System.out.println(RESULTS);
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage(), e);
                }
            }
        });

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
                    jsnobject1 = new JSONObject(reslt);
                    success4 = true;
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

    private class DownloadWebpageTask2 extends AsyncTask<String, Void, String> {
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
                    jsnobject2 = new JSONObject(reslt);
                    success5 = true;
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
