package com.example.indormitory;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

/**
 * Created by vproh on 17.03.2018.
 */

public class BaseActivity extends AppCompatActivity {
    public static final int RC_SIGN_IN = 123;

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
        startSignInActivity();
    }

    public void startSignInActivity() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.EmailBuilder().build(),
                                new AuthUI.IdpConfig.GoogleBuilder().build(),
                                new AuthUI.IdpConfig.PhoneBuilder().build(),
                                new AuthUI.IdpConfig.FacebookBuilder().build()))
                        .setLogo(R.drawable.ic_donut)
                        .build(),
                RC_SIGN_IN);
    }
}
