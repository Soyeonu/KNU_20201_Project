package com.example.car_project;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CarSelectLVAdapter extends BaseAdapter {
    private ArrayList<CarSelectListView> cs_list = new ArrayList<>();

    public CarSelectLVAdapter(){

    }

    @Override
    public int getCount(){
        return cs_list.size();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.car_select_listview, parent, false);
        }

        TextView masterId = convertView.findViewById(R.id.tv_masterId);
        TextView ownerName = convertView.findViewById(R.id.tv_ownerName);

        CarSelectListView cs_listviewItem = cs_list.get(position);
        masterId.setText(cs_listviewItem.getMasterId());
        ownerName.setText(cs_listviewItem.getOwenerName());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return cs_list.get(position);
    }

    public void addItem(String mid, String name) {
        CarSelectListView item = new CarSelectListView();

        item.setMasterId(mid);
        item.setOwenerName(name);

        cs_list.add(item);
    }
}
