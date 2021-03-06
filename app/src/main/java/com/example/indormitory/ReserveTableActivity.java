package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TimePicker;

import com.example.indormitory.services.TimerService;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import ua.privatbank.paylibliqpay.ErrorCode;
import ua.privatbank.paylibliqpay.LiqPay;
import ua.privatbank.paylibliqpay.LiqPayCallBack;

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
                if(isUserLoggedIn()) {
                    startActivity(new Intent(ReserveTableActivity.this, ProfileActivity.class));
                } else
                    startSignInActivity();
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
                if(isUserLoggedIn()) {
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");
                    Log.e("Basket", dateFormatter.format(new Date()));
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                } else {
                    redirectUserToLogin();
                }
            }
        });
        findViewById(R.id.reserve_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUserLoggedIn()) {
                    LiqPayCallBack callBack = new LiqPayCallBack() {
                        @Override
                        public void onResponseSuccess(final String resp) {
                            //TODO on responce success
                        }
                        @Override
                        public void onResponceError(final ErrorCode errorCode) {
                            //TODO on responce error
                        }
                    };
                    HashMap<String, String> map = new HashMap<>();
                    map.put("version", "3");
                    map.put("public_key", "i80499295503");
                    map.put("action", "pay");
                    map.put("amount", "1");
                    map.put("currency", "UAH");
                    map.put("description", "Оплата: Пакет 20");
                    map.put("order_id", String.valueOf(Math.random()*999999));
                    map.put("language", "ru");
                    map.put("server_url", "http://***.gq");
                    map.put("sandbox", "1");
                    String privateKey = "KaiuCPXH9uIEyH6VI6MV0R72sC1pFMKFSZZ4g0z0";
                    Log.e("Basket", "Before checkout");
                    LiqPay.checkout(getApplicationContext(), map, privateKey, callBack);
                } else {
                    redirectUserToLogin();
                }
            }
        });
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
