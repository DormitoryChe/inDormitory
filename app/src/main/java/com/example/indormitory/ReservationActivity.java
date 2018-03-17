package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import com.example.indormitory.models.News;

/**
 * Created by Jeckk on 12.03.2018.
 */

public class ReservationActivity extends BaseActivity {
   private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        mProfileImageView = findViewById(R.id.toolbar_profile);
      //  mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);

        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReservationActivity.this, LoginActivity.class));
            }
        });

        mShoppingCartImageView = findViewById(R.id.toolbar_shopping_cart);
        mShoppingCartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReservationActivity.this, ShoppingCartActivity.class));
            }
        });

       mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("BaseActivity", "OnRefresh");
                mSwipeRefreshLayout.setRefreshing(false);
                startActivity(new Intent(getApplicationContext(), ReservationActivity.class));
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorMenuActive);
        //mSwipeRefreshLayout.setClipChildren(false);
        mSwipeRefreshLayout.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
            @Override
            public boolean canChildScrollUp(@NonNull SwipeRefreshLayout parent, @Nullable View child) {
                if (child != null)
                    return child.canScrollVertically(-1);

                return false;
            }
        });
    }
}
