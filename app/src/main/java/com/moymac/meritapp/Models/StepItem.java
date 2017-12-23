package com.moymac.meritapp.Models;

import java.util.List;

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
    private List<String> childrenStepsName;
    private List<String> childrenStepsText;
    private List<Integer> childrenStepsId;
    private List<Integer> childrenStepsType;
    private double childrenPrice;



    public StepItem() {
    }

    public StepItem(String imageUrl, String title, String difficulty, float rating, double price, String time, List<Integer> childrenStepsId, List<String> childrenStepsName, List<String> childrenStepsText, List<Integer> childrenStepsType, double childrenPrice) {

        this.imageUrl = imageUrl;
        this.title = title;
        this.difficulty = difficulty;
        this.rating = rating;
        this.price = price;
        this.time = time;
        this.childrenStepsId = childrenStepsId;
        this.childrenStepsName = childrenStepsName;
        this.childrenStepsText = childrenStepsText;
        this.childrenStepsType = childrenStepsType;
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

    public double getChildrenPrice() {
        return childrenPrice;
    }

    public void setChildrenPrice(double childrenPrice) {
        this.childrenPrice = childrenPrice;
    }

    public List<String> getChildrenStepsName() {
        return childrenStepsName;
    }

    public void setChildrenStepsName(List<String> childrenStepsName) {
        this.childrenStepsName = childrenStepsName;
    }

    public List<String> getChildrenStepsText() {
        return childrenStepsText;
    }

    public void setChildrenStepsText(List<String> childrenStepsText) {
        this.childrenStepsText = childrenStepsText;
    }

    public List<Integer> getChildrenStepsId() {
        return childrenStepsId;
    }

    public void setChildrenStepsId(List<Integer> childrenStepsId) {
        this.childrenStepsId = childrenStepsId;
    }

    public List<Integer> getChildrenStepsType() {
        return childrenStepsType;
    }

    public void setChildrenStepsType(List<Integer> childrenStepsType) {
        this.childrenStepsType = childrenStepsType;
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
        if (childrenStepsName != null ? !childrenStepsName.equals(stepItem.childrenStepsName) : stepItem.childrenStepsName != null)
            return false;
        if (childrenStepsText != null ? !childrenStepsText.equals(stepItem.childrenStepsText) : stepItem.childrenStepsText != null)
            return false;
        if (childrenStepsId != null ? !childrenStepsId.equals(stepItem.childrenStepsId) : stepItem.childrenStepsId != null)
            return false;
        return childrenStepsType != null ? childrenStepsType.equals(stepItem.childrenStepsType) : stepItem.childrenStepsType == null;
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
        result = 31 * result + (childrenStepsName != null ? childrenStepsName.hashCode() : 0);
        result = 31 * result + (childrenStepsText != null ? childrenStepsText.hashCode() : 0);
        result = 31 * result + (childrenStepsId != null ? childrenStepsId.hashCode() : 0);
        result = 31 * result + (childrenStepsType != null ? childrenStepsType.hashCode() : 0);
        temp = Double.doubleToLongBits(childrenPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }


    /**
     * @return List of elements prepared for tests
     */


}
