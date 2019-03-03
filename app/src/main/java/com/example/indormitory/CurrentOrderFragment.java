package com.example.indormitory;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.indormitory.models.Dish;
import com.example.indormitory.models.Orders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CurrentOrderFragment extends Fragment {
    private RecyclerView mChecksRecyclerView;
    private CheckAdapter checkAdapter;
    private LinearLayout wrapper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_current_order, container, false);
        mChecksRecyclerView = view.findViewById(R.id.current_order_check_recycler_view);
        mChecksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        view.findViewById(R.id.current_order_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrapper = getActivity().findViewById(R.id.content_wrapper);
                for(int i = 0; i < wrapper.getChildCount(); i ++) {
                    View child = wrapper.getChildAt(i);
                    child.setVisibility(View.VISIBLE);
                }
                view.setVisibility(View.GONE);
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("currentOrders");
                if(fragment != null)
                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        });
        configureAdapter();
        return view;
    }

    private void configureAdapter() {
        if(checkAdapter == null) {
            checkAdapter = new CheckAdapter(Orders.get().getOrders());
            mChecksRecyclerView.setAdapter(checkAdapter);
        } else {
            checkAdapter.setOrders(Orders.get().getOrders());
            checkAdapter.notifyDataSetChanged();
        }
    }

    private class CheckHolder extends RecyclerView.ViewHolder{
        private RecyclerView recyclerView;
        private DishAdapter adapter;

        CheckHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.current_order_check_items_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        void bind(Map<Dish, Integer> dishes) {
            Log.e("Basket", dishes.toString());
            if(adapter == null) {
                adapter = new DishAdapter(dishes);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.setDishes(dishes);
                adapter.notifyDataSetChanged();
            }
        }

        private class DishHolder extends RecyclerView.ViewHolder {
            private CircleImageView mDishLogo;
            private TextView mDishTitle;
            private TextView mDishCount;
            private TextView mDishOnePrice;
            private TextView mDishTotalPrice;
            private Dish mDish;
            private int mCount;

            public DishHolder(View itemView) {
                super(itemView);
                mDishLogo = itemView.findViewById(R.id.dish_logo);
                mDishTitle = itemView.findViewById(R.id.dish_title);
                mDishCount = itemView.findViewById(R.id.dish_count);
                mDishOnePrice = itemView.findViewById(R.id.one_dish_price);
                mDishTotalPrice = itemView.findViewById(R.id.total_price);
            }

            void bind(Dish dish, int count){
                mDish = dish;
                mCount = count;
                mDishTitle.setText(mDish.getTitle());
                mDishTotalPrice.setText(String.valueOf(mDish.getPrice()*mCount));
                mDishOnePrice.setText(String.valueOf(mDish.getPrice()));
                mDishCount.setText(String.valueOf(count));
                Glide.with(getContext()).load(mDish.getImagePath()).into(mDishLogo);
                mDishLogo.setVisibility(View.VISIBLE);
            }
        }

        private class DishAdapter extends RecyclerView.Adapter<DishHolder> {
            private Map<Dish, Integer> mDishes;

            DishAdapter(Map<Dish, Integer> dishes) {
                Log.e("Basket", "DishAdapter");
                mDishes = dishes;
            }

            @NonNull
            @Override
            public DishHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_food_current_order, parent, false);
                Log.e("Basket", "On create view holder");
                return new DishHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull DishHolder holder, int position) {
                List<Dish> dishes = new ArrayList<>(mDishes.keySet());
                final Dish dish = dishes.get(position);
                holder.bind(dish, mDishes.get(dish));
            }

            @Override
            public int getItemCount() {
                return mDishes.size();
            }

            void setDishes(Map<Dish, Integer> dishes) {
                this.mDishes = dishes;
            }
        }
    }

    private class CheckAdapter extends RecyclerView.Adapter<CheckHolder> {
        private ArrayList<Map<Dish, Integer>> mOrders;

        private CheckAdapter(ArrayList<Map<Dish, Integer>> orders) {
            mOrders = orders;
        }

        @NonNull
        @Override
        public CheckHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_current_order_check, parent, false);
            return new CheckHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CheckHolder holder, int position) {
            holder.bind(mOrders.get(position));
        }

        @Override
        public int getItemCount() {
            return mOrders.size();
        }

        void setOrders(ArrayList<Map<Dish, Integer>> orders) {
            mOrders = orders;
        }
    }
}
