package com.example.skylights.represent;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by skylights on 3/6/2016.
 */
public class MyCardFragment extends CardFragment {

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.custom_card);
//
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        CardFragment cardFragment = CardFragment.create(getString(R.string.card_fragment_title),
//                getString(R.string.card_fragment_text),
//                R.drawable.card_icon);
//        cardFragment.setCardGravity(Gravity.BOTTOM);
//        transaction.add(R.id.container, cardFragment);
//        transaction.commit();
//    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("Zootopia", "in oncreate, got:");
        Toast.makeText(getActivity(), "Something happened!", Toast.LENGTH_SHORT).show();

        View v = inflater.inflate(R.layout.custom_card, container, false);
        TextView the_name = (TextView) v.findViewById(R.id.name);
        the_name.setText(KEY_TITLE);
        TextView the_party = (TextView) v.findViewById(R.id.party);
        the_name.setText(KEY_TEXT);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String the_name = KEY_TITLE;
//                String the_party = KEY_TEXT;

                Toast.makeText(getActivity(), "Tapped!", Toast.LENGTH_SHORT).show();

//                if (the_name.equals("Mary Lookalike") || the_name.equals("Cedric Diggory")) {
//                    Intent intent = new Intent(v.getContext(), RepProfile2.class);
//                    intent.putExtra("the_name", the_name);
//                    intent.putExtra("the_party", the_party);
//                    v.getContext().startActivity(intent);
//                } else {
//                    Intent intent = new Intent(v.getContext(), RepProfile.class);
//                    intent.putExtra("the_name", the_name);
//                    intent.putExtra("the_party", the_party);
//                    v.getContext().startActivity(intent);
//                }
            }
        });
        return v;
    }
}
