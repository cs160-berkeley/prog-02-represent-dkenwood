package com.example.skylights.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_ZIP = "com.skylights.represent.MESSAGE";

    private Button mZipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mZipButton = (Button) findViewById(R.id.button_send);
//
//        mZipButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
//                sendIntent.putExtra("CAT_NAME", "Fred");
//                startService(sendIntent);
//            }
//        });
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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

        return super.onOptionsItemSelected(item);
    }

    public void searchZip(View view) {
        EditText editZip = (EditText) findViewById(R.id.enterzip);
        String message = editZip.getText().toString();

        Intent sendIntent = new Intent(this, PhoneToWatchService.class);
        sendIntent.putExtra("zip", message);
        startService(sendIntent);

        Intent intent = new Intent(this, DisplayZipActivity.class);
        intent.putExtra("zip_code", message);
        startActivity(intent);
    }

    public void useCurrLoc(View view) {
        String currLoc = "94704";
        Intent sendIntent = new Intent(this, PhoneToWatchService.class);
        sendIntent.putExtra("curr_zip_code", currLoc);
        startService(sendIntent);

        Intent intent = new Intent(this, DisplayCurrLocActivity.class);
        intent.putExtra("curr_zip_code", currLoc);
        startActivity(intent);
    }

}
