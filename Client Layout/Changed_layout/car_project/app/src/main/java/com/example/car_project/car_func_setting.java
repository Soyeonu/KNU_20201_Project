package com.example.car_project;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.car_project.Car_Func_Setting.CustomNoticeDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class car_func_setting extends Fragment {
    private Activity activity;
    static final String[] LIST_MENU = {"계정 관리", "알림 설정", "등록 설정"};
    public car_func_setting() {
        // Required empty public constructor
        // 환경 설정
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_func_setting, container, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, LIST_MENU);

        System.out.println(adapter);

        ListView listview = view.findViewById(R.id.settingListview);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        System.out.println((String)parent.getItemAtPosition(position));
                        System.out.println(position);
                        break;
                    case 1:
                        System.out.println(position);
                        CustomNoticeDialog customDialog = new CustomNoticeDialog(getActivity());
                        customDialog.show();
                        break;
                    case 2:
                        System.out.println(position);
                        System.out.println((String)parent.getItemAtPosition(position));
                        break;
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            activity = (Activity) context;
        }

    }

}
