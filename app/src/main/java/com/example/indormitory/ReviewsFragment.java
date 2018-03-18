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
import com.example.indormitory.models.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ростислав on 17.03.2018.
 */

public class ReviewsFragment extends Fragment {
    private RecyclerView mReviewsRecyclerView;
    private List<Review> mReviewsList = new ArrayList<>();
    private ReviewsAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, null);
        mReviewsRecyclerView = view.findViewById(R.id.reviews_recycler_view);
        mReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        for(int i = 0; i < 10; i ++) {
            mReviewsList.add(new Review(" Name #" + i, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n" +
                    "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n" +
                    "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n" +
                    "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\n" +
                    "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n" +
                    "proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
        }
        configureAdapter();
        return view;
    }

    private void configureAdapter() {
        if (mAdapter == null) {
            mAdapter = new ReviewsAdapter(mReviewsList);
            mReviewsRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setReviews(mReviewsList);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class ReviewsHolder extends RecyclerView.ViewHolder {
        private TextView mUserNameTextView;
        private TextView mTitleTextView;
        private Review mReview;

        ReviewsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_review, parent, false));

            mUserNameTextView = itemView.findViewById(R.id.profile_name);
            mTitleTextView = itemView.findViewById(R.id.review_description);

        }

        void bind(Review review) {
            mReview = review;
            mTitleTextView.setText(mReview.getTitle());
            mUserNameTextView.setText(mReview.getUserName());
        }
    }

    private class ReviewsAdapter extends RecyclerView.Adapter<ReviewsHolder> {
        private List<Review> mReviewsList;

        ReviewsAdapter(List<Review> reviewsList) {
            mReviewsList = reviewsList;
        }

        @NonNull
        @Override
        public ReviewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ReviewsHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewsHolder holder, int position) {
            Review review = mReviewsList.get(position);
            holder.bind(review);
        }

        @Override
        public int getItemCount() {
            return mReviewsList.size();
        }

        void setReviews(List<Review> reviewsList) {
            mReviewsList = reviewsList;
        }
    }
}
