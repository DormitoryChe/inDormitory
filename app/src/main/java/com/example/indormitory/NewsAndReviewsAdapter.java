package com.example.indormitory;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by Ростислав on 17.03.2018.
 */

public class NewsAndReviewsAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    NewsAndReviewsAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Log.e("Basket", "News fragment");
                return new NewsFragment();
            case 1:
                Log.e("Basket", "Reviews fragment");
                return new ReviewsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
