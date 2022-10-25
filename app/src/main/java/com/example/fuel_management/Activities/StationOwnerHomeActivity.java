package com.example.fuel_management.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuel_management.Adaptors.OwnerHomeAdapter;
import com.example.fuel_management.Adaptors.OwnerStationDetailAdapter;
import com.example.fuel_management.Models.FillingStationModel;
import com.example.fuel_management.R;
import com.example.fuel_management.Services.FillingStationService;
import com.example.fuel_management.Session.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class StationOwnerHomeActivity extends AppCompatActivity {

   /* String stationList [] = {"Panadura","Kalutara","Piliyandala"};*/

    //Initialize variables
    private ListView listView;
    private Button btn_add_new;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private List<FillingStationModel> stationList;
    private OwnerHomeAdapter adapter;
    private FillingStationService fillingStationService;
    private SessionManager sessionManager;
    private EditText edt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_station_view_grid);
        overridePendingTransition(1, 1);

        //Assign values
        fillingStationService = new FillingStationService(this);
        stationList = new ArrayList<FillingStationModel>();
        sessionManager = new SessionManager(this);
        edt_search =findViewById(R.id.Edt_Owner_Station_Search);


        //Button for adding new filling station
        btn_add_new = (Button) findViewById(R.id.Btn_Add_New);

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            //   adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
              //do nothing
            }
        });

        //Onclick event of the add new  button
        btn_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {;
                Intent intent=new Intent(StationOwnerHomeActivity.this, FillingStationRegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    //Getting data for the recycle view
    private void initData() {
        fillingStationService.GetAllFillingStationsByOwner(sessionManager.getSessionID(), new FillingStationService.GetAllFillingStationsByOwnerResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(StationOwnerHomeActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<FillingStationModel> stations) {
                 stationList = stations;
                 initRecylerView();

            }
        });
    }

    //setting properties for the recycler view
    private void initRecylerView() {
        recyclerView = findViewById(R.id.RecyclerView_Owner_Home);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OwnerHomeAdapter(stationList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void exitApplicationDialog(Context context) {
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Set the message show for the Alert time
        builder.setMessage(R.string.Want_to_exit);

        // Set Alert Title
        builder.setTitle(R.string.Alert);

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton(R.string.exit_app, (DialogInterface.OnClickListener) (dialog, which) -> {
            //this.finishAffinity();
            finish();

        });

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton(R.string.Cancel, (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    @Override
    public void onBackPressed() {
        exitApplicationDialog(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Menu_Logout) {
            sessionManager.removeSession();
            Intent intent=new Intent(StationOwnerHomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        if (id == R.id.Menu_Exit) {
            finish();
        }
       return  super.onOptionsItemSelected(item);
    }

}
