package com.example.indormitory;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by vproh on 15.03.2018.
 */

public class SearchableActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Get the intent, ve rify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch (String text) {
        // TODO search
        Log.e("BaseActivity", "Entered text = " + text);
    }
}
