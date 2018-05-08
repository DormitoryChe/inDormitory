package com.example.indormitory.network;

import android.service.autofill.RegexValidator;
import android.support.annotation.NonNull;

import com.example.indormitory.models.AllDishes;
import com.example.indormitory.models.AllNews;
import com.example.indormitory.models.Dish;
import com.example.indormitory.models.News;
import com.example.indormitory.models.Table;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LoadData {
    RegexValidator ds;
    public static void loadNews() {
        final AllNews allNews = AllNews.get();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("news").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(DocumentSnapshot documentSnapshot : task.getResult()) {
                        News news = new News(documentSnapshot.get("news_title").toString(), documentSnapshot.get("news_description").toString(),
                                documentSnapshot.get("news_image_path").toString(), documentSnapshot.get("news_date_begin").toString(),
                                documentSnapshot.get("news_date_end").toString());
                        allNews.addNews(news);
                    }
                }
            }
        });
    }
    public static void loadDishes() {
        final AllDishes allDishes = AllDishes.get();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("salads").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        Dish dish = new Dish(documentSnapshot.getId(), documentSnapshot.get("name").toString(), Double.valueOf(documentSnapshot.get("price").toString()),
                                documentSnapshot.get("image_path").toString(), (ArrayList<String>) documentSnapshot.get("ingredients"),
                                documentSnapshot.get("information").toString(), Double.valueOf(documentSnapshot.get("weight").toString()),
                                Double.valueOf(documentSnapshot.get("calories").toString()));
                        if (allDishes.getAllDishes().containsKey(documentSnapshot.get("type").toString())) {
                            allDishes.getAllDishes().get(documentSnapshot.get("type").toString()).add(dish);
                        } else {
                            ArrayList<Dish> dishes = new ArrayList<>();
                            dishes.add(dish);
                            allDishes.getAllDishes().put(documentSnapshot.get("type").toString(), dishes);
                        }
                    }
                }
            }
        });
    }

    public static void loadTables(final List<Table> tables) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tables").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(DocumentSnapshot documentSnapshot : task.getResult()) {
                        Table table = new Table(documentSnapshot.getId(), documentSnapshot.get("state").toString(), Integer.valueOf(documentSnapshot.get("position").toString()),
                                                documentSnapshot.get("reserve_to").toString(), documentSnapshot.get("busy_from").toString(),
                                                documentSnapshot.get("busy_by").toString(), documentSnapshot.get("image_path").toString());
                        tables.add(table);
                    }
                }
            }
        });

    }
}
