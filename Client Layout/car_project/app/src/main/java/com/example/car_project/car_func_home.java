package com.example.car_project;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.io.File;

import me.relex.circleindicator.CircleIndicator;


/**
 * A simple {@link Fragment} subclass.
 */
public class car_func_home extends Fragment implements ImageButton.OnClickListener{
    private Activity activity;

    public car_func_home() {
        // Required empty public constructor
        // 에어컨과 비디오, 오디오를 포함한 클래스
    }


//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car_func_home, container, false);

        ViewPager mViewPager = view.findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(3);

        ViewPagerFragmentAdapter mfragmentAdapter = new ViewPagerFragmentAdapter(getChildFragmentManager());

        mfragmentAdapter.addItem(new car_func_aircon());
        mfragmentAdapter.addItem(new Fragment1());

        mViewPager.setAdapter(mfragmentAdapter);

        System.out.println(mfragmentAdapter.getCount());

        // view pager 아래 점
        CircleIndicator indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);

        ImageButton vButton = view.findViewById(R.id.videoBtn);
        vButton.setOnClickListener(this);
        ImageButton mButton = view.findViewById(R.id.musicBtn);
        mButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

//        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.READ_EXTERNAL_STORAGE)) {
//
//            } else {
//                ActivityCompat.requestPermissions(activity,
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
//            }
//        }

        switch(v.getId()){
            case R.id.videoBtn:
                // 비디오 버튼 클릭 시
                System.out.println("video");
                // video 관련 파일 창으로 연결
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Intent.createChooser(intent, "Open"));    // 파일을 열기만함.

                break;
            case R.id.musicBtn:
                // 오디오 버튼 클릭 시
                System.out.println("music");
                // audio 관련 파일 창으로 연결
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Intent.createChooser(intent, "Open"));    // 파일을 열기만함.

                break;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            activity = (Activity) context;
        }

    }
}
