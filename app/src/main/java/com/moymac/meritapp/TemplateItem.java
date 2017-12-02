package com.moymac.meritapp;

import android.view.View;

import java.util.ArrayList;

/**
 * Simple POJO model for example
 */
public class TemplateItem {

    private String imageUrl;
    private String title;
    private String author;
    private String description;
    private String difficulty;
    private float rating;
    private float price;
    private String time;

    private View.OnClickListener requestBtnClickListener;

    public TemplateItem() {
    }

    public TemplateItem(String imageUrl, String title, String author, String description, String difficulty, float rating, float price, String time) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.author = author;
        this.description = description;
        this.difficulty = difficulty;
        this.rating = rating;
        this.price = price;
        this.time = time;


    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemplateItem that = (TemplateItem) o;

        if (Float.compare(that.rating, rating) != 0) return false;
        if (Float.compare(that.price, price) != 0) return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (difficulty != null ? !difficulty.equals(that.difficulty) : that.difficulty != null)
            return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        return requestBtnClickListener != null ? requestBtnClickListener.equals(that.requestBtnClickListener) : that.requestBtnClickListener == null;
    }

    @Override
    public int hashCode() {
        int result = imageUrl != null ? imageUrl.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (difficulty != null ? difficulty.hashCode() : 0);
        result = 31 * result + (rating != +0.0f ? Float.floatToIntBits(rating) : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (requestBtnClickListener != null ? requestBtnClickListener.hashCode() : 0);
        return result;
    }

    /**
     * @return List of elements prepared for tests
     */
    public static ArrayList<TemplateItem> getTestingList() {
        ArrayList<TemplateItem> items = new ArrayList<>();
        items.add(new TemplateItem("https://image.flaticon.com/icons/png/128/181/181549.png","title", "author", "description","EASY",1,30, "20 mins"));
        items.add(new TemplateItem("https://upload.wikimedia.org/wikipedia/commons/thumb/1/18/About_icon_%28The_Noun_Project%29.svg/200px-About_icon_%28The_Noun_Project%29.svg.png","title2", "author", "description","HARD",5,30, "15 mins"));
        items.add(new TemplateItem("https://image.flaticon.com/icons/png/128/181/181549.png","title3", "author", "description","MODERATE",5,35, "25 mins"));
        items.add(new TemplateItem("https://image.flaticon.com/icons/png/128/181/181549.png","title4", "author", "description","EASY",5,40, "10 mins"));
        items.add(new TemplateItem("https://image.flaticon.com/icons/png/128/181/181549.png","title5", "author", "description","EASY",2,50, "30 mins"));
        items.add(new TemplateItem("https://image.flaticon.com/icons/png/128/181/181549.png","title6", "author", "description","EASY",5,60, "60 mins"));
        items.add(new TemplateItem("https://image.flaticon.com/icons/png/128/181/181549.png","title7", "author", "description","EASY",4,70, "90 mins"));

        return items;

    }

}
