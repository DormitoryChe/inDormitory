package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Jeckk on 12.03.2018.
 */

public class ReservationActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        mProfileImageView = findViewById(R.id.toolbar_profile);
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
    }
}
