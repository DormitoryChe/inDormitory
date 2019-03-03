package com.example.indormitory.models;

import java.util.ArrayList;
import java.util.Map;

public class Orders {
    private static Orders orders;

    private ArrayList<Map<Dish, Integer>> mOrders;
    public static Orders get() {
        if(orders == null)
            orders = new Orders();
        return orders;
    }

    private Orders() {
        mOrders = new ArrayList<>();
    }

    public void addOrder(Map<Dish, Integer> order) {
        mOrders.add(order);
    }

    public ArrayList<Map<Dish, Integer>> getOrders() {
        return mOrders;
    }

    public boolean isEmptyOrders() {
        return mOrders.isEmpty();
    }

}
