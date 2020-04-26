package com.example.car_project;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import me.relex.circleindicator.CircleIndicator;


/**
 * A simple {@link Fragment} subclass.
 */
public class car_func_home extends Fragment implements ImageButton.OnClickListener{
    View view;
    private ViewPager mViewPager;
    private ViewPagerFragmentAdapter mfragmentAdapter;
    private ImageButton vButton, mButton;

    public car_func_home() {
        // Required empty public constructor
        // 에어컨과 비디오, 오디오를 포함한 클래스
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_car_func_home, container, false);

        mViewPager = view.findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(3);

        mfragmentAdapter = new ViewPagerFragmentAdapter(getChildFragmentManager());

        mfragmentAdapter.addItem(new car_func_aircon());
        mfragmentAdapter.addItem(new Fragment1());

        mViewPager.setAdapter(mfragmentAdapter);

        System.out.println(mfragmentAdapter.getCount());

        // view pager 아래 점
        CircleIndicator indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);

        vButton = view.findViewById(R.id.videoBtn);
        vButton.setOnClickListener(this);
        mButton = view.findViewById(R.id.musicBtn);
        mButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.videoBtn:
                // 비디오 버튼 클릭 시
                System.out.println("video");
                break;
            case R.id.musicBtn:
                // 오디오 버튼 클릭 시
                System.out.println("music");
                break;
        }
    }
}
