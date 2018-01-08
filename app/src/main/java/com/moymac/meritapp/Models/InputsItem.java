package com.moymac.meritapp.Models;

import java.util.Date;

/**
 * Created by moymac on 11/13/17.
 */

public class InputsItem {
    int step;
  //  Date creationTime;
    int content_type;
    String content;

    public InputsItem(int step,int content_type, String content){
        this.step = step;
      //  this.creationTime = creationTime;
        this.content = content;
        this.content_type = content_type;
    }

    public int getContent_type() {
        return content_type;
    }

    public void setContent_type(int content_type) {
        this.content_type = content_type;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

