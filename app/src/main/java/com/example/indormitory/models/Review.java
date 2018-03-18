package com.example.indormitory.models;

/**
 * Created by Jeckk on 17.03.2018.
 */

public class Review {
    private String mUserName;
    private String mTitle;

    public Review(String mUserName, String mTitle) {
        this.mUserName = mUserName;
        this.mTitle = mTitle;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getTitle() {
        return mTitle;
    }


}
