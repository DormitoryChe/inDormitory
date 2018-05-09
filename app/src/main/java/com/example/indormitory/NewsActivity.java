
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

import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

public class NewsActivity extends BaseActivity {
    private TabHost mTabHost;
    private TabHost.TabSpec mTabSpec;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Fragment fragment = new Fragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.navigation_menu_fragment, fragment).commit();

        findViewById(R.id.toolbar_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUserLoggedIn()) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                } else {
                    startSignInActivity();
                }
            }
        });
        findViewById(R.id.toolbar_shopping_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ShoppingCartActivity.class));
            }
        });
        mTabHost = findViewById(R.id.tabhost);

        mSearchView = findViewById(R.id.search);
        initializeSearch();

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Firebase", "on Activity Result");
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
            } else {
                // Sign in failed, check response for error code
                // ...
            }
        }
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
