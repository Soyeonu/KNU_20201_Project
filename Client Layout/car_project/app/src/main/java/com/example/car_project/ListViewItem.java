package com.example.car_project;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable iconDrawable;
    private String userName;
    private String authority;

    public void setIconDrawable(Drawable icon){
        iconDrawable = icon;
    }

    public void setUserName(String name){
        userName = name;
    }

    public void setAuthority(String authority){
        this.authority = authority;
    }

    public Drawable getIcon(){
        return this.iconDrawable;
    }

    public String getUserName(){
        return this.userName;
    }

    public String getAuthority(){
        return this.authority;
    }
}
