package com.example.indormitory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by Jeckk on 12.03.2018.
 */

public class BaseActivity extends AppCompatActivity {

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void startShoppingCartActivity() {
        Intent intent = new Intent(this, ShoppingCartActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_option_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login_button:
                startLoginActivity();
                return true;
            case R.id.shopping_cart:
                startShoppingCartActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
