package com.example.skylights.represent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;
import java.util.Random;

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
        Random randomGenerator = new Random();
        for (int idx = 1; idx <= 1; ++idx) {
            int randomInt = randomGenerator.nextInt(8);
            if (randomInt == 0) {
                intent.putExtra("zip_code", "20601");
            }
            if (randomInt == 1) {
                intent.putExtra("zip_code", "75002");
            }
            if (randomInt == 2) {
                intent.putExtra("zip_code", "83210");
            }
            if (randomInt == 3) {
                intent.putExtra("zip_code", "13760");
            }
            if (randomInt == 4) {
                intent.putExtra("zip_code", "32907");
            }
            if (randomInt == 5) {
                intent.putExtra("zip_code", "85224");
            }
            if (randomInt == 6) {
                intent.putExtra("zip_code", "68801");
            }
            if (randomInt == 7) {
                intent.putExtra("zip_code", "55318");
            }
            if (randomInt == 8) {
                intent.putExtra("zip_code", "59601");
            }
        }
        Log.d("T", "about to start watch MainActivity with SENT_ZIP");
        startActivity(intent);
        if( messageEvent.getPath().equalsIgnoreCase("/94704") ) {

        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}
