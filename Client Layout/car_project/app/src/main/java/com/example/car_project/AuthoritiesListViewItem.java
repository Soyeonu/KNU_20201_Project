package com.example.car_project;

import android.widget.Switch;

public class AuthoritiesListViewItem {
    private String name;
    private Boolean flag;
    private String permID;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermID() {return permID;}
    public void setPermID(String permID) {this.permID = permID;}
}
