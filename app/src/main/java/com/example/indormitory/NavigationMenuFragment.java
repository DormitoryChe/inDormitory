package com.example.indormitory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NavigationMenuFragment extends Fragment {
    private ImageView reservationImageView;
    private ImageView newsImageView;
    private ImageView buyImageView;
    private ImageView profileImageView;
    private TextView reservationTextView;
    private TextView newsTextView;
    private TextView buyTextView;
    private TextView profileTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_menu, container, false);

        LinearLayout reservationLayout = view.findViewById(R.id.linear_layout_reservation);
        LinearLayout newsLayout = view.findViewById(R.id.linear_layout_news);
        LinearLayout buyLayout = view.findViewById(R.id.linear_layout_buy);
        LinearLayout profileLayout = view.findViewById(R.id.linear_layout_profile);

        reservationImageView = view.findViewById(R.id.image_view_reservation);
        newsImageView = view.findViewById(R.id.image_view_news);
        buyImageView = view.findViewById(R.id.image_view_buy);
        profileImageView = view.findViewById(R.id.image_view_profile);

        reservationTextView = view.findViewById(R.id.text_view_reservation);
        newsTextView = view.findViewById(R.id.text_view_news);
        buyTextView = view.findViewById(R.id.text_view_buy);
        profileTextView = view.findViewById(R.id.text_view_profile);

        /*if (getActivity() instanceof HomeActivity)
            configureNavigationMenuForLearn();
        else if (getActivity() instanceof SearchActivity)
            configureNavigationMenuForSearch();
        else if (getActivity() instanceof ArticlesActivity)
            configureNavigationMenuForArticles();
        else if (getActivity() instanceof ProfileActivity)
            configureNavigationMenuForHome();*/

        reservationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configureNavigationMenuForReservation();
                //startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });

        newsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configureNavigationMenuForNews();
                //startActivity(new Intent(getContext(), HomeActivity.class));
            }
        });

        buyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configureNavigationMenuForBuy();
                //startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configureNavigationMenuForProfile();
                //startActivity(new Intent(getContext(), ArticlesActivity.class));
            }
        });

        return view;
    }

    public void configureNavigationMenuForReservation() {
        reservationImageView.setImageResource(R.drawable.reservation_active);
        reservationTextView.setTextColor(getResources().getColor(R.color.colorMenuActive));
        newsImageView.setImageResource(R.drawable.news_not_active);
        newsTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
        buyImageView.setImageResource(R.drawable.food_not_active);
        buyTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
        profileImageView.setImageResource(R.drawable.profile_not_active);
        profileTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
    }

    public void configureNavigationMenuForNews() {
        reservationImageView.setImageResource(R.drawable.reservation_not_active);
        reservationTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
        newsImageView.setImageResource(R.drawable.news_active);
        newsTextView.setTextColor(getResources().getColor(R.color.colorMenuActive));
        buyImageView.setImageResource(R.drawable.food_not_active);
        buyTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
        profileImageView.setImageResource(R.drawable.profile_not_active);
        profileTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
    }
    public void configureNavigationMenuForBuy() {
        reservationImageView.setImageResource(R.drawable.reservation_not_active);
        reservationTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
        newsImageView.setImageResource(R.drawable.news_not_active);
        newsTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
        buyImageView.setImageResource(R.drawable.food_active);
        buyTextView.setTextColor(getResources().getColor(R.color.colorMenuActive));
        profileImageView.setImageResource(R.drawable.profile_not_active);
        profileTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
    }

    public void configureNavigationMenuForProfile() {
        reservationImageView.setImageResource(R.drawable.reservation_not_active);
        reservationTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
        newsImageView.setImageResource(R.drawable.news_not_active);
        newsTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
        buyImageView.setImageResource(R.drawable.food_not_active);
        buyTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
        profileImageView.setImageResource(R.drawable.profile_active);
        profileTextView.setTextColor(getResources().getColor(R.color.colorMenuActive));
    }
}
