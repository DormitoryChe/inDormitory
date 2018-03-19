package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.indormitory.models.AllDishes;
import com.example.indormitory.models.Dish;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeckk on 12.03.2018.
 */

public class MenuActivity extends BaseActivity {
    private View mMenuContainer;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private String[] items = {"Алкоголь", "Булочки", "М'ясні страви", "Більше алкоголю"};
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_menu);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

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

        mProfileImageButton = findViewById(R.id.toolbar_profile);
        mProfileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentUser == null) {
                    startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                } else
                    startActivity(new Intent(MenuActivity.this, ProfileActivity.class));
            }
        });
        mShoppingCartImageButton = findViewById(R.id.toolbar_shopping_cart);
        mShoppingCartImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, ShoppingCartActivity.class));
            }
        });

        mMenuContainer = findViewById(R.id.menu_container);
        mSearchView = findViewById(R.id.search);
        initializeSearch();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSearchView.setQuery("", false);
        mMenuContainer.requestFocus();
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