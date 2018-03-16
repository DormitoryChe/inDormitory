package com.example.indormitory.models;

import android.content.Context;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Ростислав on 16.03.2018.
 */

public class AllDishes {
    private static AllDishes allDishes;

    private Map<String, List<Dish>> mDishesMap;

    public static AllDishes get() {
        if(allDishes == null)
            allDishes = new AllDishes();
        return allDishes;
    }

    private AllDishes() {
        mDishesMap = new LinkedHashMap<>();
    }

    public Map<String, List<Dish>> getAllDishes() {
        return mDishesMap;
    }
    public void addDishesByOneMenuItem(String menuItem, List<Dish> dishes) {
        mDishesMap.put(menuItem, dishes);
    }

    public List<Dish> getDishesByOneMenuItem(String menuItem) {
        return mDishesMap.get(menuItem);
    }

    public Dish getDish(UUID uuid) {
        for(Map.Entry<String, List<Dish>> entry : mDishesMap.entrySet())
            for(Dish temp : entry.getValue())
                if(temp.getUuid().equals(uuid))
                    return temp;
        return null;
    }


}
