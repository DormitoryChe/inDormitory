package com.example.indormitory.models;

import java.util.List;
import java.util.Map;

/**
 * Created by Ростислав on 20.03.2018.
 */

public class User {
    private String name;
    private String email;
    private String phoneNumber;
    private List<Dish> historyOrders;
    private Map<String, String> currentReservation;
    private List<Dish> currentOrder;
}
