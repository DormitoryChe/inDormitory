package com.example.indormitory.models;

import java.util.List;
import java.util.UUID;

/**
 * Created by Ростислав on 13.03.2018.
 */

public class Dish {
    private UUID uuid;
    private String title;
    private double price;
    private String imagePath;
    private List<String> ingredients;
    private String methodOfCooking;

    public Dish(String title, double price, String imagePath, List<String> ingredients, String methodOfCooking) {
        uuid = UUID.randomUUID();
        this.title = title;
        this.price = price;
        this.imagePath = imagePath;
        this.ingredients = ingredients;
        this.methodOfCooking = methodOfCooking;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getMethodOfCooking() {
        return methodOfCooking;
    }
}
