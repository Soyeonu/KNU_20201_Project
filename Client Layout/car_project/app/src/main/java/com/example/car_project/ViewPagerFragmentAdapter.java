package com.example.car_project;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {
    int numOfTabs = 0;
    private ArrayList<Fragment> items = new ArrayList<Fragment>();

    public ViewPagerFragmentAdapter(FragmentManager fm){
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        System.out.println(fm);
    }

    public void addItem(Fragment item){
        this.numOfTabs++;
        items.add(item);
    }

    @NonNull
    @Override
    public Fragment getItem(int position){
        return items.get(position);
    }

    @Override
    public int getCount(){
        return items.size();
    }
}
