package com.example.car_project;

public class CarSelectListView {
    private String masterId;
    private String OwenerName;

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public void setOwenerName(String owenerName) {
        OwenerName = owenerName;
    }


    public String getMasterId() {
        return masterId;
    }

    public String getOwenerName() {
        return OwenerName;
    }
}
