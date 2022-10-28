package com.example.fuel_management.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuel_management.Adaptors.OwnerHomeAdapter;
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

/**
 * The class for display all filling station details with fuel availability
 *
 * @version 1.0
 */
public class UserHomeActivity extends AppCompatActivity {

    //Initialize variables
    ArrayList<FillingStationModel> fillingStationModelArrayList;
    private ListView listView;
    private Button btn_logout;
    private SearchView searchView;
    private SessionManager sessionManager;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private UserHomeDetailAdaptor adapter;
    private FillingStationModel fillingStationModel;
    private  FillingStationService fillingStationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_station_view_grid);

        fillingStationModel = new FillingStationModel();
        fillingStationService = new FillingStationService(UserHomeActivity.this);
        initData();



        sessionManager = new SessionManager(UserHomeActivity.this);

    }

    private void getQueueStationDetails() {
        String stationName  = sessionManager.getQueueFillingStation();
        if(!stationName.equals("NO")){
            fillingStationService.GetFillingStationByName(stationName, new FillingStationService.GetFillingStationByNameResponse() {
                @Override
                public void onError(String message) {
                    Toast.makeText(UserHomeActivity.this, message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(FillingStationModel station) {
                   fillingStationModel = station;
                }
            });
        }

    }

    private void initData() {
        fillingStationService.getFillingStationDetails(new FillingStationService.GetAllFillingStationsByUserResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(UserHomeActivity.this, message.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayList<FillingStationModel> fillingStationModelArrayList1) {
                fillingStationModelArrayList =fillingStationModelArrayList1;

                initRecycleView();
                searchFillingStation(fillingStationModelArrayList,adapter);

            }
        });
    }

    private void initRecycleView() {
        recyclerView = findViewById(R.id.ListView_User_Station_View_List);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UserHomeDetailAdaptor(this,fillingStationModelArrayList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    //search the filling station by name service called
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

    //send data to that filtered to adaptor
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

    //Create menu in the the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_main_menu, menu);
        return true;
    }




    //Control actions of menu in the action bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if( id == R.id.UserMenu_Current_Queue) {
            if(!sessionManager.getQueueFillingStation().equals("NO")){
                Intent intent = new Intent(this, UserEditFormActivity.class);
                intent.putExtra("fillingStatName",fillingStationModel.getName());
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Join to a queue first to view current queue!!", Toast.LENGTH_SHORT).show();
            }

        }
        if(id == R.id.UserMenu_UserProfile){
            Intent intent=new Intent(UserHomeActivity.this, UserProfileActivity.class);
            startActivity(intent);
        }
        if(id == R.id.UserMenu_History) {
            Intent intent=new Intent(UserHomeActivity.this, QueueHistoryActivity.class);
            startActivity(intent);
        }

        if (id == R.id.UserMenu_Logout) {
            sessionManager.removeQueueSession();
            sessionManager.removeSession();
            Intent intent=new Intent(UserHomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        if (id == R.id.UserMenu_Exit) {
            finish();
        }
        return  super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
        if( !sessionManager.getQueueFillingStation().equals("No")){
            getQueueStationDetails();
        }
    }
}
