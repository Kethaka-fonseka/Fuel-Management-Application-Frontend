package com.example.fuel_management.Activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fuel_management.R;

public class FillingStationRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filling_station_register_form);

        Spinner locationSpinner = (Spinner) findViewById(R.id.Spinner_Location);
        ArrayAdapter<String> locationSpinnerAdapter = new ArrayAdapter<String>(
                FillingStationRegisterActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.locations));
        locationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationSpinnerAdapter);
    }
}
