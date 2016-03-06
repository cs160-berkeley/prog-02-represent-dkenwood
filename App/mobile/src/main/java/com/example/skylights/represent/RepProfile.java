package com.example.skylights.represent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

/**
 * Created by skylights on 3/4/2016.
 */
public class RepProfile extends Activity {

    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.representative_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = getIntent();
        String the_name = intent.getStringExtra("the_name");
        String the_party = intent.getStringExtra("the_party");

        TextView profname = (TextView) findViewById(R.id.prof_name);
        profname.setText(the_name);

        TextView profparty = (TextView) findViewById(R.id.prof_party);
        profparty.setText(the_party);

        if (the_party.equals("Republican")) {
            profparty.setTextColor(Color.parseColor("#EB5757"));
        }
        if (the_party.equals("Democrat")) {
            profparty.setTextColor(Color.parseColor("#2D9CDB"));
        }

        ImageView profimage = (ImageView) findViewById(R.id.prof_photo);
        if (the_name.equals("Barbara Boxer")) {
            profimage.setImageResource(R.drawable.barbara_s);
        }
        if (the_name.equals("Hermione Granger")) {
            profimage.setImageResource(R.drawable.hermione);
        }
        if (the_name.equals("Mary Lookalike")) {
            profimage.setImageResource(R.drawable.mary);
        }
        if (the_name.equals("Dianne Repubstein")) {
            profimage.setImageResource(R.drawable.dianne_s);
        }
        if (the_name.equals("Cedric Diggory")) {
            profimage.setImageResource(R.drawable.cedric);
        }
        if (the_name.equals("Fleur Delacour")) {
            profimage.setImageResource(R.drawable.fleur);
        }

//        listView = (ListView) findViewById(R.id.committees);
//
//        // Defined Array values to show in ListView
//        String[] values = new String[] { "Android List View",
//                "Adapter implementation",
//                "Simple List View In Android",
//                "Create List View Android",
//                "Android Example",
//                "List View Source Code",
//                "List View Array Adapter",
//                "Android Example List View"
//        };
//
//        // Define a new Adapter
//        // First parameter - Context
//        // Second parameter - Layout for the row
//        // Third parameter - ID of the TextView to which the data is written
//        // Forth - the Array of data
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1, values);
//
//
//        // Assign adapter to ListView
//        listView.setAdapter(adapter);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}