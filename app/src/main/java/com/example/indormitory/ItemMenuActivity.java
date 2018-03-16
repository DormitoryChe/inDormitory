package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Ростислав on 13.03.2018.
 */

public class ItemMenuActivity extends BaseActivity {
    private ImageView mBackImageView;

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
        mProfileImageView = findViewById(R.id.toolbar_profile);
        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemMenuActivity.this, LoginActivity.class));
            }
        });
        mShoppingCartImageView = findViewById(R.id.toolbar_shopping_cart);
        mShoppingCartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemMenuActivity.this, ShoppingCartActivity.class));
            }
        });

    }
}
