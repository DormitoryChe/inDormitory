package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.indormitory.models.AllDishes;
import com.example.indormitory.models.Basket;
import com.example.indormitory.models.Dish;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeckk on 12.03.2018.
 */

public class MenuItemFragment extends Fragment {
    static final String PAGE_ITEM = "page_item";
    private static final String UUID_EXTRA = "uuid_extra";
    private static String[] items;
    private String mItem;
    private RecyclerView mMenuRecyclerView;
    private MenuAdapter mAdapter;
    private List<Dish> mDishesList = new ArrayList<>();

    static MenuItemFragment newInstance(int position) {
        MenuItemFragment menuItemFragment = new MenuItemFragment();
        items = AllDishes.get().getAllDishes().keySet().toArray(new String[AllDishes.get().getAllDishes().keySet().size()]);
        Bundle arguments = new Bundle();
        arguments.putString(PAGE_ITEM, items[position]);
        menuItemFragment.setArguments(arguments);
        return menuItemFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItem = getArguments().getString(PAGE_ITEM);
        mDishesList.clear();
        mDishesList = AllDishes.get().getAllDishes().get(mItem);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_item, null);
        TextView textView = view.findViewById(R.id.fragment_item);
        textView.setText(mItem);
        mMenuRecyclerView = view.findViewById(R.id.menu_recycler_view);
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        configureAdapter();
        return view;
    }

    private void configureAdapter() {
        mAdapter = new MenuAdapter(mDishesList);
        mMenuRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private class DishHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private TextView mPriceTextView;
        private ImageView mDishLogoImageView;
        private TextView mDishCountTextView;
        private Dish mDish;

        DishHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_food, parent, false));

            mDishLogoImageView = itemView.findViewById(R.id.dish_logo);
            mTitleTextView = itemView.findViewById(R.id.dish_title);
            mPriceTextView = itemView.findViewById(R.id.dish_price);
            mDishCountTextView = itemView.findViewById(R.id.dish_count);
        }

        void bind(Dish dish) {
            mDish = dish;
            mTitleTextView.setText(mDish.getTitle());
            mPriceTextView.setText(String.valueOf(mDish.getPrice()));

            Glide.with(MenuItemFragment.this).load(mDish.getImagePath()).into(mDishLogoImageView);
            mDishLogoImageView.setVisibility(View.VISIBLE);

            itemView.findViewById(R.id.dish_add_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Basket.get().addDish(mDish, Integer.valueOf(mDishCountTextView.getText().toString()));
                    Toast.makeText(getContext(), R.string.add_button_toast, Toast.LENGTH_SHORT).show();
                    mDishCountTextView.setText(String.valueOf(1));
                }
            });
            itemView.findViewById(R.id.dish_plus_button).setClickable(true);
            itemView.findViewById(R.id.dish_plus_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Basket", "click");
                    Log.e("Basket", String.valueOf(Integer.valueOf(mDishCountTextView.getText().toString())));
                    if(Integer.valueOf(mDishCountTextView.getText().toString()) < 10)
                        mDishCountTextView.setText(String.valueOf(Integer.valueOf(mDishCountTextView.getText().toString()) + 1));
                }
            });
            itemView.findViewById(R.id.dish_minus_button).setClickable(true);
            itemView.findViewById(R.id.dish_minus_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Integer.valueOf(mDishCountTextView.getText().toString()) > 1)
                        mDishCountTextView.setText(String.valueOf(Integer.valueOf(mDishCountTextView.getText().toString()) - 1));
                }
            });
            mDishLogoImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ItemMenuActivity.class);
                    intent.putExtra(UUID_EXTRA, mDish.getId());
                    startActivity(intent);
                }
            });
        }
    }

    private class MenuAdapter extends RecyclerView.Adapter<DishHolder> {
        private List<Dish> mDishesList;

        MenuAdapter(List<Dish> dishesList) {
            mDishesList = dishesList;
        }

        @NonNull
        @Override
        public DishHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new DishHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DishHolder holder, int position) {
            Dish dish = mDishesList.get(position);
            holder.bind(dish);
        }

        @Override
        public int getItemCount() {
            return mDishesList.size();
        }
    }
}