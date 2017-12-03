package com.moymac.meritapp;

import android.view.View;

import java.util.ArrayList;

/**
 * Simple POJO model for example
 */
public class StepItem {

    private String imageUrl;
    private String title;
    private String difficulty;
    private float rating;
    private double price;
    private String time;
    private String numChildrenSteps;
    private String childrenSteps;
    private double childrenPrice;

    private View.OnClickListener requestBtnClickListener;

    public StepItem() {
    }

    public StepItem(String imageUrl, String title, String difficulty, float rating, double price, String time, String numChildrenSteps, String childrenSteps, double childrenPrice) {

        this.imageUrl = imageUrl;
        this.title = title;
        this.difficulty = difficulty;
        this.rating = rating;
        this.price = price;
        this.time = time;
        this.numChildrenSteps = numChildrenSteps;
        this.childrenSteps = childrenSteps;
        this.childrenPrice = childrenPrice;



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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumChildrenSteps() {
        return numChildrenSteps;
    }

    public void setNumChildrenSteps(String numChildrenSteps) {
        this.numChildrenSteps = numChildrenSteps;
    }

    public String getChildrenSteps() {
        return childrenSteps;
    }

    public void setChildrenSteps(String childrenSteps) {
        this.childrenSteps = childrenSteps;
    }

    public double getChildrenPrice() {
        return childrenPrice;
    }

    public void setChildrenPrice(double childrenPrice) {
        this.childrenPrice = childrenPrice;
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

        StepItem stepItem = (StepItem) o;

        if (Float.compare(stepItem.rating, rating) != 0) return false;
        if (Double.compare(stepItem.price, price) != 0) return false;
        if (Double.compare(stepItem.childrenPrice, childrenPrice) != 0) return false;
        if (imageUrl != null ? !imageUrl.equals(stepItem.imageUrl) : stepItem.imageUrl != null)
            return false;
        if (title != null ? !title.equals(stepItem.title) : stepItem.title != null) return false;
        if (difficulty != null ? !difficulty.equals(stepItem.difficulty) : stepItem.difficulty != null)
            return false;
        if (time != null ? !time.equals(stepItem.time) : stepItem.time != null) return false;
        if (numChildrenSteps != null ? !numChildrenSteps.equals(stepItem.numChildrenSteps) : stepItem.numChildrenSteps != null)
            return false;
        if (childrenSteps != null ? !childrenSteps.equals(stepItem.childrenSteps) : stepItem.childrenSteps != null)
            return false;
        return requestBtnClickListener != null ? requestBtnClickListener.equals(stepItem.requestBtnClickListener) : stepItem.requestBtnClickListener == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = imageUrl != null ? imageUrl.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (difficulty != null ? difficulty.hashCode() : 0);
        result = 31 * result + (rating != +0.0f ? Float.floatToIntBits(rating) : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (numChildrenSteps != null ? numChildrenSteps.hashCode() : 0);
        result = 31 * result + (childrenSteps != null ? childrenSteps.hashCode() : 0);
        temp = Double.doubleToLongBits(childrenPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (requestBtnClickListener != null ? requestBtnClickListener.hashCode() : 0);
        return result;
    }

    /**
     * @return List of elements prepared for tests
     */


}
