package com.example.indormitory;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Jeckk on 18.03.2018.
 */

public class ReserveTableActivity extends BaseActivity {
    private TimePicker timePicker;
    private ImageView mBackImageView;
    private Button menuButton;
    private Button reservationButton;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private AlertDialog.Builder alertBuilder;
    private AlertDialog alertDialog;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_table);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        inflater = (this).getLayoutInflater();
        alertBuilder = new AlertDialog.Builder(this);
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
                if(mCurrentUser == null) {
                    startActivity(new Intent(ReserveTableActivity.this, LoginActivity.class));
                } else
                    startActivity(new Intent(ReserveTableActivity.this, ProfileActivity.class));
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
                if(mCurrentUser == null) {
                    View dialogView = inflater.inflate(R.layout.not_authorized_alert, null);
                    alertBuilder.setTitle(null);
                    Log.e("Basket", "Busy");
                    alertBuilder.setCancelable(true);
                    alertBuilder.setView(dialogView);
                    alertDialog = alertBuilder.create();
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alertDialog.show();
                    ImageButton cancelButton;
                    cancelButton = dialogView.findViewById(R.id.cancel_button);
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.hide();
                        }
                    });
                    Button signInButton;
                    signInButton = dialogView.findViewById(R.id.sign_in_button);
                    signInButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(ReserveTableActivity.this, LoginActivity.class));
                        }
                    });
                } else
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            }
        });

        reservationButton = findViewById(R.id.reserve_button);
        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentUser == null) {
                    View dialogView = inflater.inflate(R.layout.not_authorized_alert, null);
                    alertBuilder.setTitle(null);
                    Log.e("Basket", "Busy");
                    alertBuilder.setCancelable(true);
                    alertBuilder.setView(dialogView);
                    alertDialog = alertBuilder.create();
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alertDialog.show();
                    ImageButton cancelButton;
                    cancelButton = dialogView.findViewById(R.id.cancel_button);
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.hide();

                        }
                    });
                    Button signInButton;
                    signInButton = dialogView.findViewById(R.id.sign_in_button);
                    signInButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(ReserveTableActivity.this, LoginActivity.class));
                        }
                    });
                }
            }
        });
    }
}
