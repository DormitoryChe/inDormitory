package com.example.indormitory;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * Created by Jeckk on 12.03.2018.
 */

public class BaseActivity extends AppCompatActivity {

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_option_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_item).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default

        MenuItem searchItem = menu.findItem(R.id.search_item);
        searchItem.expandActionView();
        searchView.clearFocus();

        /*
        try {
            Field searchField = SearchView.class.getDeclaredField("mBackButton");
            searchField.setAccessible(true);
            ImageView mSearchBackButton = (ImageView) searchField.get(searchView);

            if (mSearchBackButton != null) {
                Log.e("BaseActivity", "not null");
                mSearchBackButton.setVisibility(View.GONE);
                mSearchBackButton.setEnabled(false);
                mSearchBackButton.setImageDrawable(getResources().getDrawable(R.drawable.arrow_up));
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
*/

        String activityName = getClass().getSimpleName();
        if (activityName.equals(ScanActivity.class.getSimpleName())) {
            disableLogin(menu);
            disableSearch(menu);
            disableShoppingCart(menu);
        } else if (activityName.equals(ReservationActivity.class.getSimpleName())) {
            disableSearch(menu);
        } else if (activityName.equals(ShoppingCartActivity.class.getSimpleName())) {
            disableSearch(menu);
        } else if (activityName.equals(ItemMenuActivity.class.getSimpleName())) {
            disableSearch(menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login_button:
                startLoginActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void disableLogin(Menu menu) {
        menu.findItem(R.id.login_button).setEnabled(false);
        menu.findItem(R.id.login_button).setVisible(false);
    }

    private void disableSearch(Menu menu) {
        menu.findItem(R.id.search_item).setEnabled(false);
        menu.findItem(R.id.search_item).setVisible(false);
    }

    private void disableShoppingCart(Menu menu) {
        menu.findItem(R.id.shopping_cart).setEnabled(false);
        menu.findItem(R.id.shopping_cart).setVisible(false);
    }
}
