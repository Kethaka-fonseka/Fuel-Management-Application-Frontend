package com.example.fuel_management.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fuel_management.R;

public class OwnerStationDetailAdapter extends BaseAdapter {
    Context context;
    String listStation[];
    int listImages[];
    LayoutInflater inflater;

    public OwnerStationDetailAdapter(Context context,String [] listStation){
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
        convertView = inflater.inflate(R.layout.station_detail_owner_card,null);
        TextView textView = (TextView) convertView.findViewById(R.id.Txt_Owner_Card_Station_Name);
        textView.setText(listStation[i]);
        return convertView;
    }
}
