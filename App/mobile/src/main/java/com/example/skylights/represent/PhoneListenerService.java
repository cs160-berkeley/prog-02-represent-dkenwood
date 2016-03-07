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

        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}
