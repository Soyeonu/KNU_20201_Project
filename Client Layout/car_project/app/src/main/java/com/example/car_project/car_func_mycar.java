package com.example.car_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
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

        final ListView listview;
        final ListViewAdapter adapter = new ListViewAdapter();

        listview = view.findViewById(R.id.user_listview);
        listview.setAdapter(adapter);

        // adapter에 addItem( masterId, Owner name) 으로 리스트 뷰에 추가할 수 있습니다
        adapter.addItem("길동", "01012341234");
        adapter.addItem("콩순", "01023452345");
        adapter.addItem("박박", "01034563456");

        // 리스트뷰 클릭 리스너
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AuthoritiesDialog dialog = new AuthoritiesDialog(getContext());
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = 800;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.show();
                Window window = dialog.getWindow();
                window.setAttributes(lp);
            }
        });
        return view;
    }
}
