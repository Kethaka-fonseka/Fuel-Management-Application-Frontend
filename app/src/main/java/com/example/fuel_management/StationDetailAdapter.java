package com.example.fuel_management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StationDetailAdapter extends BaseAdapter {
    Context context;
    String listStation[];
    int listImages[];
    LayoutInflater inflater;

    public StationDetailAdapter(Context context,String [] listStation){
        this.context = context;
        this.listStation = listStation;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listStation.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        convertView = inflater.inflate(R.layout.station_detail,null);
        TextView textView = (TextView) convertView.findViewById(R.id.fuel_station_name);
        textView.setText(listStation[i]);
        return convertView;
    }
}
