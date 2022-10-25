package com.example.fuel_management.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.fuel_management.Models.FillingStationModel;
import com.example.fuel_management.Models.FuelModel;
import com.example.fuel_management.R;
import com.example.fuel_management.Services.FillingStationService;
import com.example.fuel_management.Session.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class FillingStationRegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Initialize variables
    private CheckBox checkBox_Diesel;
    private CheckBox checkBox_Petrol;
    private Spinner locationSpinner;
    private List<String> fuelNames;
    private FillingStationModel fillingStation;
    private List<FuelModel> fuels;
    private Button btn_register_station, btn_cancel;
    private EditText txt_stationName;
    private SessionManager sessionManager;
    private AwesomeValidation awesomeValidation;
    private FillingStationService fillingStationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filling_station_register_form);

        locationSpinner = (Spinner) findViewById(R.id.Spinner_RegisterStation_Location);
        ArrayAdapter<String> locationSpinnerAdapter = new ArrayAdapter<String>(
                FillingStationRegisterActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.locations));
        locationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationSpinnerAdapter);

        //Assign variables
        sessionManager = new SessionManager(this);
        btn_register_station = findViewById(R.id.Btn__Filling_Station_Register_Register);
        checkBox_Diesel = findViewById(R.id.Checkbox_Diesel);
        checkBox_Petrol = findViewById(R.id.Checkbox_Petrol);
        btn_cancel = findViewById(R.id.Btn__Filling_Station_Register_Cancel);
        txt_stationName = findViewById(R.id.Edit_Add_Station_Name);
        fillingStation= new FillingStationModel();
        fuelNames  = new ArrayList<String>();
        fuels = new ArrayList<FuelModel>();
        fillingStationService = new FillingStationService(this);
        locationSpinner.setOnItemSelectedListener(this);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        //validate  station name field
        awesomeValidation.addValidation(this, R.id.Edit_Add_Station_Name, RegexTemplate.NOT_EMPTY, R.string.invalid_station_name);

        btn_register_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Adding selected fuel Fuel type array list
                createFuelArray();
               if(!fuels.isEmpty() && awesomeValidation.validate()){
                   fillingStation.setName(txt_stationName.getText().toString());
                   fillingStation.setFuelTypes(fuels);
                   fillingStation.setOwner(sessionManager.getSessionID());

                   fillingStationService.AddNewFillingStation(fillingStation, new FillingStationService.AddNewFillingStationResponse() {
                       @Override
                       public void onError(String message) {
                           Toast.makeText(FillingStationRegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                       }

                       @Override
                       public void onResponse(String successMessage) {
                           Toast.makeText(FillingStationRegisterActivity.this, "Filling station Added Successfully", Toast.LENGTH_SHORT).show();
                           Intent intent=new Intent(FillingStationRegisterActivity.this, StationOwnerHomeActivity.class);
                           startActivity(intent);
                       }
                   });
               }
               else{
                   Toast.makeText(FillingStationRegisterActivity.this, "You need to check at least one fuel type to continue", Toast.LENGTH_LONG).show();
               }
            }
        });
  //Cancel the activity

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    //Adding selected fuel Fuel type array list
    public void createFuelArray() {
       for ( String name : fuelNames){
            FuelModel fuel = new FuelModel();
            fuel.setFuelName(name);
            fuel.setStatus("Available");
            fuels.add(fuel);
        }
        Log.d("Ketaka",String.valueOf(fuels.size()));
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        String fuelName = ((CheckBox) view).getText().toString();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.Checkbox_Diesel:
                if (checked)
                    fuelNames.add(fuelName);
                else
                // Remove the fuel
                    fuelNames.remove(fuelName);
                break;
            case R.id.Checkbox_Petrol:
                if (checked)
                    fuelNames.add(fuelName);
                else {
                    fuelNames.remove(fuelName);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      fillingStation.setLocation(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
  //Do Nothing
    }
}
