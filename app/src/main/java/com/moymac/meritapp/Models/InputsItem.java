package com.moymac.meritapp.Models;

import java.util.Date;

/**
 * Created by moymac on 11/13/17.
 */

public class InputsItem {
    int step;
  //  Date creationTime;

    Object content;

    public InputsItem(int step,Object content){
        this.step = step;
      //  this.creationTime = creationTime;
        this.content = content;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}

