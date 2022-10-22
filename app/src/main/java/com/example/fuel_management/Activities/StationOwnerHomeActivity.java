package com.example.fuel_management.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fuel_management.Adaptors.OwnerStationDetailAdapter;
import com.example.fuel_management.R;
import com.example.fuel_management.Session.SessionManager;

public class StationOwnerHomeActivity extends AppCompatActivity {

    String stationList [] = {"Panadura","Kalutara","Piliyandala"};

    //Initialize variables
    private ListView listView;
    private Button btn_add_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_station_view_grid);
        listView = (ListView)findViewById(R.id.ListView_Owner_Station_View_List);
        OwnerStationDetailAdapter ownerStationDetailAdapter = new OwnerStationDetailAdapter(getApplicationContext(),stationList);
        listView.setAdapter(ownerStationDetailAdapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(StationOwnerHomeActivity.this, "gfgfg", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StationOwnerHomeActivity.this, FillingStationUpdateActivity.class);
                startActivity(intent);
            }
        });

        btn_add_new = (Button) findViewById(R.id.Btn_Add_New);

        btn_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StationOwnerHomeActivity.this, "Adooo", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(StationOwnerHomeActivity.this, FillingStationRegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
