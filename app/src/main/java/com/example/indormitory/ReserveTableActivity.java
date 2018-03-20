package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Jeckk on 18.03.2018.
 */

public class ReserveTableActivity extends BaseActivity {
    private TimePicker timePicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_table);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        timePicker = findViewById(R.id.spinner);
        timePicker.setIs24HourView(true);

        findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.toolbar_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentUser == null) {
                    startActivity(new Intent(ReserveTableActivity.this, LoginActivity.class));
                } else
                    startActivity(new Intent(ReserveTableActivity.this, ProfileActivity.class));
            }
        });
        findViewById(R.id.toolbar_shopping_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReserveTableActivity.this, ShoppingCartActivity.class));
            }
        });
        findViewById(R.id.reserve_and_buy_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentUser == null) {
                    Toast.makeText(ReserveTableActivity.this, "You are not authorized. Please login or signin", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ReserveTableActivity.this, LoginActivity.class));
                } else
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            }
        });
        findViewById(R.id.reserve_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentUser == null) {
                    Toast.makeText(ReserveTableActivity.this, "You are not authorized. Please login or signin", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ReserveTableActivity.this, LoginActivity.class));
                }
            }
        });
    }
}
