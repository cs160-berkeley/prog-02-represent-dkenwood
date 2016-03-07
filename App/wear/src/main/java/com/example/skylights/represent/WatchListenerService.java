package com.example.skylights.represent;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by skylights on 3/5/2016.
 */
public class WatchListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());

        if( messageEvent.getPath().equalsIgnoreCase("/zip") ) {
            Log.d("T", "Got it!");
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, DisplayReps.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("zippy", value);
            startActivity(intent);
        } else if ( messageEvent.getPath().equalsIgnoreCase("/county") ) {
            Log.d("T", "Got it!");
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, DisplayVote.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("county", value);
            startActivity(intent);
        } else if (messageEvent.getPath().equalsIgnoreCase("/location")) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, DisplayReps.class);
            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("zippy", value);
            Log.d("T", "about to start watch MainActivity with LOCCY");
            startActivity(intent);
        } else {
            super.onMessageReceived( messageEvent );
        }

    }

}