package com.example.skylights.represent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by skylights on 3/6/2016.
 */
public class GridPagerAdapter extends FragmentGridPagerAdapter {

    private final Context mContext;
//    private List mRows;
    List<DisplayReps.Representative> mReps;

    private static final float MAXIMUM_CARD_EXPANSION_FACTOR = 3.0f;

    public GridPagerAdapter(Context ctx, List<DisplayReps.Representative> reps, FragmentManager fm) {
        super(fm);
        mContext = ctx;
        mReps = reps;
    }

    @Override
    public Fragment getFragment(int row, int col) {
//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        ViewGroup customcard = (ViewGroup) inflater.inflate(R.layout.custom_card, null);
        DisplayReps.Representative rep = mReps.get(row);
        CardFragment fragment = new MyCardFragment();
        fragment = MyCardFragment.create(rep.getName(col), rep.getParty(col));
//        fragment.onCreateContentView(inflater, customcard, );
        fragment.setCardGravity(Gravity.BOTTOM);
        fragment.setExpansionEnabled(true);
        fragment.setExpansionDirection(CardFragment.EXPAND_DOWN);
        fragment.setExpansionFactor(MAXIMUM_CARD_EXPANSION_FACTOR);
        return fragment;
    }

    public Drawable getBackgroundForPage(int row, int column) {
        if( row == 2 && column == 1) {
            // Place image at specified position
            return mContext.getResources().getDrawable(R.drawable.demo_bg, null);
        } else {
            // Default to background image for row
            return GridPagerAdapter.BACKGROUND_NONE;
        }
    }

    // Obtain the number of pages (vertical)
    @Override
    public int getRowCount() {
        return mReps.size();
    }

    // Obtain the number of pages (horizontal)
    @Override
    public int getColumnCount(int rowNum) {
        return 1;
    }

    @Override
    public Drawable getBackgroundForRow(int row) {
        return ContextCompat.getDrawable(mContext, mReps.get(row).getImageResource());
    }
}
