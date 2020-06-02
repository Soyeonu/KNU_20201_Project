package com.example.car_project;

import android.annotation.TargetApi;
import androidx.fragment.app.Fragment;
import android.os.Build;
import android.os.Bundle;

// androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class car_func_aircon extends Fragment implements Button.OnClickListener{
    View view;
    Button airconB;

    public car_func_aircon() {
        // Required empty public constructor
        // 에어컨 클래스
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_car_func_aircon, container, false);
        airconB = view.findViewById(R.id.airconBtn);
        airconB.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.airconBtn:
                //에어컨 작동 버튼 클릭 시 이벤트 설정
                System.out.println("에어컨 작동하기");
                break;
        }
    }
}
