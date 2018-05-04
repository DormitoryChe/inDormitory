package com.example.indormitory;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.indormitory.models.AllDishes;
import com.example.indormitory.models.Dish;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jeckk on 12.03.2018.
 */

public class MenuActivity extends BaseActivity {
    private View mMenuContainer;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressBar progressBar;
    private AllDishes allDishes = AllDishes.get();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        progressBar = findViewById(R.id.progressBar);
        mViewPager = findViewById(R.id.menu_view_pager);
        mMenuContainer = findViewById(R.id.menu_container);
        mSearchView = findViewById(R.id.search);

        findViewById(R.id.toolbar_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentUser == null) {
                    startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                } else
                    startActivity(new Intent(MenuActivity.this, ProfileActivity.class));
            }
        });
        findViewById(R.id.toolbar_shopping_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, ShoppingCartActivity.class));
            }
        });
        initializeSearch();
        mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setClipToPadding(false);
        if(allDishes.getAllDishes().size() == 0) {
            WaitFetch waitFetch = new WaitFetch(this);
            waitFetch.execute();
        }
    }

    private void fetchDishesFromDatabase() {
        db.collection("salads").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(DocumentSnapshot documentSnapshot : task.getResult()) {
                        Dish dish = new Dish(documentSnapshot.getId(), documentSnapshot.get("name").toString(), Double.valueOf(documentSnapshot.get("price").toString()),
                                            documentSnapshot.get("image_path").toString(), (ArrayList<String>)documentSnapshot.get("ingredients"),
                                            documentSnapshot.get("information").toString(), Double.valueOf(documentSnapshot.get("weight").toString()),
                                            Double.valueOf(documentSnapshot.get("calories").toString()));
                        //downloadDishLogo(dish);
                        if(allDishes.getAllDishes().containsKey(documentSnapshot.get("type").toString())) {
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

    @Override
    protected void onResume() {
        super.onResume();
        mSearchView.setQuery("", false);
        mMenuContainer.requestFocus();
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MenuItemFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return AllDishes.get().getAllDishes().keySet().size();
        }
    }

    private static class WaitFetch extends AsyncTask<Void, Void, Void> {
        private final WeakReference<MenuActivity> mActivityRef;
        WaitFetch(MenuActivity activity){
            mActivityRef = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mActivityRef.get().progressBar.setVisibility(View.VISIBLE);
            mActivityRef.get().progressBar.setIndeterminate(false);
            mActivityRef.get().progressBar.setMax(1000);
            mActivityRef.get().mViewPager.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mActivityRef.get().fetchDishesFromDatabase();
            while (AllDishes.get().getAllDishes().size() == 0)
                try {
                    TimeUnit.MILLISECONDS.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mActivityRef.get().progressBar.setVisibility(View.GONE);
            mActivityRef.get().mViewPager.setVisibility(View.VISIBLE);
            mActivityRef.get().mPagerAdapter.notifyDataSetChanged();

        }
    }
}