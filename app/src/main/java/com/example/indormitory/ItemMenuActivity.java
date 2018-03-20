package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.indormitory.models.AllDishes;
import com.example.indormitory.models.Basket;
import com.example.indormitory.models.Dish;


/**
 * Created by Ростислав on 13.03.2018.
 */

public class ItemMenuActivity extends BaseActivity {
    private static final String UUID_EXTRA = "uuid_extra";
    private String dishId;
    private Dish mDish;
    private TextView mDishNameTextView;
    private TextView mDishPriceTextView;
    private TextView mDishCountTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_menu);

        mDishNameTextView = findViewById(R.id.food_name);
        mDishPriceTextView = findViewById(R.id.dish_price);
        mDishCountTextView = findViewById(R.id.dish_count);

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
                    startActivity(new Intent(ItemMenuActivity.this, LoginActivity.class));
                } else
                    startActivity(new Intent(ItemMenuActivity.this, ProfileActivity.class));
            }
        });
        findViewById(R.id.toolbar_shopping_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemMenuActivity.this, ShoppingCartActivity.class));
            }
        });
        findViewById(R.id.dish_minus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.valueOf(mDishCountTextView.getText().toString()) > 1)
                    mDishCountTextView.setText(String.valueOf(Integer.valueOf(mDishCountTextView.getText().toString()) - 1));
            }
        });
        findViewById(R.id.dish_plus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.valueOf(mDishCountTextView.getText().toString()) < 10)
                    mDishCountTextView.setText(String.valueOf(Integer.valueOf(mDishCountTextView.getText().toString()) + 1));
            }
        });
        findViewById(R.id.dish_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Basket.get(getApplicationContext()).addDish(mDish, Integer.valueOf(mDishCountTextView.getText().toString()));
                mDishCountTextView.setText(String.valueOf(1));
            }
        });

        dishId = getIntent().getStringExtra(UUID_EXTRA);
        mDish = AllDishes.get().getDish(dishId);

        mDishNameTextView.setText(mDish.getTitle());
        mDishPriceTextView.setText(String.valueOf(mDish.getPrice()));
    }
}
