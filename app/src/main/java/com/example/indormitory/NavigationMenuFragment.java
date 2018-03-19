package com.example.indormitory;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.Log;
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
    private ImageView scanImageView;
    private TextView reservationTextView;
    private TextView newsTextView;
    private TextView buyTextView;
    private TextView scanTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_menu, container, false);

        LinearLayout reservationLayout = view.findViewById(R.id.linear_layout_reservation);
        LinearLayout newsLayout = view.findViewById(R.id.linear_layout_news);
        LinearLayout buyLayout = view.findViewById(R.id.linear_layout_buy);
        LinearLayout scanLayout = view.findViewById(R.id.linear_layout_scan);

        reservationImageView = view.findViewById(R.id.image_view_reservation);
        newsImageView = view.findViewById(R.id.image_view_news);
        buyImageView = view.findViewById(R.id.image_view_buy);
        scanImageView = view.findViewById(R.id.image_view_scan);

        reservationTextView = view.findViewById(R.id.text_view_reservation);
        newsTextView = view.findViewById(R.id.text_view_news);
        buyTextView = view.findViewById(R.id.text_view_buy);
        scanTextView = view.findViewById(R.id.text_view_scan);

        if (getActivity() instanceof MenuActivity || getActivity() instanceof ItemMenuActivity || getActivity() instanceof ShoppingCartActivity)
            configureNavigationMenuForBuy();
        else if (getActivity() instanceof ReservationActivity || getActivity() instanceof ReserveTableActivity)
            configureNavigationMenuForReservation();
        else if (getActivity() instanceof NewsActivity)
            configureNavigationMenuForNews();
        else if (getActivity() instanceof ScanActivity)
            configureNavigationMenuForScan();
        else
            configureNavigationMenuByDefault();

        reservationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO another animation container is null
                Intent intent = new Intent(getActivity(), ReservationActivity.class);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });

        newsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });

        buyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MenuActivity.class);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });

        scanLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScanActivity.class);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity());
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
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
        scanImageView.setImageResource(R.drawable.qr_code_not_active);
        scanTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
    }

    public void configureNavigationMenuForNews() {
        reservationImageView.setImageResource(R.drawable.reservation_not_active);
        reservationTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
        newsImageView.setImageResource(R.drawable.news_active);
        newsTextView.setTextColor(getResources().getColor(R.color.colorMenuActive));
        buyImageView.setImageResource(R.drawable.food_not_active);
        buyTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
        scanImageView.setImageResource(R.drawable.qr_code_not_active);
        scanTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
    }
    public void configureNavigationMenuForBuy() {
        reservationImageView.setImageResource(R.drawable.reservation_not_active);
        reservationTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
        newsImageView.setImageResource(R.drawable.news_not_active);
        newsTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
        buyImageView.setImageResource(R.drawable.food_active);
        buyTextView.setTextColor(getResources().getColor(R.color.colorMenuActive));
        scanImageView.setImageResource(R.drawable.qr_code_not_active);
        scanTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
    }

    public void configureNavigationMenuForScan() {
        reservationImageView.setImageResource(R.drawable.reservation_not_active);
        reservationTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
        newsImageView.setImageResource(R.drawable.news_not_active);
        newsTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
        buyImageView.setImageResource(R.drawable.food_not_active);
        buyTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
        scanImageView.setImageResource(R.drawable.qr_code_active);
        scanTextView.setTextColor(getResources().getColor(R.color.colorMenuActive));
    }

    public void configureNavigationMenuByDefault() {
        reservationImageView.setImageResource(R.drawable.reservation_not_active);
        reservationTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
        newsImageView.setImageResource(R.drawable.news_not_active);
        newsTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
        buyImageView.setImageResource(R.drawable.food_not_active);
        buyTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
        scanImageView.setImageResource(R.drawable.qr_code_not_active);
        scanTextView.setTextColor(getResources().getColor(R.color.colorMenuNotActive));
    }
}
