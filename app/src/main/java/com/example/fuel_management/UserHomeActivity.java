package com.example.fuel_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserHomeActivity extends AppCompatActivity {

    String stationList [] = {"Panadura","Kalutara","piliyandala"};

    //Initialize variables
    private ListView listView;
    private Button btn_logout;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_station_view_grid);
        listView = (ListView)findViewById(R.id.station_view_list);
        StationDetailAdapter stationDetailAdapter = new StationDetailAdapter(getApplicationContext(),stationList);
        listView.setAdapter(stationDetailAdapter);

        btn_logout = (Button) findViewById(R.id.Btn_UserHome_Logout);
        sessionManager = new SessionManager(UserHomeActivity.this);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserHomeActivity.this, "WWWWWW", Toast.LENGTH_SHORT).show();
                sessionManager.removeSession();
                Intent intent=new Intent(UserHomeActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
