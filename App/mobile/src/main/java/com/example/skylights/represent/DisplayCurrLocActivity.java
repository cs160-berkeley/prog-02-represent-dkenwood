package com.example.skylights.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by skylights on 3/4/2016.
 */
public class DisplayCurrLocActivity extends AppCompatActivity {

    List<String> barbara = Arrays.asList("Barbara Boxer", "Democrat", "@boxer It's time to put our brightest minds to work.");
    List<String> dianne = Arrays.asList("Dianne Repubstein", "Republican", "@dianne Well, I've decided to switch parties.");
    List<String> mary = Arrays.asList("Mary Lookalike", "Democrat", "@mary This is my first official tweet.");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_currloc_screen);

        Intent intent = getIntent();
        String zipcode = intent.getStringExtra("curr_zip_code");

        TextView the_address = (TextView) findViewById(R.id.address);
        the_address.setText(getString(R.string.address));

        RecyclerView recList = (RecyclerView) findViewById(R.id.the_repList);
//        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        RepAdapter ca = new RepAdapter(createList());
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

    private List<RepInfo> createList() {

        List<RepInfo> result = new ArrayList<RepInfo>();

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

        return result;
    }

}
