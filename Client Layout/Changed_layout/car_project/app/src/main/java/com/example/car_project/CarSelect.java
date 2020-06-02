package com.example.car_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class CarSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_select);

        final ListView listview;
        final CarSelectLVAdapter adapter = new CarSelectLVAdapter();

        listview = findViewById(R.id.carSelect_listview);
        listview.setAdapter(adapter);

        // adapter에 addItem( masterId, Owner name) 으로 리스트 뷰에 추가할 수 있습니다
        adapter.addItem("hello", "길동");
        adapter.addItem("no", "아니");
        adapter.addItem("bye", "잘가");

        // 리스트뷰 클릭 리스너
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // adapter.getItem(position)으로 listview안의 값을 가져 올 수 있음.
                // 클릭한 리스트뷰 마스터 아이디 출력
                System.out.println(((CarSelectListView)adapter.getItem(position)).getMasterId());

                // Car func 액티비티로 이동
                Intent intent = new Intent(CarSelect.this, car_func.class);
                startActivity(intent);
            }
        });
    }
}
