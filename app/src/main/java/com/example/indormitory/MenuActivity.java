package com.example.indormitory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * Created by Jeckk on 12.03.2018.
 */

public class MenuActivity extends BaseActivity {
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private String[] items = {"Алкоголь", "Булочки", "М'ясні страви", "Більше алкоголю"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mViewPager = findViewById(R.id.menu_view_pager);
        mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setClipToPadding(false);
//        mViewPager.setPadding(40,0,40,0);
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MenuItemFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return items.length;
        }
    }
}
