package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.indormitory.models.AllDishes;
import com.example.indormitory.models.Basket;
import com.example.indormitory.models.Dish;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private ImageButton mDishMinusButton;
    private ImageButton mDishPlusButton;
    private Button mDishAddButton;
    private TextView mDishCountTextView;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_menu);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

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
                    startActivity(new Intent(ItemMenuActivity.this, LoginActivity.class));
                } else
                    startActivity(new Intent(ItemMenuActivity.this, ProfileActivity.class));
            }
        });
        mShoppingCartImageButton = findViewById(R.id.toolbar_shopping_cart);
        mShoppingCartImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemMenuActivity.this, ShoppingCartActivity.class));
            }
        });

        uuid_extra = (UUID)getIntent().getSerializableExtra(UUID_EXTRA);
        mDish = AllDishes.get().getDish(uuid_extra);

        mDishNameTextView = findViewById(R.id.food_name);
        mDishPriceTextView = findViewById(R.id.dish_price);
        mDishMinusButton = findViewById(R.id.dish_minus_button);
        mDishPlusButton = findViewById(R.id.dish_plus_button);
        mDishAddButton = findViewById(R.id.dish_add_button);
        mDishCountTextView = findViewById(R.id.dish_count);

        mDishNameTextView.setText(mDish.getTitle());
        mDishPriceTextView.setText(String.valueOf(mDish.getPrice()));
        mDishMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.valueOf(mDishCountTextView.getText().toString()) > 1)
                    mDishCountTextView.setText(String.valueOf(Integer.valueOf(mDishCountTextView.getText().toString()) - 1));
            }
        });
        mDishPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.valueOf(mDishCountTextView.getText().toString()) < 10)
                    mDishCountTextView.setText(String.valueOf(Integer.valueOf(mDishCountTextView.getText().toString()) + 1));
            }
        });
        mDishAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Basket.get(getApplicationContext()).addDish(mDish, Integer.valueOf(mDishCountTextView.getText().toString()));
                Log.e("Basket", mDish.getUuid().toString());
                mDishCountTextView.setText(String.valueOf(1));
            }
        });
    }
}
