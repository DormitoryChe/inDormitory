package com.example.indormitory.models;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by Ростислав on 13.03.2018.
 */

public class Dish {
    private String id;
    private String title;
    private double price;
    private String imagePath;
    private ArrayList<String> ingredients;
    private String description;
    private double calories;
    private double weight;
    private Drawable image;

    public Dish(String id, String title, double price, String imagePath, ArrayList<String> ingredients, String description, double weight, double calories) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.imagePath = imagePath;
        this.ingredients = ingredients;
        this.description = description;
        this.calories = calories;
        this.weight = weight;
    }

    public String getId() {
        return id;
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

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public String getDescription() {
        return description;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public double getCalories() {
        return calories;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Dish " +
                "id = " + id +
                ", title = '" + title + '\'' +
                ", price = " + price + '\n';
    }
}
