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
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<>();

    public ListViewAdapter(){

    }

    @Override
    public int getCount(){
        return listViewItemList.size();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.listview_item, parent, false);
            convertView = View.inflate(context, R.layout.listview_item, parent);
        }

        ImageView iconImageView = convertView.findViewById(R.id.listImg);
        TextView userNameView = convertView.findViewById(R.id.listUser);
        TextView authorView = convertView.findViewById(R.id.listAut);

        ListViewItem listViewItem = listViewItemList.get(position);
        iconImageView.setImageDrawable(listViewItem.getIcon());
        userNameView.setText(listViewItem.getUserName());
        authorView.setText(listViewItem.getAuthority());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    public void addItem(Drawable icon, String name, String auth) {
        ListViewItem item = new ListViewItem();

        item.setIconDrawable(icon);
        item.setUserName(name);
        item.setAuthority(auth);

        listViewItemList.add(item);
    }

}
