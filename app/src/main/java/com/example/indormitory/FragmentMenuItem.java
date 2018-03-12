package com.example.indormitory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Jeckk on 12.03.2018.
 */

public class FragmentMenuItem extends Fragment {
    static final String PAGE_ITEM = "page_item";
    private static String[] items = {"Алкоголь", "Булочки", "М'ясні страви", "Більше алкоголю"};
    private String mItem;
    static FragmentMenuItem newInstance(int position) {
        FragmentMenuItem fragmentMenuItem = new FragmentMenuItem();
        Bundle arguments = new Bundle();
        arguments.putString(PAGE_ITEM, items[position]);
        fragmentMenuItem.setArguments(arguments);
        return fragmentMenuItem;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItem = getArguments().getString(PAGE_ITEM);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_item, null);

        TextView textView = view.findViewById(R.id.fragment_item);
        textView.setText(mItem);
        return view;
    }
}
