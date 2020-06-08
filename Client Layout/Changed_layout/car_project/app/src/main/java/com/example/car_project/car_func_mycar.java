package com.example.car_project;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class car_func_mycar extends Fragment {
    View view;
    public car_func_mycar() {
        // Required empty public constructor
        // 내 차량 관리
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_car_func_mycar, container, false);

        // 아직 리스트 뷰가 완성되지 않았어요..
        ListView listview;
        ListViewAdapter adapter;

        adapter = new ListViewAdapter();

        listview = view.findViewById(R.id.listView);
        listview.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.person), "길동친구", "차량 대여자");
        adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.person), "안뇽뇽", "손님");
        adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.person), "헬로로", "가족");

        return view;
    }
}
