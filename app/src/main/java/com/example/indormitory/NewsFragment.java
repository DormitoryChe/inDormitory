package com.example.indormitory;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.indormitory.models.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ростислав on 17.03.2018.
 */

public class NewsFragment extends Fragment {
    private RecyclerView mNewsRecyclerView;
    private List<News> mNewsList = new ArrayList<>();
    private NewsAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        mNewsRecyclerView = view.findViewById(R.id.news_recycler_view);
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        for(int i = 0; i < 10; i ++) {
            mNewsList.add(new News("News # " + i, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n" +
                    "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n" +
                    "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n" +
                    "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\n" +
                    "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n" +
                    "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", "Image path"));
        }

        configureAdapter();
        return view;
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
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
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
