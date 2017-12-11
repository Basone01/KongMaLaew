package com.advpro2.basone.kongmalaew;

/**
 * Created by kanazang on 11/3/2017.
 */

public class Data {
    String category=null;
    boolean selected=false;
    public Data(String category, boolean selected) {

        this.category=category;
        this.selected=selected;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }



}
