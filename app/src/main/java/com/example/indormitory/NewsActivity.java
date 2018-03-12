package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
        setContentView(R.layout.activity_news);
        mNewsRecyclerView = findViewById(R.id.news_recycler_view);
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Fragment fragment = new Fragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.navigation_menu_fragment, fragment).commit();
        for(int i = 0; i < 10; i ++)
            mNewsList.add(new News("News # " + i, "Description", "Image path"));
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
        private News mNews;

        NewsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_news, parent, false));

            mTitleTextView = itemView.findViewById(R.id.news_title);
            mDescriptionTextView = itemView.findViewById(R.id.price);
        }

        void bind(News news) {
            mNews = news;
            mTitleTextView.setText(mNews.getTitle());
            mDescriptionTextView.setText(mNews.getDescription());
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
