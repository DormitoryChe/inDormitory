package com.example.indormitory.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ростислав on 16.03.2018.
 */

public class AllNews {
    private static AllNews allNews;

    private List<News> newsList;

    public static AllNews get() {
        if(allNews == null)
            allNews =  new AllNews();
        return allNews;
    }

    private AllNews() {
        newsList = new ArrayList<>();
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public News getNews(String title) {
        for(News news : newsList)
            if(news.getTitle().equals(title))
                return news;
        return null;
    }
}
