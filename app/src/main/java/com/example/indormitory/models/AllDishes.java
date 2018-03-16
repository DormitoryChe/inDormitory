package com.example.indormitory.models;

import android.content.Context;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ростислав on 16.03.2018.
 */

public class AllDishes {
    private static AllDishes allDishes;

    private Map<String, List<Dish>> mDishesMap;

    public static AllDishes get(Context context) {
        if(allDishes == null)
            allDishes = new AllDishes(context);
        return allDishes;
    }

    private AllDishes(Context context) {
        mDishesMap = new LinkedHashMap<>();
    }

    public void addDishesByOneMenuItem(String menuItem, List<Dish> dishes) {
        mDishesMap.put(menuItem, dishes);
    }
}
