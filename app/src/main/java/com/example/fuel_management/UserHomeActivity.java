package com.example.fuel_management;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class UserHomeActivity extends AppCompatActivity {

    String stationList [] = {"Panadura","Kalutara","piliyandala"};

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_station_view_grid);
        listView = (ListView)findViewById(R.id.station_view_list);
        StationDetailAdapter stationDetailAdapter = new StationDetailAdapter(getApplicationContext(),stationList);
        listView.setAdapter(stationDetailAdapter);
    }
}
