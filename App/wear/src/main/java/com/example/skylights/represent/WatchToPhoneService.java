package com.example.skylights.represent;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skylights on 3/5/2016.
 */
public class WatchToPhoneService extends Service implements GoogleApiClient.ConnectionCallbacks {

        private static GoogleApiClient mWatchApiClient;
        private static String path;
        private static String text;

        public static void sendMessage(final String path, final String text, Context context ) {
            WatchToPhoneService.path = path;
            WatchToPhoneService.text = text;
            mWatchApiClient = new GoogleApiClient.Builder(context)
                    .addApi( Wearable.API )
                    .addConnectionCallbacks(new WatchToPhoneService())
                    .build();
            //and actually connect it
            mWatchApiClient.connect();
        }


        @Override //alternate method to connecting: no longer create this in a new thread, but as a callback
        public void onConnected(Bundle bundle) {
            Log.d("T", "in onconnected");
            Wearable.NodeApi.getConnectedNodes(mWatchApiClient)
                    .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                        @Override
                        public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                            List<Node> nodes = getConnectedNodesResult.getNodes();
                            Log.d("Cheese", "Found nodes");
                            //when we find a connected node, we populate the list declared above
                            //finally, we can send a message
                            for (Node node : nodes) {
                                Wearable.MessageApi.sendMessage(
                                        mWatchApiClient, node.getId(), path, text.getBytes());
                            }
                            Log.d("Cheese", "Sent");
                            mWatchApiClient.disconnect();
                        }
                    });
        }


        @Override //we need this to implement GoogleApiClient.ConnectionsCallback
        public void onConnectionSuspended(int i) {}

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
}