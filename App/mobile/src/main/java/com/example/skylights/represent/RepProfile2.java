package com.example.skylights.represent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.skylights.represent.R;
import com.squareup.picasso.Picasso;

/**
 * Created by skylights on 3/6/2016.
 */
public class RepProfile2 extends Activity {

    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.representative_profile2);

        Intent intent = getIntent();
        String the_name = intent.getStringExtra("the_name");
        String the_party = intent.getStringExtra("the_party");
        String the_photo = intent.getStringExtra("the_photo");
        String the_county = intent.getStringExtra("the_county");

        TextView profname = (TextView) findViewById(R.id.prof_name);
        profname.setText(the_name);

        TextView profparty = (TextView) findViewById(R.id.prof_party);
        profparty.setText(the_party);

        TextView profcounty = (TextView) findViewById(R.id.prof_county);
        profcounty.setText(the_county);

        if (the_party.equals("Republican")) {
            profparty.setTextColor(Color.parseColor("#EB5757"));
        }
        if (the_party.equals("Democrat")) {
            profparty.setTextColor(Color.parseColor("#2D9CDB"));
        }

        ImageView profimage = (ImageView) findViewById(R.id.prof_photo);

        Picasso.with(getBaseContext()).load("https://theunitedstates.io/images/congress/225x275/" +
                the_photo + ".jpg").into(profimage);
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

    public void viewVote(View view) {
        TextView county = (TextView) findViewById(R.id.prof_county);
        String the_county = county.getText().toString();
        Intent sendIntent = new Intent(this, PhoneToWatchService.class);
        sendIntent.putExtra("the_county", the_county);
        startService(sendIntent);
    }
}