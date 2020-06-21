package com.example.car_project;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> userList = new ArrayList<>();

    public ListViewAdapter(){

    }

    @Override
    public int getCount(){
        return userList.size();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        TextView userName = convertView.findViewById(R.id.tv_userName);
        TextView phone = convertView.findViewById(R.id.tv_phone);

        ListViewItem listviewItem = userList.get(position);
        userName.setText(listviewItem.getUserName());
        phone.setText(listviewItem.getExpDate());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    public void addItem(String name, String exp, String regid) {
        ListViewItem item = new ListViewItem();

        item.setUserName(name);
        item.setExpDate(exp);
        item.setRegID(regid);

        userList.add(item);
    }
}
