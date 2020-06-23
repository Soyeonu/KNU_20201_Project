package com.example.car_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.ArrayList;

public class AuthoritiesLVAdapter extends BaseAdapter {
    private ArrayList<AuthoritiesListViewItem> list = new ArrayList<>();
    private Switch autho;

    public AuthoritiesLVAdapter() {

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.authorities_listview_item, parent, false);
        }

        autho = convertView.findViewById(R.id.authority);

        final AuthoritiesListViewItem listItem = list.get(position);
        autho.setText(listItem.getName());
        autho.setChecked(listItem.getFlag());

        autho.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listItem.setFlag(isChecked);
            }
        });

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(String permID, String name, boolean bool) {
        AuthoritiesListViewItem item = new AuthoritiesListViewItem();

        item.setPermID(permID);
        item.setName(name);
        item.setFlag(bool);
        list.add(item);
    }
}
