package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;

import com.example.indormitory.models.AllDishes;
import com.example.indormitory.models.Dish;

import java.util.ArrayList;
import java.util.List;

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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_menu);

        if(AllDishes.get().getAllDishes().size() == 0) {
            for(String item : items) {
                List<Dish> mDishesList = new ArrayList<>();
                for(int i = 0; i < 5; i ++)
                    mDishesList.add(new Dish("Olive", 50 + i, null, null, null));
                AllDishes.get().addDishesByOneMenuItem(item, mDishesList);
            }
        }

        mViewPager = findViewById(R.id.menu_view_pager);
        mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setClipToPadding(false);
        mProfileImageView = findViewById(R.id.toolbar_profile);
        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
            }
        });
        mShoppingCartImageView = findViewById(R.id.toolbar_shopping_cart);
        mShoppingCartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, ShoppingCartActivity.class));
            }
        });
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