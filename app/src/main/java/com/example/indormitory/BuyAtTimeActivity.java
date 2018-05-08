package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.TimePicker;

/**
 * Created by Jeckk on 18.03.2018.
 */

public class BuyAtTimeActivity extends BaseActivity {
    private TimePicker timePicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_at_time);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        timePicker = findViewById(R.id.spinner);

        findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.toolbar_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUserLoggedIn()) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                } else
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        findViewById(R.id.toolbar_shopping_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyAtTimeActivity.this, ShoppingCartActivity.class));
            }
        });
        findViewById(R.id.go_to_reservation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyAtTimeActivity.this, ReservationActivity.class));
            }
        });
        timePicker.setIs24HourView(true);
    }
}
