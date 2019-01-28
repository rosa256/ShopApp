package com.example.damian.projectandroid.Models;

import android.widget.ImageView;

public class Category {
    private int _id;


    private String title;
    private int imageBanner;

    public Category(){}

    public Category(String title, int imageBanner) {
        this.title = title;
        this.imageBanner = imageBanner;
    }

    public String getCategoryTitle() {
        return title;
    }

    public void setCategoryTitle(String title) {
        this.title = title;
    }

    public int getCategoryImageBanner() {
        return imageBanner;
    }

    public void setCategoryImageBanner(int imageBanner) {
        this.imageBanner = imageBanner;
    }

    public int getCategory_id() {
        return _id;
    }

    public void setCategory_id(int _id) {
        this._id = _id;
    }
}
