package com.example.fuel_management.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuel_management.Activities.FillingStationRegisterActivity;
import com.example.fuel_management.Activities.StationOwnerHomeActivity;
import com.example.fuel_management.Activities.UserEditFormActivity;
import com.example.fuel_management.Activities.UserHomeActivity;
import com.example.fuel_management.Models.FillingStationModel;
import com.example.fuel_management.R;
import com.example.fuel_management.Services.FillingStationService;
import com.example.fuel_management.Session.SessionManager;

import java.util.List;

public class UserHomeDetailAdaptor extends RecyclerView.Adapter<UserHomeDetailAdaptor.ViewHolder> {

    private List<FillingStationModel> fillingStationModelList;
    private FillingStationService fillingStationService;
    private Context context;
    private SessionManager sessionManager;

    public UserHomeDetailAdaptor(Context context,List<FillingStationModel> fillingStationModelList,SessionManager sessionManager) {
        this.context=context;
        this.fillingStationModelList = fillingStationModelList;
        this.sessionManager=sessionManager;
    }

//    // Define the listener interface
//    public interface OnItemClickListener {
//        void onItemClick(View itemView);
//    }
//
//    // Define listener member variable
//    public OnItemClickListener listener;
//
//    // Define the method that allows the parent activity or fragment to define the listener
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }

    public void setFilteredList(List<FillingStationModel> filteredList){
        this.fillingStationModelList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.station_detail_user_card,parent,false);
        return  new UserHomeDetailAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHomeDetailAdaptor.ViewHolder holder, int position) {
        // Get the data model based on position
        FillingStationModel fillingStationModel = fillingStationModelList.get(position);
        System.out.println("fillingStationModel==>"+fillingStationModel.getFuelTypes().get(0).getFuelName());
        if(fillingStationModel.getFuelTypes().size()>0){
            for(int i =0 ; i<fillingStationModel.getFuelTypes().size();i++){
                System.out.println("dsff");
                if(fillingStationModel.getFuelTypes().get(i).getFuelName().contains("Pet")){
                    System.out.println("dsff");
                    holder.petrolStatus.setText("Petrol "+fillingStationModel.getFuelTypes().get(i).getStatus());
                }else if (fillingStationModel.getFuelTypes().get(i).getFuelName().contains("Di")){
                    System.out.println("dfgfgf");
                    holder.dieselStatus.setText("Diesel "+fillingStationModel.getFuelTypes().get(i).getStatus());
                }
            }
        }
        holder.stationName.setText(fillingStationModel.getName());
        holder.locationName.setText(fillingStationModel.getLocation());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, UserEditFormActivity.class);
                intent.putExtra("fillingStatName",fillingStationModel.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fillingStationModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView stationName,locationName,petrolStatus,dieselStatus;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.CardView_Station_Detail_User_card);
            stationName = (TextView) itemView.findViewById(R.id.Txt_User_Card_Station_Name);
            locationName = (TextView) itemView.findViewById(R.id.Txt_User_Card_Location);
            petrolStatus = (TextView) itemView.findViewById(R.id.Txt_User_Card_Fuel_Type_Petrol);
            dieselStatus = (TextView) itemView.findViewById(R.id.Txt_User_Card_Fuel_Type_Diesel);
        }
    }
}