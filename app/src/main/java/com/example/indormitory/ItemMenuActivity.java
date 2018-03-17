package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.indormitory.models.AllDishes;
import com.example.indormitory.models.Dish;

import java.util.UUID;

/**
 * Created by Ростислав on 13.03.2018.
 */

public class ItemMenuActivity extends BaseActivity {
    private static final String UUID_EXTRA = "uuid_extra";
    private ImageView mBackImageView;
    private UUID uuid_extra;
    private Dish mDish;
    private TextView mDishNameTextView;
    private TextView mDishPriceTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_menu);

        mBackImageView = findViewById(R.id.toolbar_back);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        mProfileImageView = findViewById(R.id.toolbar_profile);
//        mProfileImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ItemMenuActivity.this, LoginActivity.class));
//            }
//        });
//        mShoppingCartImageView = findViewById(R.id.toolbar_shopping_cart);
//        mShoppingCartImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ItemMenuActivity.this, ShoppingCartActivity.class));
//            }
//        });

        uuid_extra = (UUID)getIntent().getSerializableExtra(UUID_EXTRA);
        mDish = AllDishes.get().getDish(uuid_extra);

        mDishNameTextView = findViewById(R.id.food_name);
        mDishPriceTextView = findViewById(R.id.dish_price);

        mDishNameTextView.setText(mDish.getTitle());
        mDishPriceTextView.setText(String.valueOf(mDish.getPrice()));
    }
}
