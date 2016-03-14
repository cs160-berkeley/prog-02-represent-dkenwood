package com.example.skylights.represent;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;
import com.twitter.sdk.android.tweetui.UserTimeline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import io.fabric.sdk.android.Fabric;

/**
 * Created by skylights on 3/4/2016.
 */
public class RepAdapter extends RecyclerView.Adapter<RepAdapter.RepViewHolder> {

    private List<RepInfo> repList;

    protected static Context the_context;

    private static final String TWITTER_KEY = "C10FdIwGkqm0CVN76Npodg7VY";
    private static final String TWITTER_SECRET = "xpuGNxPEVUUxIpqR9BhkBABl94zREADkogULVfsCyzBlRGHlX7";

    public RepAdapter(List<RepInfo> repList) {
        this.repList = repList;
    }

    @Override
    public int getItemCount() {
        return repList.size();
    }

    @Override
    public void onBindViewHolder(RepViewHolder repViewHolder, int i) {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(repViewHolder.context, new Twitter(authConfig));

        the_context = repViewHolder.context;

        RepInfo ci = repList.get(i);
        repViewHolder.vName.setText(ci.rep_name);
        repViewHolder.vParty.setText(ci.party);
        repViewHolder.vTweet.setText(ci.tweet);
        if (ci.house.equals("house")) {
            repViewHolder.vHouse.setText("House Rep");
        }
        if (ci.house.equals("senate")) {
            repViewHolder.vHouse.setText("Senator");
        }
        repViewHolder.vHiddenPhoto.setText(ci.photo);
        repViewHolder.vHiddenPhoto.setVisibility(View.INVISIBLE);
        repViewHolder.vCounty.setText(ci.county);
        repViewHolder.vCounty.setVisibility(View.INVISIBLE);

        Picasso.with(repViewHolder.context).load("https://theunitedstates.io/images/congress/225x275/" +
                ci.photo + ".jpg").into(repViewHolder.vPhoto);

//        repViewHolder.vPhoto.setImageDrawable(ContextCompat.getDrawable(repViewHolder.context, ci.photo));
        if (ci.party.equals("Republican")) {
            repViewHolder.vParty.setTextColor(Color.parseColor("#EB5757"));
        }
        if (ci.party.equals("Democrat")) {
            repViewHolder.vParty.setTextColor(Color.parseColor("#2D9CDB"));
        }
        if (ci.party.equals("Independent")) {
            repViewHolder.vParty.setTextColor(Color.parseColor("#666699"));
        }

////        final ViewGroup parentView = (ViewGroup) getWindow().getDecorView().getRootView();
////        final ViewGroup parentView = repViewHolder.vTweet;
//        String tweeter = "RepBarbaraLee";

//        final UserTimeline userTimeline = new UserTimeline.Builder()
//                .screenName("fabric")
//                .build();
//        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(the_context)
//                .setTimeline(userTimeline)
//                .build();
//        setListAdapter(adapter);

//        try {
//            getPublicTimeline(tweeter);
//        } catch(Exception e) {
//            Log.e("ERROR", e.getMessage(), e);
//        }
//
//        TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
//            @Override
//            public void success(Result<Tweet> result) {
//                TweetView tweetView = new TweetView(the_context, result.data);
//                parentView.addView(tweetView);
//            }
//
//            @Override
//            public void failure(TwitterException exception) {
//                Log.d("TwitterKit", "Load Tweet failure", exception);
//            }
//        });

    }

//    public void getPublicTimeline(String twitter) throws JSONException {
//
//        RequestParams params = new RequestParams();
//        params.put("screen_name", twitter);
//        params.put("count", "1");
//        TwitterRestClient.get("statuses/user_timeline.json", params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // If the response is JSONObject instead of expected JSONArray
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
//                // Pull out the first event on the public timeline
//                try {
//                    final JSONObject first = timeline.getJSONObject(0);
//                    String tweetText = first.getString("text");
//                    System.out.println(tweetText);
//                    System.out.println("lalalalalalala");
//                } catch(Exception e) {
//                    Log.e("ERROR", e.getMessage(), e);
//                }
//
//            }
//        });
//    }

    @Override
    public RepViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new RepViewHolder(itemView);
    }

    public static class RepViewHolder extends RecyclerView.ViewHolder {
        protected TextView vName;
        protected TextView vParty;
        protected TextView vHouse;
        protected TextView vTweet;
        protected ImageView vPhoto;
        protected TextView vHiddenPhoto;
        protected TextView vCounty;
//        protected ViewGroup vTweet;
        protected Context context;

        public RepViewHolder(View v) {
            super(v);
            context = v.getContext();
            vName =  (TextView) v.findViewById(R.id.card_name);
            vParty = (TextView)  v.findViewById(R.id.card_party);
            vTweet = (TextView)  v.findViewById(R.id.tweet);
            vHouse = (TextView)  v.findViewById(R.id.house);
            vHiddenPhoto = (TextView)  v.findViewById(R.id.secretphoto);
            vCounty = (TextView)  v.findViewById(R.id.secretcounty);
            vPhoto = (ImageView)  v.findViewById(R.id.photo);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String the_name = vName.getText().toString();
                    String the_party = vParty.getText().toString();
                    String the_house = vHouse.getText().toString();
                    String the_photo = vHiddenPhoto.getText().toString();
                    String the_county = vCounty.getText().toString();

                    if (the_house.equals("House Rep")) {
                        Intent intent = new Intent(v.getContext(), RepProfile2.class);
                        intent.putExtra("the_name", the_name);
                        intent.putExtra("the_party", the_party);
                        intent.putExtra("the_photo", the_photo);
                        intent.putExtra("the_county", the_county);
                        v.getContext().startActivity(intent);
                    } else {
                        Intent intent = new Intent(v.getContext(), RepProfile.class);
                        intent.putExtra("the_name", the_name);
                        intent.putExtra("the_party", the_party);
                        intent.putExtra("the_photo", the_photo);
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }
}