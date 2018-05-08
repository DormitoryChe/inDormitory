package com.example.indormitory;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by vproh on 17.03.2018.
 */

public class BaseActivity extends AppCompatActivity {
    SearchView mSearchView;
    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
    }

    public void initializeSearch() {
        SearchView searchView = findViewById(R.id.search);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        // Assumes current activity is the searchable activity
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        if(mSearchView != null) {
            mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
                boolean isSearchActive = true;
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    int visibility = View.GONE;
                    Log.e("BaseActivity", "Is search active = " + isSearchActive);
                    Log.e("BaseActivity", "Visibility = " + visibility);
                    isSearchActive = !isSearchActive;
                    if(isSearchActive)
                        visibility = View.VISIBLE;
                    findViewById(R.id.toolbar_profile).setVisibility(visibility);
                    findViewById(R.id.toolbar_shopping_cart).setVisibility(visibility);
                }
            });
        }
    }

    public boolean isUserLoggedIn() {
        return mCurrentUser != null;
    }

    public void redirectUserToLogin() {
        Toast.makeText(getApplicationContext(), "You are not authorized. Please login or signin", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void initView() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSPARENT);
        params.gravity = Gravity.BOTTOM | Gravity.END;
        params.horizontalMargin = 20;
        params.verticalMargin = 40;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        LayoutInflater layOutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View viewAboveAllActivities = layOutInflater.inflate(R.layout.reserve_timer, null);

        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(viewAboveAllActivities, params);
    }
}
