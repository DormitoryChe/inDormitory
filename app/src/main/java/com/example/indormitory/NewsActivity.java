package com.example.indormitory;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.indormitory.models.News;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends BaseActivity {
    private RecyclerView mNewsRecyclerView;
    private NewsAdapter mAdapter;
    private List<News> mNewsList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        DataBindingUtil.setContentView(this, R.layout.activity_news);
        mNewsRecyclerView = findViewById(R.id.news_recycler_view);
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProfileImageView = findViewById(R.id.toolbar_profile);
        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewsActivity.this, LoginActivity.class));
            }
        });
        mShoppingCartImageView = findViewById(R.id.toolbar_shopping_cart);
        mShoppingCartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewsActivity.this, ShoppingCartActivity.class));
            }
        });
        Fragment fragment = new Fragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.navigation_menu_fragment, fragment).commit();
        for(int i = 0; i < 10; i ++)
            // TODO normal string description
            mNewsList.add(new News("News # " + i, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n" +
                    "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n" +
                    "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n" +
                    "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\n" +
                    "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n" +
                    "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", "Image path"));
        configureAdapter();
    }

    private void configureAdapter() {
        if (mAdapter == null) {
            mAdapter = new NewsAdapter(mNewsList);
            mNewsRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setNews(mNewsList);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class NewsHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private TextView mDescriptionTextView;
        private ImageButton mMoreDescriptionButton;
        private News mNews;

        NewsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_news, parent, false));

            mTitleTextView = itemView.findViewById(R.id.news_title);
            mDescriptionTextView = itemView.findViewById(R.id.news_description);
            mMoreDescriptionButton = itemView.findViewById(R.id.news_more_button);
        }

        void bind(News news) {
            mNews = news;
            mTitleTextView.setText(mNews.getTitle());
            mDescriptionTextView.setText(mNews.getDescription());

            mMoreDescriptionButton.setOnClickListener(new View.OnClickListener() {
                boolean isMoreTextVisible = false;

                ObjectAnimator animation = ObjectAnimator.ofInt(
                        mDescriptionTextView,
                        "maxLines",
                        25);
                ObjectAnimator animation1 = ObjectAnimator.ofInt(
                        mDescriptionTextView,
                        "maxLines",
                        3);

                @Override
                public void onClick(View v) {
                   animation.setDuration(1000);
                   animation1.setDuration(1000);
                    Log.e("BaseActivity", "Start delay = " + animation.getStartDelay() );

                    if (isMoreTextVisible) {
                        animation1.start();
                        mMoreDescriptionButton.setImageResource(R.drawable.arrow_down);
                    } else {
                        animation.start();
                        mMoreDescriptionButton.setImageResource(R.drawable.arrow_up);
                    }

                    isMoreTextVisible = !isMoreTextVisible;
                }
            });
        }
    }

    private class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {
        private List<News> mNewsList;

        NewsAdapter(List<News> newsList) {
            mNewsList = newsList;
        }

        @NonNull
        @Override
        public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(NewsActivity.this);
            return new NewsHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
            News news = mNewsList.get(position);
            holder.bind(news);
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }

        void setNews(List<News> newsList) {
            mNewsList = newsList;
        }
    }
}