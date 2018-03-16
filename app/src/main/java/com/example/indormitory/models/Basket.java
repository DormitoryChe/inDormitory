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
        if(mDishes.containsKey(dish)) {
            mDishes.put(dish, mDishes.get(dish) + count);
            updateTotal();
        }
        else {
            mDishes.put(dish, count);
            updateTotal();
        }
    }

    public void minusDish(Dish dish) {
        mDishes.put(dish, mDishes.get(dish) - 1);
        updateTotal();
    }

    public void deleteDish(Dish dish) {
        mDishes.remove(dish);
        updateTotal();
    }

    public Map<Dish, Integer> getDishes() {
        return mDishes;
    }

    public void updateTotal() {
        double newTotal = 0;
        for(Map.Entry<Dish, Integer> entry : mDishes.entrySet()) {
            double temp = entry.getValue()*entry.getKey().getPrice();
            newTotal += temp;
        }
        mTotal = newTotal;
    }

    public double getTotal() {
        return mTotal;
    }
}
