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

//    private GoogleApiClient mApiClient;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        //initialize the googleAPIClient for message passing
//        mApiClient = new GoogleApiClient.Builder( this )
//                .addApi( Wearable.API )
//                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
//                    @Override
//                    public void onConnected(Bundle connectionHint) {
//                    }
//
//                    @Override
//                    public void onConnectionSuspended(int cause) {
//                    }
//                })
//                .build();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mApiClient.disconnect();
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        // Which cat do we want to feed? Grab this info from INTENT
//        // which was passed over when we called startService
//        Bundle extras = intent.getExtras();
//        final String send;
//        final String value;
//
//        if (extras.getString("zip") != null) {
//            send = "/zip";
//            value = extras.getString("zip");
//        }  else if (extras.getString("the_county") != null) {
//            send = "/county";
//            value = extras.getString("the_county");
//        } else {
//            send = "/location";
//            value = extras.getString("curr_zip_code");
//        }
//        //final String zipCode = extras.getString("zip");
//
//        // Send the message with the cat name
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //first, connect to the apiclient
//                mApiClient.connect();
//                //now that you're connected, send a massage with the cat name
//                sendMessage(send, value);
//            }
//        }).start();
//
//        return START_STICKY;
//    }
//
//    @Override //remember, all services need to implement an IBinder
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    private void sendMessage( final String path, final String text ) {
//        //one way to send message: start a new thread and call .await()
//        //see watchtophoneservice for another way to send a message
//        new Thread( new Runnable() {
//            @Override
//            public void run() {
//                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( mApiClient ).await();
//                for(Node node : nodes.getNodes()) {
//                    //we find 'nodes', which are nearby bluetooth devices (aka emulators)
//                    //send a message for each of these nodes (just one, for an emulator)
//                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
//                            mApiClient, node.getId(), path, text.getBytes() ).await();
//                    //4 arguments: api client, the node ID, the path (for the listener to parse),
//                    //and the message itself (you need to convert it to bytes.)
//                }
//            }
//        }).start();
//    }
//
//}

//    public class WatchToPhoneService extends Service implements GoogleApiClient.ConnectionCallbacks {

        //
//    private static GoogleApiClient mWatchApiClient;
//    private static String path;
//    private static String text;
//
//    public static void sendMessage(final String path, final String text, Context context ) {
//        WatchToPhoneService.path = path;
//        WatchToPhoneService.text = text;
//        mWatchApiClient = new GoogleApiClient.Builder(context)
//                .addApi( Wearable.API )
//                .addConnectionCallbacks(new WatchToPhoneService())
//                .build();
//        //and actually connect it
//        mWatchApiClient.connect();
//    }
//
//
//    @Override //alternate method to connecting: no longer create this in a new thread, but as a callback
//    public void onConnected(Bundle bundle) {
//        Log.d("T", "in onconnected");
//        Wearable.NodeApi.getConnectedNodes(mWatchApiClient)
//                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
//                    @Override
//                    public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
//                        List<Node> nodes = getConnectedNodesResult.getNodes();
//                        Log.d("Cheese", "Found nodes");
//                        //when we find a connected node, we populate the list declared above
//                        //finally, we can send a message
//                        for (Node node : nodes) {
//                            Wearable.MessageApi.sendMessage(
//                                    mWatchApiClient, node.getId(), path, text.getBytes());
//                        }
//                        Log.d("Cheese", "Sent");
//                        mWatchApiClient.disconnect();
//                    }
//                });
//    }
//
//
//    @Override //we need this to implement GoogleApiClient.ConnectionsCallback
//    public void onConnectionSuspended(int i) {}
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }

//    private GoogleApiClient mWatchApiClient;
//    private List<Node> nodes = new ArrayList<>();
//    private static String path;
//    private static String text;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        //initialize the googleAPIClient for message passing
//        mWatchApiClient = new GoogleApiClient.Builder( this )
//                .addApi( Wearable.API )
//                .addConnectionCallbacks(this)
//                .build();
//        //and actually connect it
//        mWatchApiClient.connect();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mWatchApiClient.disconnect();
//    }
//
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override //alternate method to connecting: no longer create this in a new thread, but as a callback
//    public void onConnected(Bundle bundle) {
//        Log.d("T", "in onconnected");
//        Wearable.NodeApi.getConnectedNodes(mWatchApiClient)
//                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
//                    @Override
//                    public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
//                        nodes = getConnectedNodesResult.getNodes();
//                        Log.d("T", "found nodes");
//                        //when we find a connected node, we populate the list declared above
//                        //finally, we can send a message
//                        sendMessage("/send_location", "94704");
//                        Log.d("T", "sent");
//                    }
//                });
//    }
//
//    @Override //we need this to implement GoogleApiClient.ConnectionsCallback
//    public void onConnectionSuspended(int i) {}
//
//    private void sendMessage(final String path, final String text ) {
//        WatchToPhoneService.path = path;
//        WatchToPhoneService.text = text;
//        for (Node node : nodes) {
//            Wearable.MessageApi.sendMessage(
//                    mWatchApiClient, node.getId(), path, text.getBytes());
//        }
//    }
//
//}
