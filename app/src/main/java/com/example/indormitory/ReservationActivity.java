package com.example.indormitory;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


/**
 * Created by Jeckk on 12.03.2018.
 */

public class ReservationActivity extends BaseActivity {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Button mTable1Button;
    private Button mTable2Button;
    private Button mTable3Button;
    private Button mTable4Button;
    private Button mTable5Button;
    private Button mTable6Button;
    private Button mTable7Button;
    private Button mTable8Button;
    private Button mTable9Button;
    private AlertDialog.Builder alertBuilder;
    private AlertDialog alertDialog;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        inflater = (this).getLayoutInflater();
        alertBuilder = new AlertDialog.Builder(this);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);

        mProfileImageButton = findViewById(R.id.toolbar_profile);
        mProfileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReservationActivity.this, LoginActivity.class));
            }
        });
        mShoppingCartImageButton = findViewById(R.id.toolbar_shopping_cart);
        mShoppingCartImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReservationActivity.this, ShoppingCartActivity.class));
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("BaseActivity", "OnRefresh");
                mSwipeRefreshLayout.setRefreshing(false);
                startActivity(new Intent(getApplicationContext(), ReservationActivity.class));
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorMenuActive);
        mSwipeRefreshLayout.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
            @Override
            public boolean canChildScrollUp(@NonNull SwipeRefreshLayout parent, @Nullable View child) {
                if (child != null)
                    return child.canScrollVertically(-1);

                return false;
            }
        });

        mTable1Button = findViewById(R.id.table_1_button);
        mTable2Button = findViewById(R.id.table_2_button);
        mTable3Button = findViewById(R.id.table_3_button);
        mTable4Button = findViewById(R.id.table_4_button);
        mTable5Button = findViewById(R.id.table_5_button);
        mTable6Button = findViewById(R.id.table_6_button);
        mTable7Button = findViewById(R.id.table_7_button);
        mTable8Button = findViewById(R.id.table_8_button);
        mTable9Button = findViewById(R.id.table_9_button);

        mTable1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogByButtonState(mTable1Button);
            }
        });
        mTable2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogByButtonState(mTable2Button);
            }
        });
        mTable3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogByButtonState(mTable3Button);
            }
        });
        mTable4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogByButtonState(mTable4Button);
            }
        });
        mTable5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogByButtonState(mTable5Button);
            }
        });
        mTable6Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogByButtonState(mTable6Button);
            }
        });
        mTable7Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogByButtonState(mTable7Button);
            }
        });
        mTable8Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogByButtonState(mTable8Button);
            }
        });
        mTable9Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogByButtonState(mTable9Button);
            }
        });
    }

    private void showAlertDialogByButtonState(Button button) {
        Drawable currentDrawable = button.getBackground();
        Drawable freeDrawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.seat_free);
        Drawable reservedDrawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.seat_reserved);
        View dialogView;
        ImageButton cancelButton;
        if(currentDrawable.getConstantState().equals(freeDrawable.getConstantState())) {
            dialogView = inflater.inflate(R.layout.reservation_free_table_alert, null);
            alertBuilder.setTitle(null);
            alertBuilder.setCancelable(true);
            alertBuilder.setView(dialogView);
            alertDialog = alertBuilder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();
        } else if (currentDrawable.getConstantState().equals(reservedDrawable.getConstantState())) {
            dialogView = inflater.inflate(R.layout.reservation_reserved_table_alert, null);
            alertBuilder.setTitle(null);
            alertBuilder.setCancelable(true);
            alertBuilder.setView(dialogView);
            alertDialog = alertBuilder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();
        } else {
            dialogView = inflater.inflate(R.layout.reservation_busy_table_alert, null);
            alertBuilder.setTitle(null);
            alertBuilder.setCancelable(true);
            alertBuilder.setView(dialogView);
            alertDialog = alertBuilder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();
        }
        cancelButton = dialogView.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
            }
        });
    }
}
