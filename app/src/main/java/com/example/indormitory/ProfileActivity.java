package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.indormitory.models.Orders;
import com.firebase.ui.auth.AuthUI;

/**
 * Created by Ростислав on 19.03.2018.
 */

public class ProfileActivity extends BaseActivity {
    private LinearLayout wrapper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        wrapper = findViewById(R.id.content_wrapper);
        if(!isUserLoggedIn())
            startSignInActivity();
        findViewById(R.id.toolbar_shopping_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ShoppingCartActivity.class));
            }
        });
        findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(getApplicationContext());
                startActivity(new Intent(ProfileActivity.this, NewsActivity.class));
            }
        });

        findViewById(R.id.current_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Orders.get().isEmptyOrders()) {
                    getSupportFragmentManager().beginTransaction().add(wrapper.getId(), new CurrentOrderFragment(), "currentOrders").commit();
                    for(int i = 0; i < wrapper.getChildCount(); i ++) {
                        View child = wrapper.getChildAt(i);
                        child.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}
