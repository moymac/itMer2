package com.moymac.meritapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moymac on 11/13/17.
 */

public class Inputs {
    @SerializedName("step")
    int step;

    @SerializedName("text")
    String text;

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

