package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by Jeckk on 18.03.2018.
 */

public class BuyAtTimeActivity extends BaseActivity {
    private ImageView mBackImageView;
    private TextView goToReservationTextView;
    private TimePicker timePicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_at_time);

        mBackImageView = findViewById(R.id.toolbar_back);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mProfileImageButton = findViewById(R.id.toolbar_profile);
        mProfileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyAtTimeActivity.this, LoginActivity.class));
            }
        });
        mShoppingCartImageButton = findViewById(R.id.toolbar_shopping_cart);
        mShoppingCartImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyAtTimeActivity.this, ShoppingCartActivity.class));
            }
        });
        goToReservationTextView = findViewById(R.id.go_to_reservation);
        goToReservationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyAtTimeActivity.this, ReservationActivity.class));
            }
        });
        timePicker = findViewById(R.id.spinner);
        timePicker.setIs24HourView(true);
    }
}
