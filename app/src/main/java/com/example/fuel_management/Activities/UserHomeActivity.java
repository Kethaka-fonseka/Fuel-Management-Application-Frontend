package com.example.fuel_management.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuel_management.Adaptors.StationDetailAdapter;
import com.example.fuel_management.Adaptors.UserHomeDetailAdaptor;
import com.example.fuel_management.Models.FillingStationModel;
import com.example.fuel_management.R;
import com.example.fuel_management.Session.SessionManager;

import java.util.ArrayList;

public class UserHomeActivity extends AppCompatActivity {

    ArrayList<FillingStationModel> fillingStationModelArrayList;

    //Initialize variables
    private ListView listView;
    private Button btn_logout;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_station_view_grid);
//        listView = (ListView)findViewById(R.id.ListView_User_Station_View_List);
//        StationDetailAdapter stationDetailAdapter = new StationDetailAdapter(getApplicationContext(),stationList);
//        listView.setAdapter(stationDetailAdapter);
//        listView.setClickable(true);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(UserHomeActivity.this, UserEditFormActivity.class);
//                startActivity(intent);
//            }
//        });
        System.out.println("sdsdsdsd");
        RecyclerView recyclerView = findViewById(R.id.ListView_User_Station_View_List);
        fillingStationModelArrayList = FillingStationModel.createContactsList(20);
        UserHomeDetailAdaptor adaptor = new UserHomeDetailAdaptor(this,fillingStationModelArrayList);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptor.setOnItemClickListener(new UserHomeDetailAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView) {

                Intent intent = new Intent(UserHomeActivity.this, UserEditFormActivity.class);
              startActivity(intent);
            }
        });


        btn_logout = (Button) findViewById(R.id.Btn_UserHome_Logout);
        sessionManager = new SessionManager(UserHomeActivity.this);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserHomeActivity.this, "WWWWWW", Toast.LENGTH_SHORT).show();
                sessionManager.removeSession();
                Intent intent=new Intent(UserHomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
