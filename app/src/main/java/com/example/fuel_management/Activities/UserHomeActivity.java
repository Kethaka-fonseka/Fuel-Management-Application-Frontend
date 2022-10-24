package com.example.fuel_management.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuel_management.Adaptors.StationDetailAdapter;
import com.example.fuel_management.Adaptors.UserHomeDetailAdaptor;
import com.example.fuel_management.Models.FillingStationModel;
import com.example.fuel_management.R;
import com.example.fuel_management.Services.FillingStationService;
import com.example.fuel_management.Services.UserService;
import com.example.fuel_management.Session.SessionManager;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserHomeActivity extends AppCompatActivity {

    ArrayList<FillingStationModel> fillingStationModelArrayList;

    //Initialize variables
    private ListView listView;
    private Button btn_logout;
    private SearchView searchView;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_station_view_grid);
        FillingStationService fillingStationService = new FillingStationService(UserHomeActivity.this);
        fillingStationService.getFillingStationDetails(new FillingStationService.GetAllFillingStationsByUserResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(UserHomeActivity.this, message.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("message.toString()"+message.toString());
            }

            @Override
            public void onResponse(ArrayList<FillingStationModel> fillingStationModelArrayList) {
                Toast.makeText(UserHomeActivity.this, "Content Successful", Toast.LENGTH_SHORT).show();
                RecyclerView recyclerView = findViewById(R.id.ListView_User_Station_View_List);
                UserHomeDetailAdaptor adaptor = new UserHomeDetailAdaptor(UserHomeActivity.this,fillingStationModelArrayList);
                recyclerView.setAdapter(adaptor);
                recyclerView.setLayoutManager(new LinearLayoutManager(UserHomeActivity.this));
//                adaptor.setOnItemClickListener(new UserHomeDetailAdaptor.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View itemView) {
//                        Intent intent = new Intent(UserHomeActivity.this, UserEditFormActivity.class);
//                        startActivity(intent);
//                    }
//                });
                searchFillingStation(fillingStationModelArrayList,adaptor);

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

    private void searchFillingStation(ArrayList<FillingStationModel> fillingStationModelArrayList,UserHomeDetailAdaptor adaptor) {
        searchView = findViewById(R.id.SearchView_Search_User_Station);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText,fillingStationModelArrayList,adaptor);
                return true;
            }
        });
    }

    private void filterList(String newText,ArrayList<FillingStationModel> fillingStationModelArrayList,UserHomeDetailAdaptor adaptor) {

        List<FillingStationModel>  filteredList = new ArrayList<>();
        for(FillingStationModel fillingStationModel :fillingStationModelArrayList){
            if(fillingStationModel.getName().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(fillingStationModel);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(UserHomeActivity.this,"No data found", Toast.LENGTH_SHORT).show();
        }else{
            adaptor.setFilteredList(filteredList);
        }
    }
}
