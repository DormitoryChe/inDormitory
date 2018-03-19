package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;
import android.widget.TabHost;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class NewsActivity extends BaseActivity {
    private TabHost mTabHost;
    private TabHost.TabSpec mTabSpec;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_news);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        Fragment fragment = new Fragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.navigation_menu_fragment, fragment).commit();
        mTabHost = findViewById(R.id.tabhost);
        configureTab();

        final ViewPager viewPager = findViewById(R.id.pager);
        final NewsAndReviewsAdapter newsAndReviewsAdapter = new NewsAndReviewsAdapter(getSupportFragmentManager(), 2);
        viewPager.setAdapter(newsAndReviewsAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("Basket", "on Selected");
                if(position == 0)
                    mTabHost.setCurrentTabByTag("tag1");
                if(position == 1)
                    mTabHost.setCurrentTabByTag("tag2");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId.equals("tag1"))
                    viewPager.setCurrentItem(0);
                else
                    viewPager.setCurrentItem(1);
            }
        });

        mProfileImageButton = findViewById(R.id.toolbar_profile);
        mProfileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentUser == null) {
                    startActivity(new Intent(NewsActivity.this, LoginActivity.class));
                } else
                    startActivity(new Intent(NewsActivity.this, ProfileActivity.class));
            }
        });
        mShoppingCartImageButton = findViewById(R.id.toolbar_shopping_cart);
        mShoppingCartImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewsActivity.this, ShoppingCartActivity.class));
            }
        });
        mSearchView = findViewById(R.id.search);
        initializeSearch();
    }

    private void configureTab() {
        mTabHost.setup();
        mTabSpec = mTabHost.newTabSpec("tag1");
        mTabSpec.setIndicator("News");
        mTabSpec.setContent(R.id.news_recycler_view);
        mTabHost.addTab(mTabSpec);
        mTabSpec = mTabHost.newTabSpec("tag2");
        mTabSpec.setIndicator("Reviews");
        mTabSpec.setContent(R.id.reviews_recycler_view);
        mTabHost.addTab(mTabSpec);
    }
}
