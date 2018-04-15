package com.example.indormitory;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.indormitory.models.AllDishes;
import com.example.indormitory.models.Basket;
import com.example.indormitory.models.Dish;


/**
 * Created by Ростислав on 13.03.2018.
 */

public class ItemMenuActivity extends BaseActivity {
    private static final String UUID_EXTRA = "uuid_extra";
    private Dish mDish;
    private TextView mDishCountTextView;
    private LinearLayout mDishIngredientsItemMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_menu);

        TextView mDishNameTextView = findViewById(R.id.food_name);
        TextView mDishPriceTextView = findViewById(R.id.dish_price);
        mDishCountTextView = findViewById(R.id.dish_count);
        ImageView mDishLogoImageView = findViewById(R.id.menu_logo);
        TextView mDishCaloriesTextView = findViewById(R.id.food_calories);
        TextView mDishWeightTextView = findViewById(R.id.food_weight);
        TextView mDishInformationTextView = findViewById(R.id.information);
        mDishIngredientsItemMenu = findViewById(R.id.ingredients);

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

        String dishId = getIntent().getStringExtra(UUID_EXTRA);
        mDish = AllDishes.get().getDish(dishId);

        mDishNameTextView.setText(mDish.getTitle());
        mDishPriceTextView.setText(String.valueOf(mDish.getPrice()));
        mDishWeightTextView.setText(String.valueOf(mDish.getWeight()));
        mDishCaloriesTextView.setText(String.valueOf(mDish.getCalories()));
        Log.e("Basket", mDish.getImagePath());
        Glide.with(ItemMenuActivity.this).load(mDish.getImagePath()).into(mDishLogoImageView);
        mDishLogoImageView.setVisibility(View.VISIBLE);
        mDishInformationTextView.setText(mDish.getDescription());
        addIngredients();
    }

    private void addIngredients() {
        for(String ingredient : mDish.getIngredients()) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 10);
            TextView textView = new TextView(getApplicationContext());
            textView.setText(ingredient);
            textView.setCompoundDrawablePadding(15);
            textView.setTextColor(Color.BLACK);
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.circle, 0, 0, 0);
            textView.setLayoutParams(params);
            mDishIngredientsItemMenu.addView(textView);
        }
    }
}
