package com.example.car_project;

public class CarSelectListView {
    private String masterId;
    private String OwenerName;
    private String regid;
    private String expdate;

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public void setOwenerName(String owenerName) {
        OwenerName = owenerName;
    }
    public void setRegid(String regid) {this.regid = regid;}
    public void setExpdate(String exp) {this.expdate = exp;}


    public String getMasterId() {
        return masterId;
    }

    public String getOwenerName() {
        return OwenerName;
    }
    public String getRegid() {return regid;}
    public String getExpdate() {return expdate;}
}
