package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeckk on 18.03.2018.
 */

public class ReserveTableActivity extends BaseActivity {
    private TimePicker timePicker;
    private ImageView mBackImageView;
    private Button menuButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_table);
        timePicker = findViewById(R.id.spinner);
        timePicker.setIs24HourView(true);

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
                startActivity(new Intent(ReserveTableActivity.this, LoginActivity.class));
            }
        });
        mShoppingCartImageButton = findViewById(R.id.toolbar_shopping_cart);
        mShoppingCartImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReserveTableActivity.this, ShoppingCartActivity.class));
            }
        });

        menuButton = findViewById(R.id.reserve_and_buy_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            }
        });
    }
}
