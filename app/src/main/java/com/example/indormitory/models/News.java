package com.example.indormitory.models;

public class News {
    private String title;
    private String description;
    private String imagePath;
    private String newsDateBegin;
    private String newsDateEnd;

    public News(String title, String description, String imagePath, String newsDateBegin, String newsDateEnd) {
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.newsDateBegin = newsDateBegin;
        this.newsDateEnd = newsDateEnd;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getNewsDateBegin() {
        return newsDateBegin;
    }

    public String getNewsDateEnd() {
        return newsDateEnd;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
