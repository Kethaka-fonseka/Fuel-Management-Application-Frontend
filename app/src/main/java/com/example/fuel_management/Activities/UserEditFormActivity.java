package com.example.fuel_management.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fuel_management.Models.QueueModel;
import com.example.fuel_management.Models.TimeFormatDTO;
import com.example.fuel_management.Models.VehicleTypeDTO;
import com.example.fuel_management.R;
import com.example.fuel_management.Services.QueueService;
import com.example.fuel_management.Session.SessionManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * Edit details of the queue that user joined .
 *
 * @version 1.0
 */
public class UserEditFormActivity extends AppCompatActivity {

    //Assign variables
    TextView heading_txt,time_txt,txt_car_count,txt_van_count,txt_bike_count,txt_wheel_count;
    Button btn_joined_to_queue,btn_exit_from_queue,btn_exit_after_queue;
    private SessionManager sessionManager;
    private QueueService queueService;

    private AlertDialog.Builder joinedQueueDialogBuilder;
    private AlertDialog dialogQue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_queue_edit_form);

        //get intent details
        Intent intent = getIntent();
        String statName = intent.getStringExtra("fillingStatName");
        sessionManager = new SessionManager(this);
        String username = sessionManager.getSessionUsername();

        heading_txt =(TextView)findViewById(R.id.Txt_Heading_User_Queue_Edit_Form);
        time_txt =(TextView)findViewById(R.id.Txt_Time_Waiting_In_Queue);

        txt_car_count = (TextView)findViewById(R.id.Txt_Car_Count);
        txt_van_count = (TextView)findViewById(R.id.Txt_Van_Count);
        txt_bike_count = (TextView)findViewById(R.id.Txt_Bike_Count);
        txt_wheel_count = (TextView)findViewById(R.id.Txt_ThreeWheel_Count);

        btn_joined_to_queue = (Button) findViewById(R.id.Btn_Joined_User_Queue_Edit_Form);
        btn_exit_from_queue = (Button) findViewById(R.id.Btn_Exit_Before_User_Queue_Edit_Form);
        btn_exit_after_queue = (Button) findViewById(R.id.Btn_Exit_After_User_Queue_Edit_Form);

        heading_txt.setText(statName);

        // get Vehicle count in filling station
        QueueService queueService = new QueueService(this);
        queueService.getQueueLengthByStation(statName, new QueueService.GetQueueLengthByStationResponse() {
            @Override
            public void onError(String message) {
                txt_car_count.setText(String.valueOf(0));
                txt_van_count.setText(String.valueOf(0));
                txt_bike_count.setText(String.valueOf(0));
                txt_wheel_count.setText(String.valueOf(0));
            }

            @Override
            public void onResponse(List<VehicleTypeDTO> vehicleTypes) {
                if(vehicleTypes.size()>0) {
                    for (int i = 0; i < vehicleTypes.size(); i++) {
                        System.out.println("0");
                        if (vehicleTypes.get(i).getVehicleType().contains("Car")) {
                            System.out.println("1");
                            txt_car_count.setText(String.valueOf(vehicleTypes.get(i).getTotal()));
                        } else if (vehicleTypes.get(i).getVehicleType().contains("Van")) {
                            System.out.println("2");
                            txt_van_count.setText(String.valueOf(vehicleTypes.get(i).getTotal()));
                        } else if (vehicleTypes.get(i).getVehicleType().contains("Bike")) {
                            System.out.println("3");
                            txt_bike_count.setText(String.valueOf(vehicleTypes.get(i).getTotal()));
                        } else if (vehicleTypes.get(i).getVehicleType().contains("Three Wheeler")) {
                            System.out.println("4");
                            txt_wheel_count.setText(String.valueOf(vehicleTypes.get(i).getTotal()));
                        }
                    }
                }
            }
        });

        //a method call time count that queue exist
        queueService.getTimeWaitingAtQueueByStation(statName, new QueueService.GetTimeWaitingAtQueueByStationResponse() {
            @Override
            public void onError(String message) {
                time_txt.setText("* You are the first person in the queue");
            }

            @Override
            public void onResponse(TimeFormatDTO timeFormat) {
                time_txt.setText("* People waiting at the queue Hours"+timeFormat.getHours()+" Minute "+timeFormat.getMinutes()+" Seconds "+timeFormat.getSeconds());
            }
        });

        //Join to the queue buttons call
        btn_joined_to_queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = sessionManager.getQueueSessionStatus();
                if(status.contains("NO")){
                    Intent intent = new Intent(UserEditFormActivity.this,UserJoinQueuePopUpActivity.class);
                    intent.putExtra("fillingStatName",statName);
                    startActivity(intent);
                }else{
                    Toast.makeText(UserEditFormActivity.this, "You are already join to queue", Toast.LENGTH_LONG).show();
                }
            }
        });

        //exit from the queue buttons call
        btn_exit_from_queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitQueue(sessionManager,queueService,"Exit");
            }
        });

        //exit after the queue buttons call
        btn_exit_after_queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitQueue(sessionManager,queueService,"Pumped");
            }
        });
    }

    //method to call exit queue service
    private void exitQueue(SessionManager sessionManager,QueueService queueService, String status) {
        String queueSessionID = sessionManager.getQueueSessionID();
        if (!queueSessionID.contains("NO")) {
            String fillingStation = sessionManager.getQueueFillingStation();
            String sessionStatus = sessionManager.getQueueSessionStatus();
            String vehicleType = sessionManager.getQueueVehicleType();
            String customer = sessionManager.getQueueCustomer();
            String arrivalTime = sessionManager.getQueueArrivalTime();
            String departTime = sessionManager.getQueueDepartTime();

            QueueModel queueModel = new QueueModel();
            queueModel.setId(queueSessionID);
            queueModel.setStatus(status);
            queueModel.setFillingStation(fillingStation);
            queueModel.setCustomer(customer);
            queueModel.setVehicleType(vehicleType);
            queueModel.setArrivalTime(arrivalTime);
            //stackoverflow reference
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
            LocalDateTime now = LocalDateTime.now();
            queueModel.setDeparTime(dtf.format(now));

            if (sessionStatus.contains("NO")) {
                Toast.makeText(UserEditFormActivity.this, "You are not joined to queue", Toast.LENGTH_LONG).show();
            } else {
                exitToQueue(queueService, queueModel, status);
            }
        }
    }

    //get the queue details by id
    private QueueModel getQueueById(QueueService queueService,String queueId){
        QueueModel resQueueModel = null;
        queueService.getQueueByIdService(queueId,new QueueService.QetQueueByIdResponse(){
            @Override
            public void onError(String message) {
                Toast.makeText(UserEditFormActivity.this, message, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onResponse(QueueModel queueModel) {
            }
        });
        return resQueueModel;
    }

    //method to call exit queue service
    private void exitToQueue(QueueService queueService,QueueModel queueModel,String status) {
        queueModel.setStatus(status);
        queueService.updateCustomerToQueueByStation(queueModel,new QueueService.UpdateCustomerToQueueResponse(){
            @Override
            public void onError(String message) {
                Toast.makeText(UserEditFormActivity.this, "You exited from queue, Have a nice Day", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UserEditFormActivity.this,UserHomeActivity.class);
                startActivity(intent);
                sessionManager.removeQueueSession();
            }

            @Override
            public void onResponse(String successMessage) {
                Toast.makeText(UserEditFormActivity.this, "You exited from queue, Have a nice Day", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UserEditFormActivity.this,UserHomeActivity.class);
                startActivity(intent);
                sessionManager.removeQueueSession();
            }
        });
    }

}
