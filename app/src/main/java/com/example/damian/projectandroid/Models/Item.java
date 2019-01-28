package com.example.damian.projectandroid.Models;

public class Item {
    private int _id;
    private String name;
    private Double price;
    private int categoryFK;
    private int itemBanner;

    public Item(){}

    public Item(String name, Double price, int cantegoryFK, int itemBanner) {
        this.name = name;
        this.price = price;
        this.categoryFK = cantegoryFK;
        this.itemBanner = itemBanner;
    }

    public Item(int _id, String name, Double price, int categoryFK, int itemBanner) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.categoryFK = categoryFK;
        this.itemBanner = itemBanner;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getCategoryFK() {
        return categoryFK;
    }

    public void setCategoryFK(int categoryFK) {
        this.categoryFK = categoryFK;
    }

    public int getItemBanner() {
        return itemBanner;
    }

    public void setItemBanner(int itemBanner) {
        this.itemBanner = itemBanner;
    }
}
