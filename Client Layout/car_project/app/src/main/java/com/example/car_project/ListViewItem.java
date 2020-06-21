package com.example.car_project;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private String ExpDate;
    private String userName;
    private String RegID;



    public String getUserName() {
        return userName;
    }
    public String getExpDate() {
        return ExpDate;
    }
    public String getRegID() { return RegID; }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setExpDate(String expdate) {
        this.ExpDate = expdate;
    }
    public void setRegID(String regid) { this.RegID = regid; }


}
