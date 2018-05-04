package com.example.indormitory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.indormitory.models.AllNews;
import com.example.indormitory.models.News;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ростислав on 17.03.2018.
 */

public class NewsFragment extends Fragment {
    private RecyclerView mNewsRecyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private NewsAdapter mAdapter;
    private AllNews allNews;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        progressBar = view.findViewById(R.id.progressBar);
        mNewsRecyclerView = view.findViewById(R.id.news_recycler_view);
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        allNews = AllNews.get();
        if(allNews.getNewsList().size() == 0) {
            WaitFetch waitFetch = new WaitFetch(this);
            waitFetch.execute();
        } else {
            configureAdapter();
        }
        return view;
    }

    private void fetchNewsFromDatabase() {
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
                    configureAdapter();
                }
            }
        });
    }

    private void configureAdapter() {
        if (mAdapter == null) {
            mAdapter = new NewsAdapter(allNews.getNewsList());
            mNewsRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setNews(allNews.getNewsList());
            mAdapter.notifyDataSetChanged();
        }
    }

    private class NewsHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private TextView mDescriptionTextView;
        private ImageView mNewsLogoImageView;
        private ImageButton mMoreDescriptionButton;
        private TextView mNewsDateTextView;
        private News mNews;

        NewsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_news, parent, false));

            mTitleTextView = itemView.findViewById(R.id.news_title);
            mNewsLogoImageView = itemView.findViewById(R.id.news_logo);
            mDescriptionTextView = itemView.findViewById(R.id.news_description);
            mMoreDescriptionButton = itemView.findViewById(R.id.news_more_button);
            mNewsDateTextView = itemView.findViewById(R.id.news_date);
        }

        void bind(News news) {
            mNews = news;
            mTitleTextView.setText(mNews.getTitle());
            Glide.with(NewsFragment.this).load(mNews.getImagePath()).into(mNewsLogoImageView);
            mNewsLogoImageView.setVisibility(View.VISIBLE);

            mDescriptionTextView.setText(mNews.getDescription());
            mNewsDateTextView.setText(mNews.getNewsDateBegin() + " - " + mNews.getNewsDateEnd());
            //Log.e("Basket", "Line counts = " + descriptionTextLineCount);
           // if (count >= mDescriptionTextView.getMaxLines())
                mMoreDescriptionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TransitionManager.beginDelayedTransition( (ViewGroup) itemView.findViewById(R.id.news_list_container));
                        mDescriptionTextView.setMaxLines(Integer.MAX_VALUE);
                        mMoreDescriptionButton.setVisibility(View.GONE);
                    }
                });
           // else
             //   mMoreDescriptionButton.setVisibility(View.GONE);
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

    private static class WaitFetch extends AsyncTask<Void, Void, Void> {
        private final WeakReference<NewsFragment> mFragmentRef;
        WaitFetch(NewsFragment fragment){
            mFragmentRef = new WeakReference<>(fragment);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mFragmentRef.get().progressBar.setVisibility(View.VISIBLE);
            mFragmentRef.get().progressBar.setIndeterminate(false);
            mFragmentRef.get().progressBar.setMax(1000);
            mFragmentRef.get().mNewsRecyclerView.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mFragmentRef.get().fetchNewsFromDatabase();
            while (mFragmentRef.get().allNews.getNewsList().size() == 0)
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mFragmentRef.get().progressBar.setVisibility(View.GONE);
            mFragmentRef.get().mNewsRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
