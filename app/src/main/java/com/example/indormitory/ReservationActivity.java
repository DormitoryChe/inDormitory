package com.example.indormitory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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

import com.example.indormitory.models.Table;
import com.example.indormitory.network.LoadData;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


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
    private List<Table> allTables = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        inflater = (this).getLayoutInflater();
        alertBuilder = new AlertDialog.Builder(this);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);

        new WaitFetch(this).execute();
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
                return child != null && child.canScrollVertically(-1);

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

    private void showAlertDialogByButtonState(Button button) {
        Drawable drawable = button.getBackground();
        drawable = drawable.getConstantState().newDrawable().mutate();
        Bitmap currentBitmap = drawableToBitmap(drawable);
        currentBitmap = currentBitmap.copy(currentBitmap.getConfig(), false);
        Bitmap freeBitmap = drawableToBitmap(ContextCompat.getDrawable(getApplicationContext(), R.drawable.seat_free));
        Bitmap reservedBitmap = drawableToBitmap(ContextCompat.getDrawable(getApplicationContext(), R.drawable.seat_reserved));
        View dialogView;

        if(compare(currentBitmap, freeBitmap)) {
            startActivity(new Intent(ReservationActivity.this, ReserveTableActivity.class));
        } else if (compare(currentBitmap, reservedBitmap)) {
            dialogView = inflater.inflate(R.layout.reservation_reserved_table_alert, null);
            alertBuilder.setTitle(null);
            alertBuilder.setCancelable(true);
            alertBuilder.setView(dialogView);
            alertDialog = alertBuilder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();
            ImageButton cancelButton;
            Button reserveButton;
            cancelButton = dialogView.findViewById(R.id.cancel_button);
            reserveButton = dialogView.findViewById(R.id.reserve_button);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.hide();
                }
            });
            reserveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ReservationActivity.this, ReserveTableActivity.class));
                }
            });
        } else {
            dialogView = inflater.inflate(R.layout.reservation_busy_table_alert, null);
            alertBuilder.setTitle(null);
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
        }
    }
    private static boolean compare(Bitmap b1, Bitmap b2) {
        if (b1.getWidth() == b2.getWidth() && b1.getHeight() == b2.getHeight()) {
            int[] pixels1 = new int[b1.getWidth() * b1.getHeight()];
            int[] pixels2 = new int[b2.getWidth() * b2.getHeight()];
            b1.getPixels(pixels1, 0, b1.getWidth(), 0, 0, b1.getWidth(), b1.getHeight());
            b2.getPixels(pixels2, 0, b2.getWidth(), 0, 0, b2.getWidth(), b2.getHeight());
            return Arrays.equals(pixels1, pixels2);
        }
        return false;
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private void configureTable(){
        for(Table table : allTables) {
            int tableId = getResources().getIdentifier("table_" + table.getPosition() + "_button", "id", getPackageName());
            if (table.isFree()) {
                findViewById(tableId).setBackground(getResources().getDrawable(R.drawable.seat_free));
            } else if (table.isReserved()) {
                findViewById(tableId).setBackground(getResources().getDrawable(R.drawable.seat_reserved));
            } else if (table.isBusy()) {
                findViewById(tableId).setBackground(getResources().getDrawable(R.drawable.seat_busy));
            }
        }
    }

    private static class WaitFetch extends AsyncTask<Void, Void, Void> {
        private final WeakReference<ReservationActivity> mFragmentRef;
        WaitFetch(ReservationActivity activity){
            mFragmentRef = new WeakReference<>(activity);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            LoadData.loadTables(mFragmentRef.get().allTables);
            while (mFragmentRef.get().allTables.size() == 0)
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mFragmentRef.get().configureTable();
        }
    }
}
