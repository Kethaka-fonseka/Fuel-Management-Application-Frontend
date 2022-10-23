package com.example.fuel_management.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuel_management.Activities.UserEditFormActivity;
import com.example.fuel_management.Activities.UserHomeActivity;
import com.example.fuel_management.Models.FillingStationModel;
import com.example.fuel_management.R;
import java.util.List;

public class UserHomeDetailAdaptor extends RecyclerView.Adapter<UserHomeDetailAdaptor.ViewHolder> {

    private List<FillingStationModel> fillingStationModelList;
    private Context context;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView);
    }

    // Define listener member variable
    public OnItemClickListener listener;

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Pass in the contact array into the constructor
    public UserHomeDetailAdaptor(Context context,List<FillingStationModel> fillingStationModelList) {
        this.context=context;
        this.fillingStationModelList = fillingStationModelList;
    }

    public void setFilteredList(List<FillingStationModel> filteredList){
        this.fillingStationModelList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.station_detail_user_card, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserHomeDetailAdaptor.ViewHolder holder, int position) {
        // Get the data model based on position
        FillingStationModel fillingStationModel = fillingStationModelList.get(position);
        holder.stationName.setText(fillingStationModel.getName());
        holder.locationName.setText(fillingStationModel.getOwner());
    }

    @Override
    public int getItemCount() {
        return fillingStationModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView stationName,fuelType,locationName;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(@NonNull View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            stationName = (TextView) itemView.findViewById(R.id.Txt_User_Card_Station_Name);
            locationName = (TextView) itemView.findViewById(R.id.Txt_User_Card_Location);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    listener.onItemClick(itemView);
                }
            });
        }
    }
}