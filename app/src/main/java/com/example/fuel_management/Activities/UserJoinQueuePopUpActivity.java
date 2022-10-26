package com.example.fuel_management.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fuel_management.Models.QueueModel;
import com.example.fuel_management.R;
import com.example.fuel_management.Services.QueueService;
import com.example.fuel_management.Session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Pop up window to add user vehicle detail to join the queue
 *
 * @version 1.0
 */
public class UserJoinQueuePopUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Initialize variables
    private AlertDialog.Builder joinedQueueDialogBuilder;
    private AlertDialog dialogQue;
    private String selectedVehicle;
    private Button btn_save,btn_cancel;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_join_queue_popup);

        //assigned variables
        Intent intent = getIntent();
        String statName = intent.getStringExtra("fillingStatName");
        sessionManager = new SessionManager(this);
        String username = sessionManager.getSessionUsername();
        btn_save = (Button) findViewById(R.id.Btn_Save_Join_Queue_Detail);
        btn_cancel = (Button) findViewById(R.id.Btn_Cancel_Join_Queue_Detail);

        Spinner vehicleTypeSpinner = (Spinner) findViewById(R.id.Spinner_User_Join_Queue_Popup);
        ArrayAdapter<String> vehicleTypeSpinnerAdapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.vehicleType));
        vehicleTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleTypeSpinner.setAdapter(vehicleTypeSpinnerAdapter);

        vehicleTypeSpinner.setOnItemSelectedListener(this);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.7),(int)(height*0.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x =0;
        params.y =-20;

        getWindow().setAttributes(params);

        QueueService queueService = new QueueService(UserJoinQueuePopUpActivity.this);

        //save button call for join queue details
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinToQueue(queueService,username,statName,selectedVehicle,"Waiting");
                Intent intent = new Intent(UserJoinQueuePopUpActivity.this,UserEditFormActivity.class);
                intent.putExtra("fillingStatName",statName);
                startActivity(intent);
            }
        });

        //cancel button call for join queue details
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserJoinQueuePopUpActivity.this,UserEditFormActivity.class);
                intent.putExtra("fillingStatName",statName);
                startActivity(intent);
            }
        });

    }

    //method for save queue details
    private void joinToQueue(QueueService queueService, String customer, String fillingStation, String vehicleType, String status) {
        QueueModel queueModel = new QueueModel();
        queueModel.setCustomer(customer);
        queueModel.setFillingStation(fillingStation);
        queueModel.setVehicleType(vehicleType);
        queueModel.setStatus(status);
        queueService.addCustomerToQueueByStation(queueModel, new QueueService.AddCustomerToQueueResponse() {
            @Override
            public void onError(String message) {
               Toast.makeText(UserJoinQueuePopUpActivity.this, message.toString(), Toast.LENGTH_LONG).show();
               System.out.println(message.toString());
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(UserJoinQueuePopUpActivity.this, "You Joined to the Queue", Toast.LENGTH_LONG).show();
                    sessionManager.saveQueueDetails(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedVehicle = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
   //Do nothing
    }
}
