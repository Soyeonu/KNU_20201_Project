package com.example.car_project.Tools;

public class URLManager {
    private static final String url = "http://ec2-13-125-7-131.ap-northeast-2.compute.amazonaws.com:3000/";

    public URLManager(String url){

    }

    public static String getUrl() {
        return url;
    }
}
