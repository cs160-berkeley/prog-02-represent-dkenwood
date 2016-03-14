package com.example.skylights.represent;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import cz.msebera.android.httpclient.Header;

public class DisplayZipActivity extends AppCompatActivity {

//    List<String> barbara = Arrays.asList("Barbara Boxer", "Democrat", "@boxer It's time to put our brightest minds to work.");
//    List<String> dianne = Arrays.asList("Dianne Repubstein", "Republican", "@dianne Well, I've decided to switch parties.");
//    List<String> mary = Arrays.asList("Mary Lookalike", "Democrat", "@mary This is my first official tweet.");
//    List<String> hermione = Arrays.asList("Hermione Granger", "Democrat", "@granger Just because you've got the emotional range of a teaspoon doesn't mean we all have.");
//    List<String> fleur = Arrays.asList("Fleur Delacour", "Democrat", "@fleur 'Arry, you saved my sister's life. I do not forget.");
//    List<String> cedric = Arrays.asList("Cedric Diggory", "Democrat", "@cedric Just take your egg and... mull things over in the hot water.");

    private static final String BASE_URL = "https://congress.api.sunlightfoundation.com/legislators/locate?";
    private static final String KEY = "35091875a8c64617919981778a2ac9fd";

    protected static JSONObject jsnobject = new JSONObject();
    protected static boolean success = false;
    protected static JSONObject jsnobject5 = new JSONObject();
    protected static boolean success6 = false;
    protected static boolean success7 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_zip_screen);

        String c = "";
        String s = "";

        Intent intent = getIntent();
        String zipcode = intent.getStringExtra("zip_code");
        String county = intent.getStringExtra("the_county");
        TextView zipcodey = (TextView) findViewById(R.id.zipcode);
        zipcodey.setText(zipcode);

        ConnectivityManager connMgr5 = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo5 = connMgr5.getActiveNetworkInfo();

        if (networkInfo5 != null && networkInfo5.isConnected()) {
            String urlzip5 = "https://maps.googleapis.com/maps/api/geocode/json?components=postal_code:" + zipcode;
            new DownloadWebpageTask5().execute(urlzip5);
        }

        try {
            while (!success6) {
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            JSONArray RESULTS2 = jsnobject5.getJSONArray("results");
            JSONObject res = RESULTS2.getJSONObject(0);
            JSONArray wantzip2 = res.getJSONArray("address_components");
            final int components_2 = wantzip2.length() - 1;
            for (int i = 0; i < components_2; ++i) {
                JSONObject compo2 = wantzip2.getJSONObject(i);
                if ((compo2.getJSONArray("types").get(0)).equals("locality")) {
                    c = compo2.getString("long_name");
                    success7 = true;
                }
                if ((compo2.getJSONArray("types").get(0)).equals("administrative_area_level_1")) {
                    s = compo2.getString("short_name");
                }
            }
        } catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }

        success6 = false;

        try {
            while (!success7) {
                Thread.sleep(30);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        TextView citystate = (TextView) findViewById(R.id.citystate);
        citystate.setText(c + ", " + s);
        success7 = false;

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            String urlzip = BASE_URL + "zip=" + zipcode + "&apikey=" + KEY;
            new DownloadWebpageTask().execute(urlzip);
        }

        List<RepInfo> result = new ArrayList<RepInfo>();

        try {
            while (!success) {
                Thread.sleep(100);
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


        RecyclerView recList = (RecyclerView) findViewById(R.id.repList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        RepAdapter ca = new RepAdapter(result);
        recList.setAdapter(ca);
        success = false;
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

    public void openWebsite (View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://lee.house.gov"));
        startActivity(browserIntent);
    }

    public void sendEmail (View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "Rep.Lee@opencongress.org" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        intent.putExtra(Intent.EXTRA_TEXT, "mail body");
        startActivity(Intent.createChooser(intent, ""));
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

    private class DownloadWebpageTask5 extends AsyncTask<String, Void, String> {
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
                    String reslt2 = stringBuilder.toString();
                    jsnobject5 = new JSONObject(reslt2);
                    success6 = true;
                    return reslt2;
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
            //  hmm
        }
    }

}
