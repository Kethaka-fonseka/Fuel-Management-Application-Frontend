package com.example.fuel_management.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuel_management.Adaptors.FuelTypeAdapter;
import com.example.fuel_management.Models.FillingStationModel;
import com.example.fuel_management.Models.FuelModel;
import com.example.fuel_management.R;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * SView station details screen interface related activities handle by this class  .
 *
 * @version 1.0
 */

public class ViewStationActivity extends AppCompatActivity {
//Variable initialize
private TextView txt_station_title,txt_stationName, txt_stationLocation, txt_fuelArrivalTime, txt_fuelFinishTime;
private Intent intent;
private Button btn_updateDetail;
private FillingStationModel stationModel;
private DateTimeFormatter dateTimeFormatter;
private RecyclerView recyclerView;
private LinearLayoutManager layoutManager;
private List<FuelModel> fuelList;
private FuelTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_station);
        overridePendingTransition(1, 1);

        //Assign variables
        txt_station_title = findViewById(R.id.Txt_ViewStation_Title);
        txt_stationName = findViewById(R.id.Txt_ViewStation_Name_Value);
        txt_stationLocation = findViewById(R.id.Txt_ViewStation_Location_Value);
        txt_fuelArrivalTime = findViewById(R.id.Txt_ViewStation_ArivalTime_Value);
        txt_fuelFinishTime = findViewById(R.id.Txt_ViewStation_FinishTime_Value);
        btn_updateDetail = findViewById(R.id.Btn_ViewStation_Update);
        dateTimeFormatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        fuelList = new ArrayList<FuelModel>();

        intent = getIntent();
        stationModel = (FillingStationModel) intent.getSerializableExtra("station");
        txt_station_title.setText(stationModel.getName());
        txt_stationName.setText(stationModel.getName());
        txt_stationLocation.setText(stationModel.getLocation());
        txt_fuelArrivalTime.setText(stationModel.getFuelArrivalTime().format(dateTimeFormatter));
        txt_fuelFinishTime.setText(stationModel.getFuelFinishTime().format(dateTimeFormatter));




          initData();
          initRecyclerView();

          //This button directed user to the update filling station page
          btn_updateDetail.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent=new Intent(ViewStationActivity.this, FillingStationUpdateActivity.class);
                  intent.putExtra("station",stationModel);
                  startActivity(intent);
              }
          });


    }

    //Setup recyclerview
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.Txt_ViewStation_RecyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FuelTypeAdapter(fuelList,stationModel,ViewStationActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //Add data to the fuel types recycler view
    private void initData() {
        fuelList.addAll(stationModel.getFuelTypes());
    }

}
