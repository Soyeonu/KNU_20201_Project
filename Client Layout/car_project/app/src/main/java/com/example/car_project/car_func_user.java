package com.example.car_project;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import Client_Information.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class car_func_user extends Fragment {
    private User user;

    public car_func_user() {
        // Required empty public constructor
        // 사용자 프로필
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_car_func_user, container, false);
    }

    public void getUserFromServer(){
        //Parent Activity(car_func)로부터 userid 가져옴
        String uid = ((car_func)getActivity()).getUserid();

        //uid를 통해 서버에서 비밀번호를 제외한 정보를 받아옴

        //해당 Fragment의 User Attribute에 저장
    }
}
