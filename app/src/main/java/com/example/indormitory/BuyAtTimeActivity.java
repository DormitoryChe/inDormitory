package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TimePicker;

import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

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
                    startSignInActivity();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Firebase", "on Activity Result");
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
            } else {
                // Sign in failed, check response for error code
                // ...
            }
        }
    }
}
