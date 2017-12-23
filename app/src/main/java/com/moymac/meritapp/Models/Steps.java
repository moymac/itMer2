package com.moymac.meritapp.Models;

import java.util.List;

/**
 * Created by moymac on 11/13/17.
 */

public class Steps {

    int id;
    int parent;
    String name;
    int template;
    int ordinal;
    String text;
    String owner;
    int dataType;

    public Steps(int id,String name,String text, int dataType) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.dataType = dataType;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDataType() {
        return dataType;
    }
    public void setDataType(int dataType){
        this.dataType = dataType;
    }
}

