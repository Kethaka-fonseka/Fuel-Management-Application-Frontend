package com.example.fuel_management.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.fuel_management.Models.FillingStationModel;
import com.example.fuel_management.Models.FuelModel;
import com.example.fuel_management.R;
import com.example.fuel_management.Services.FillingStationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class FillingStationUpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Initialize variables
    private Button  btn_update,btn_cancel;
    private List<String> fuelNames;
    private List<FuelModel> updatedFuelList;
    private CheckBox checkBox_Diesel;
    private CheckBox checkBox_Petrol;
    private Spinner locationSpinner;
    private FillingStationModel stationModel, updatedStationModel;
    private Intent intent;
    private TextView txt_fuelArrival_timePicker,txt_fuelArrival_datePicker, txt_fuelFinish_timePicker, txt_fuelFinish_datePicker;
    private EditText edt_stationName;
    private AwesomeValidation awesomeValidation;
    private FillingStationService fillingStationService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filling_station_update_form);
        overridePendingTransition(2, 2);

      //Assign values to variables
        edt_stationName = findViewById(R.id.Edt_UpdateStation_Name);
        txt_fuelArrival_timePicker =findViewById(R.id.Txt_FuelArrival_TimePicker);
        txt_fuelArrival_datePicker = findViewById(R.id.Txt_FuelArrival_DatePicker);
        txt_fuelFinish_timePicker = findViewById(R.id.Txt_FuelFinish_TimePicker);
        txt_fuelFinish_datePicker = findViewById(R.id.Txt_FuelFinish_DatePicker);
        locationSpinner = findViewById(R.id.Spinner_UpdateStation_Location);
        checkBox_Petrol = findViewById(R.id.Checkbox_UpdateStation_Petrol);
        checkBox_Diesel = findViewById(R.id.Checkbox_UpdateStation_Diesel);
        btn_update = findViewById(R.id.Btn_UpdateStation_Update);
        btn_cancel = findViewById(R.id.Btn_UpdateStation_Cancle);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        updatedFuelList = new ArrayList<FuelModel>();
        fillingStationService = new FillingStationService(this);
        updatedStationModel = new FillingStationModel();
        fuelNames = new ArrayList<String>();
        intent = getIntent();
        stationModel = (FillingStationModel) intent.getSerializableExtra("station");

        //Add validation for name field
        awesomeValidation.addValidation(this, R.id.Edt_UpdateStation_Name, RegexTemplate.NOT_EMPTY, R.string.invalid_station_name);

        //Setting up the spinner
        ArrayAdapter<String> locationSpinnerAdapter = new ArrayAdapter<String>(
                FillingStationUpdateActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.locations));
        locationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationSpinnerAdapter);

        //On listener for spinner
        locationSpinner.setOnItemSelectedListener(this);


        //Set exiting values for the fields
        edt_stationName.setText(stationModel.getName());
        locationSpinner.setSelection(locationSpinnerAdapter.getPosition(stationModel.getLocation()));
        txt_fuelArrival_datePicker.setText(stationModel.getFuelArrivalTime().toString().substring(0,10));
        txt_fuelArrival_timePicker.setText(stationModel.getFuelArrivalTime().toString().substring(11,16));
        txt_fuelFinish_datePicker.setText(stationModel.getFuelFinishTime().toString().substring(0,10));
        txt_fuelFinish_timePicker.setText(stationModel.getFuelFinishTime().toString().substring(11,16));
        checkBox_Petrol.setChecked(setCheckboxes("Petrol"));
        checkBox_Diesel.setChecked(setCheckboxes("Diesel"));



        //On click event of set date and time for the fuel arrival and finish time
        txt_fuelArrival_datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              handleDate(txt_fuelArrival_datePicker);

            }
        });

        txt_fuelArrival_timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTime(txt_fuelArrival_timePicker);
            }
        });

        txt_fuelFinish_datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDate(txt_fuelFinish_datePicker);
            }
        });

        txt_fuelFinish_timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTime(txt_fuelFinish_timePicker);
            }
        });


        //Update button click event
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUpdatedStation();
                if(!updatedFuelList.isEmpty() && awesomeValidation.validate()){
                    fillingStationService.UpdateFillingStationDetails(updatedStationModel, new FillingStationService.UpdateFillingStationDetailsResponse() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(FillingStationUpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String successMessage) {
                            Toast.makeText(FillingStationUpdateActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(FillingStationUpdateActivity.this, ViewStationActivity.class);
                            intent.putExtra("station",updatedStationModel);
                            startActivity(intent);
                        }
                    });
                }else {
                    Toast.makeText(FillingStationUpdateActivity.this, "You need to check at least one fuel type to continue", Toast.LENGTH_LONG).show();
                }

            }
        });

      //cancel button
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

    }

    // This will  update current station details with the current values in the fields
    private void getUpdatedStation() {
        updatedStationModel.setId(stationModel.getId());
        updatedStationModel.setOwner(stationModel.getOwner());
         updatedStationModel.setName(edt_stationName.getText().toString());
        createNewFuelTypeList();
        updatedStationModel.setFuelTypes(updatedFuelList);
        setUpdatedDates();
    }

    //This method format date and times in string format to localDateTime format
    private void setUpdatedDates() {
        String arrivalDate = txt_fuelArrival_datePicker.getText().toString()+" "+txt_fuelArrival_timePicker.getText().toString();
        String finishDate = txt_fuelFinish_datePicker.getText().toString()+" "+txt_fuelFinish_timePicker.getText().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime arrivalDateTime = LocalDateTime.parse(arrivalDate, formatter);
        LocalDateTime finishDateTime = LocalDateTime.parse(finishDate, formatter);

        updatedStationModel.setFuelArrivalTime(arrivalDateTime);
        updatedStationModel.setFuelFinishTime(finishDateTime);

    }

    //This method save all values in checked check boxes values to updateFuelList array
    private void createNewFuelTypeList() {
        for (String fuelName:fuelNames){
            FuelModel fuel = stationModel.getFuelTypes().stream().filter(fuelModel -> fuelModel.fuelName.equals(fuelName)).findFirst().orElse(null);

            if(fuel!=null){
                updatedFuelList.add(fuel);
            }
            else{
                FuelModel newFuel =new FuelModel();
                newFuel.setFuelName(fuelName);
                newFuel.setStatus("Available");
                updatedFuelList.add(newFuel);
            }
        }
    }

    //This method check check boxes based on previous values
    private boolean setCheckboxes(String name) {
        if(stationModel.getFuelTypes().stream().filter(fuelModel -> fuelModel.fuelName.equals(name)).findFirst().orElse(null)!=null){
            fuelNames.add(name);
          return true;
        }
        return false;
    }

    //This will pop a dialog to select date
    private void handleDate(TextView picker) {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DAY_OF_MONTH);



        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR,year);
                calendar1.set(Calendar.MONTH,month);
                calendar1.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                CharSequence charSequence = DateFormat.format("yyyy-MM-dd",calendar1);
                picker.setText(charSequence);
            }
        },YEAR,MONTH,DATE);
        datePickerDialog.show();
    }

    private void handleTime(TextView picker) {
     Calendar calendar =Calendar.getInstance();

     int HOUR = calendar.get(Calendar.HOUR);
     int MINUTE = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar1.set(Calendar.MINUTE,minute);

                CharSequence charSequence = DateFormat.format("HH:mm",calendar1);
                picker.setText(charSequence);
            }
        },HOUR,MINUTE,true);

        timePickerDialog.show();
    }


    //Checkbox on lick event
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        String fuelName = ((CheckBox) view).getText().toString();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.Checkbox_UpdateStation_Diesel:
                if (checked)
                    fuelNames.add(fuelName);
                else
                    // Remove the fuel
                    fuelNames.remove(fuelName);
                break;
            case R.id.Checkbox_UpdateStation_Petrol:
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


    //Spinner on click event

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updatedStationModel.setLocation(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
   //Do nothing
    }
}
