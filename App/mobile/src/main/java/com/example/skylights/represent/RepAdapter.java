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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by skylights on 3/4/2016.
 */
public class RepAdapter extends RecyclerView.Adapter<RepAdapter.RepViewHolder> {

    private List<RepInfo> repList;

    public RepAdapter(List<RepInfo> repList) {
        this.repList = repList;
    }

    @Override
    public int getItemCount() {
        return repList.size();
    }

    @Override
    public void onBindViewHolder(RepViewHolder repViewHolder, int i) {
        RepInfo ci = repList.get(i);
        repViewHolder.vName.setText(ci.rep_name);
        repViewHolder.vParty.setText(ci.party);
        repViewHolder.vTweet.setText(ci.tweet);
        repViewHolder.vPhoto.setImageDrawable(ContextCompat.getDrawable(repViewHolder.context, ci.photo));
        if (ci.party.equals("Republican")) {
            repViewHolder.vParty.setTextColor(Color.parseColor("#EB5757"));
        }
        if (ci.party.equals("Democrat")) {
            repViewHolder.vParty.setTextColor(Color.parseColor("#2D9CDB"));
        }
    }

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
        protected TextView vTweet;
        protected ImageView vPhoto;
        protected Context context;

        public RepViewHolder(View v) {
            super(v);
            context = v.getContext();
            vName =  (TextView) v.findViewById(R.id.card_name);
            vParty = (TextView)  v.findViewById(R.id.card_party);
            vTweet = (TextView)  v.findViewById(R.id.tweet);
            vPhoto = (ImageView)  v.findViewById(R.id.photo);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String the_name = vName.getText().toString();
                    String the_party = vParty.getText().toString();

                    if (the_name.equals("Mary Lookalike") || the_name.equals("Cedric Diggory")) {
                        Intent intent = new Intent(v.getContext(), RepProfile2.class);
                        intent.putExtra("the_name", the_name);
                        intent.putExtra("the_party", the_party);
                        v.getContext().startActivity(intent);
                    } else {
                        Intent intent = new Intent(v.getContext(), RepProfile.class);
                        intent.putExtra("the_name", the_name);
                        intent.putExtra("the_party", the_party);
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }
}