package com.example.skylights.represent;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayZipActivity extends AppCompatActivity {

    List<String> barbara = Arrays.asList("Barbara Boxer", "Democrat", "@boxer It's time to put our brightest minds to work.");
    List<String> dianne = Arrays.asList("Dianne Repubstein", "Republican", "@dianne Well, I've decided to switch parties.");
    List<String> mary = Arrays.asList("Mary Lookalike", "Democrat", "@mary This is my first official tweet.");
    List<String> hermione = Arrays.asList("Hermione Granger", "Democrat", "@granger Just because you've got the emotional range of a teaspoon doesn't mean we all have.");
    List<String> fleur = Arrays.asList("Fleur Delacour", "Democrat", "@fleur 'Arry, you saved my sister's life. I do not forget.");
    List<String> cedric = Arrays.asList("Cedric Diggory", "Democrat", "@cedric Just take your egg and... mull things over in the hot water.");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_zip_screen);

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }*/

        Intent intent = getIntent();
        /*Bundle extras = getIntent().getExtras();
        zipcode = extras.getString("zip_message");*/
        String zipcode = intent.getStringExtra("zip_code");
//        TextView textView = new TextView(this);
//        textView.setTextSize(40);
//        textView.setText(zipcode);
//        RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
//        layout.addView(textView);
        TextView zipcodey = (TextView) findViewById(R.id.zipcode);
        zipcodey.setText(zipcode);

        if (zipcode.equals("94704")) {
            TextView city_state = (TextView) findViewById(R.id.citystate);
            city_state.setText(getString(R.string.berkeley));
        }

        if (zipcode.equals("99999")) {
            TextView city_state = (TextView) findViewById(R.id.citystate);
            city_state.setText(getString(R.string.hogwarts));
        }

        RecyclerView recList = (RecyclerView) findViewById(R.id.repList);
//        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        RepAdapter ca = new RepAdapter(createList(zipcode));
        recList.setAdapter(ca);
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

    private List<RepInfo> createList(String zippcode) {

        List<RepInfo> result = new ArrayList<RepInfo>();

        if (zippcode.equals("94704")) {
            RepInfo ci = new RepInfo();
            ci.rep_name = barbara.get(0);
            ci.party = barbara.get(1);
            ci.tweet = barbara.get(2);
            ci.photo = R.drawable.barbara_s;
            result.add(ci);

            RepInfo inefficient = new RepInfo();
            inefficient.rep_name = dianne.get(0);
            inefficient.party = dianne.get(1);
            inefficient.tweet = dianne.get(2);
            inefficient.photo = R.drawable.dianne_s;
            result.add(inefficient);

            RepInfo third = new RepInfo();
            third.rep_name = mary.get(0);
            third.party = mary.get(1);
            third.tweet = mary.get(2);
            third.photo = R.drawable.mary;
            result.add(third);
        }

        if (zippcode.equals("99999")) {
            RepInfo ci = new RepInfo();
            ci.rep_name = hermione.get(0);
            ci.party = hermione.get(1);
            ci.tweet = hermione.get(2);
            ci.photo = R.drawable.hermione;
            result.add(ci);

            RepInfo inefficient = new RepInfo();
            inefficient.rep_name = fleur.get(0);
            inefficient.party = fleur.get(1);
            inefficient.tweet = fleur.get(2);
            inefficient.photo = R.drawable.fleur;
            result.add(inefficient);

            RepInfo third = new RepInfo();
            third.rep_name = cedric.get(0);
            third.party = cedric.get(1);
            third.tweet = cedric.get(2);
            third.photo = R.drawable.cedric;
            result.add(third);
        }

        return result;
    }

}
