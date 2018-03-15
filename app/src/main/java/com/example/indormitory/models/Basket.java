package com.example.indormitory.models;

import android.content.Context;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Ростислав on 15.03.2018.
 */

public class Basket {
    private static Basket basket;

    private Map<Dish, Integer> mDishes;
    private double mTotal;
    public static Basket get(Context context) {
        if(basket == null)
            basket = new Basket(context);
        return basket;
    }

    private Basket(Context context) {
        mDishes = new LinkedHashMap<>();
        mTotal = 0;
    }

    public void addDish(Dish dish, int count) {
        for(int i = 0; i < count; i ++)
            updateTotal(dish);

        if(mDishes.containsKey(dish))
            mDishes.put(dish, mDishes.get(dish) +count);
        else
            mDishes.put(dish, count);
    }

    public Map<Dish, Integer> getDishes() {
        return mDishes;
    }

    private void updateTotal(Dish dish) {
        mTotal += dish.getPrice();
    }

    public double getTotal() {
        return mTotal;
    }
}
