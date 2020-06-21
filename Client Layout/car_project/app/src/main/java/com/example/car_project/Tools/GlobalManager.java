package com.example.car_project.Tools;

import android.app.Application;

import Client_Information.User;

public class GlobalManager extends Application {
    //User객체를 Application 코드 전체에서 전역으로 사용하기 위한 클래스
    //모든 Activity에서 GlobalManager를 호출해 User 객체를 참조할 수 있음.
    //사용법
    //1. GlobalManager globalManager = (GlobalManager)getApplicationContext();
    //2. set or get method
    private User user;

    @Override
    public void onCreate() {
        //전역 변수 초기화
        user = new User();
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void setState(User user){
        this.user = user;
    }

    public User getState(){
        return user;
    }

}
