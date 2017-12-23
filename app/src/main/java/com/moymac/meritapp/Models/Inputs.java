package com.moymac.meritapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moymac on 11/13/17.
 */

public class Inputs {
    @SerializedName("step")
    int step;

    @SerializedName("text")
    String text;

    public int id;
    public int timesIterated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimesIterated() {
        return timesIterated;
    }

    public void setTimesIterated(int timesIterated) {
        this.timesIterated = timesIterated;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}

