package com.example.skylights.represent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by skylights on 3/5/2016.
 */
public class PhoneListenerService extends WearableListenerService {

    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    private static final String TOAST = "/send_location";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("Cheese", "in PhoneListenerService, got: " + messageEvent.getPath());
        Intent intent = new Intent(this, DisplayZipActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("zip_code", "94704");
        Log.d("T", "about to start watch MainActivity with SENT_ZIP");
        startActivity(intent);
        if( messageEvent.getPath().equalsIgnoreCase("/94704") ) {
//
////            // Value contains the String we sent over in WatchToPhoneService, "good job"
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//
//            Intent intent = new Intent(this, DisplayZipActivity.class);
//            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
//            //you need to add this flag since you're starting a new activity from a service
//            intent.putExtra("zip_code", value);
//            Log.d("T", "about to start watch MainActivity with SENT_ZIP");
//            startActivity(intent);
////
////            // Make a toast with the String
////            Context context = getApplicationContext();
////            int duration = Toast.LENGTH_SHORT;
////
////            Toast toast = Toast.makeText(context, value, duration);
////            toast.show();
//
//            // so you may notice this crashes the phone because it's
//            //''sending message to a Handler on a dead thread''... that's okay. but don't do this.
//            // replace sending a toast with, like, starting a new activity or something.
//            // who said skeleton code is untouchable? #breakCSconceptions

        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}
