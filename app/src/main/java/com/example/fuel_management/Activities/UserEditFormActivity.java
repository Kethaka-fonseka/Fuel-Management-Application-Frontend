package com.example.fuel_management.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fuel_management.Models.QueueModel;
import com.example.fuel_management.Models.TimeFormatDTO;
import com.example.fuel_management.Models.VehicleTypeDTO;
import com.example.fuel_management.R;
import com.example.fuel_management.Services.QueueService;
import com.example.fuel_management.Session.SessionManager;

import java.util.List;

public class UserEditFormActivity extends AppCompatActivity {

    TextView heading_txt,time_txt;
    Button btn_joined_to_queue,btn_exit_from_queue,btn_exit_after_queue;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_queue_edit_form);
        Intent intent = getIntent();
        String statName = intent.getStringExtra("fillingStatName");
        sessionManager = new SessionManager(this);
        String username = sessionManager.getSessionUsername();

        heading_txt =(TextView)findViewById(R.id.Txt_Heading_User_Queue_Edit_Form);
        time_txt =(TextView)findViewById(R.id.Txt_Time_Waiting_In_Queue);
        btn_joined_to_queue = (Button) findViewById(R.id.Btn_Joined_User_Queue_Edit_Form);
        btn_exit_from_queue = (Button) findViewById(R.id.Btn_Exit_Before_User_Queue_Edit_Form);
        btn_exit_after_queue = (Button) findViewById(R.id.Btn_Exit_After_User_Queue_Edit_Form);

        heading_txt.setText(statName);

        QueueService queueService = new QueueService(this);
        queueService.getQueueLengthByStation(statName, new QueueService.GetQueueLengthByStationResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(UserEditFormActivity.this, message.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<VehicleTypeDTO> vehicleTypes) {
                System.out.println(vehicleTypes.get(0).getVehicleType());

            }
        });

        queueService.getTimeWaitingAtQueueByStation(statName, new QueueService.GetTimeWaitingAtQueueByStationResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(UserEditFormActivity.this, message.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(TimeFormatDTO timeFormat) {
                time_txt.setText("Queue time is Hour "+timeFormat.getHours()+" Minute "+timeFormat.getMinutes()+" Seconds "+timeFormat.getSeconds());
            }
        });

        btn_joined_to_queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinToQueue(queueService,username,statName,"car","Waiting");
            }
        });

        btn_exit_from_queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                exitToQueue(queueService,username,statName,"car","Exit");
            }
        });

        btn_exit_after_queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                exitToQueue(queueService,username,statName,"car","Pumped");
            }
        });
    }

    private void joinToQueue(QueueService queueService,String customer,String fillingStation,String vehicleType,String status) {
        QueueModel queueModel = new QueueModel();
        queueModel.setCustomer(customer);
        queueModel.setFillingStation(fillingStation);
        queueModel.setVehicleType(vehicleType);
        queueModel.setStatus(status);
        queueService.addCustomerToQueueByStation(queueModel, new QueueService.AddCustomerToQueueResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(UserEditFormActivity.this, message.toString(), Toast.LENGTH_LONG).show();
                System.out.println(message.toString());
                Intent intent = new Intent(UserEditFormActivity.this,UserHomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onResponse(String successMessage) {
                Toast.makeText(UserEditFormActivity.this, successMessage.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UserEditFormActivity.this,UserHomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void exitToQueue(QueueService queueService,String customer,String fillingStation,String vehicleType,String status) {

    }
}
