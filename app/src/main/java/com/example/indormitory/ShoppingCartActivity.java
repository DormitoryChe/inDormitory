package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.indormitory.models.Basket;
import com.example.indormitory.models.Dish;
import com.example.indormitory.models.Orders;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import ua.privatbank.paylibliqpay.ErrorCode;
import ua.privatbank.paylibliqpay.LiqPay;
import ua.privatbank.paylibliqpay.LiqPayCallBack;

/**
 * Created by Ростислав on 14.03.2018.
 */

public class ShoppingCartActivity extends BaseActivity {

    private RecyclerView mMenuRecyclerView;
    private MenuAdapter mAdapter;
    private Map<Dish, Integer> mDishes;
    private TextView totalPrice;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        mDishes = Basket.get().getDishes();
        mMenuRecyclerView = findViewById(R.id.shopping_cart_recycler_view);
        mLinearLayout = findViewById(R.id.scroll_view_wrapper);
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
        findViewById(R.id.submit_shopping_cart_at_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUserLoggedIn()) {
                    //TODO by at time activity
                }
                else {
                    redirectUserToLogin();
                }
            }
        });
        findViewById(R.id.submit_shopping_cart).setOnClickListener(new View.OnClickListener() {
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
                    Orders.get().addOrder(new HashMap<>(Basket.get().getDishes()));
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
                    LiqPay.checkout(getApplicationContext(), map, privateKey, callBack);
                } else {
                    redirectUserToLogin();
                }
            }
        });
        findViewById(R.id.go_to_reservation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShoppingCartActivity.this, ReservationActivity.class));
            }
        });
        totalPrice = findViewById(R.id.total_price);
        totalPrice.setText(String.valueOf(Basket.get().getTotal()));
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        configureAdapter();
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

    private void configureAdapter() {
        if (mAdapter == null) {
            mAdapter = new MenuAdapter(mDishes);
            mMenuRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setDishes(mDishes);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class DishHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private TextView mTotalPriceTextView;
        private TextView mOnePriceTextView;
        private TextView mDishCountTextView;
        private ImageButton mDeleteDishButton;
        private CircleImageView mDishLogoImageView;
        private Dish mDish;
        private int mCount;

        DishHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_food_shopping_cart, parent, false));

            mTitleTextView = itemView.findViewById(R.id.dish_title);
            mTotalPriceTextView = itemView.findViewById(R.id.total_price);
            mOnePriceTextView = itemView.findViewById(R.id.one_dish_price);
            mDishCountTextView = itemView.findViewById(R.id.dish_count);
            mDeleteDishButton = itemView.findViewById(R.id.delete_dish);
            mDishLogoImageView = itemView.findViewById(R.id.dish_logo);
        }

        void bind(Dish dish, int count) {
            mDish = dish;
            mCount = count;
            mTitleTextView.setText(mDish.getTitle());
            mTotalPriceTextView.setText(String.valueOf(mDish.getPrice()*mCount));
            mOnePriceTextView.setText(String.valueOf(mDish.getPrice()));
            mDishCountTextView.setText(String.valueOf(count));
            Glide.with(ShoppingCartActivity.this).load(mDish.getImagePath()).into(mDishLogoImageView);
            mDishLogoImageView.setVisibility(View.VISIBLE);

            itemView.findViewById(R.id.dish_plus_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Integer.valueOf(mDishCountTextView.getText().toString()) < 50) {
                        mDishCountTextView.setText(String.valueOf(Integer.valueOf(mDishCountTextView.getText().toString()) + 1));
                        mTotalPriceTextView.setText(String.valueOf(mDish.getPrice()*Integer.valueOf(mDishCountTextView.getText().toString())));
                        Basket.get().addDish(mDish, 1);
                        totalPrice.setText(String.valueOf(Basket.get().getTotal()));
                    }
                }
            });
            itemView.findViewById(R.id.dish_minus_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Integer.valueOf(mDishCountTextView.getText().toString()) > 1) {
                        mDishCountTextView.setText(String.valueOf(Integer.valueOf(mDishCountTextView.getText().toString()) - 1));
                        mTotalPriceTextView.setText(String.valueOf(mDish.getPrice()*Integer.valueOf(mDishCountTextView.getText().toString())));
                        Basket.get().minusDish(mDish);
                        totalPrice.setText(String.valueOf(Basket.get().getTotal()));
                    }
                }
            });
        }
    }

    private class MenuAdapter extends RecyclerView.Adapter<DishHolder> {
        private Map<Dish, Integer> mDishesMap;

        MenuAdapter(Map<Dish, Integer> dishesMap) {
            mDishesMap = dishesMap;
        }

        @NonNull
        @Override
        public DishHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            return new DishHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull final DishHolder holder, int position) {
            List<Dish> dishes = new ArrayList<>(mDishesMap.keySet());
            final Dish dish = dishes.get(position);
            holder.bind(dish, mDishesMap.get(dish));
            holder.mDeleteDishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDishesMap.remove(dish);
                    Basket.get().deleteDish(dish);
                    notifyItemRemoved(holder.getAdapterPosition());
                    notifyItemRangeChanged(holder.getAdapterPosition(), mDishesMap.size());
                    totalPrice.setText(String.valueOf(Basket.get().getTotal()));
                    getItemCount();
                }
            });
        }

        @Override
        public int getItemCount() {
            if(mDishesMap.size() == 0){
                LayoutInflater inflater = (ShoppingCartActivity.this).getLayoutInflater();
                View view = inflater.inflate(R.layout.empty_shopping_cart, null);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.gravity = Gravity.CENTER;
                view.setLayoutParams(layoutParams);
                mLinearLayout.removeAllViews();
                mLinearLayout.addView(view);
                TextView mGoBackToMenuTextView = view.findViewById(R.id.empty_go_back);
                mGoBackToMenuTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        finish();
                    }
                });
            }
            return mDishesMap.size();
        }

        void setDishes(Map<Dish, Integer> dishesMap) {
            mDishesMap.clear();
            mDishesMap.putAll(dishesMap);
            this.notifyItemRangeInserted(0, mDishesMap.size() - 1);
        }
    }
}
