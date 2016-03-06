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
    // In PhoneToWatchService, we passed in a path, either "/FRED" or "/LEXY"
    // These paths serve to differentiate different phone-to-watch messages
//    private static final String BERKELEY = "/94704";
//    private static final String HOGWARTS = "/99999";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());

        if( messageEvent.getPath().equalsIgnoreCase("/zip") ) {
            Log.d("OMR", "Got it!");
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, DisplayReps.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("zippy", value);
            startActivity(intent);
        } else if (messageEvent.getPath().equalsIgnoreCase("/curr_zip_code")) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, DisplayReps.class );
            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("loccy", value);
            Log.d("T", "about to start watch MainActivity with CAT_NAME: Lexy");
            startActivity(intent);
        } else {
            super.onMessageReceived( messageEvent );
        }

    }


//    @Override
//    public void onMessageReceived(MessageEvent messageEvent) {
//        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());
//        //use the 'path' field in sendmessage to differentiate use cases
//        //(here, fred vs lexy)
//
//        String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//        Intent intent = new Intent(this, MainActivity.class );
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        //you need to add this flag since you're starting a new activity from a service
//        intent.putExtra("REP_NAME", "94704");
//        Log.d("T", "about to start watch MainActivity with REP_NAME: 94704");
//        startActivity(intent);
//
////        if( messageEvent.getPath().equalsIgnoreCase( BERKELEY ) ) {
////            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
////            Intent intent = new Intent(this, MainActivity.class );
////            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////            //you need to add this flag since you're starting a new activity from a service
////            intent.putExtra("REP_NAME", "Fred");
////            Log.d("T", "about to start watch MainActivity with REP_NAME: Fred");
////            startActivity(intent);
////        } else if (messageEvent.getPath().equalsIgnoreCase( HOGWARTS )) {
////            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
////            Intent intent = new Intent(this, MainActivity.class );
////            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
////            //you need to add this flag since you're starting a new activity from a service
////            intent.putExtra("REP_NAME", "Lexy");
////            Log.d("T", "about to start watch MainActivity with REP_NAME: Lexy");
////            startActivity(intent);
////        } else {
////            super.onMessageReceived( messageEvent );
////        }
//
//    }
}