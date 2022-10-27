package com.example.fuel_management.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuel_management.Activities.QueueHistoryActivity;
import com.example.fuel_management.Activities.StationOwnerHomeActivity;
import com.example.fuel_management.Models.QueueModel;
import com.example.fuel_management.R;
import com.example.fuel_management.Services.QueueService;

import java.util.List;

public class HistoryAdapter  extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<QueueModel> queueHistory;
    private QueueService queueService;
    private Context context;

    public HistoryAdapter(List<QueueModel> queueHistory, Context context) {
        this.queueHistory = queueHistory;
        this.context = context;
        this.queueService  = new QueueService(context);
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.queue_history_card,parent,false);
        return  new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        String stationID = queueHistory.get(position).getId();
        String stationName = queueHistory.get(position).getFillingStation();
        String vehicleType = queueHistory.get(position).getVehicleType();
        String arrivalTime  = queueHistory.get(position).getArrivalTime();
        String departTime  = queueHistory.get(position).getDeparTime();

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queueService.DeleteUserHistoryRecord(stationID,new QueueService.DeleteUserHistoryRecordResponse() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String successMessage) {
                        Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(context, QueueHistoryActivity.class);
                        context.startActivity(intent);
                    }
                });
            }
        });

        holder.setData(stationName, vehicleType, arrivalTime, departTime);


    }

    @Override
    public int getItemCount() {
        return queueHistory.size();
    }

    //This class set the recycler view with the card
    public class ViewHolder extends RecyclerView.ViewHolder {

        //Initialize variables
        public TextView stationName;
        public TextView vehicleType;
        public TextView arrivalTime;
        public TextView departTime;
        public ImageView deleteBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            //Assign variables
            stationName =  itemView.findViewById(R.id.Txt_QueueHisotryCard_StationNameValue);
            vehicleType = itemView.findViewById(R.id.Txt_QueueHisotryCard_VehicleTypeValue);
            arrivalTime = itemView.findViewById(R.id.Txt_QueueHisotryCard_ArrivalTimeValue);
            departTime = itemView.findViewById(R.id.Txt_QueueHisotryCard_DepartTimeValue);
            deleteBtn = itemView.findViewById(R.id.Btn_QueueHisotryCard_Delete);

        }

        public void setData(String name, String vehicle_type, String arrival_time, String depart_time) {
            stationName.setText(name);
            vehicleType.setText(vehicle_type);
            arrivalTime.setText(arrival_time.substring(0,10)+"   "+arrival_time.substring(12,16));
            departTime.setText(depart_time.substring(0,10)+"   "+depart_time.substring(12,16));


        }
    }
}
