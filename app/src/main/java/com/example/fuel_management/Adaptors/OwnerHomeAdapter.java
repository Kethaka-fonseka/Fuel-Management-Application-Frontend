package com.example.fuel_management.Adaptors;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuel_management.Activities.StationOwnerHomeActivity;
import com.example.fuel_management.Models.FillingStationModel;
import com.example.fuel_management.Models.FuelModel;
import com.example.fuel_management.R;
import com.example.fuel_management.Services.FillingStationService;
import com.example.fuel_management.Activities.ViewStationActivity;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for the recycler view of the StationOwner interface .
 *
 * @version 1.0
 */
public class OwnerHomeAdapter extends RecyclerView.Adapter<OwnerHomeAdapter.ViewHolder> implements Filterable {

    //Store list of filling stations

    //Initialize variables
    private List<FillingStationModel> stationList;
    private List<FillingStationModel> fullStationList;
    private FillingStationModel fillingStation;
    private FillingStationService fillingStationService;

    public OwnerHomeAdapter(List<FillingStationModel> stationList) {
        this.stationList = stationList;
        this.fullStationList = new ArrayList<>(stationList);
    }

    //Set Card view to set Recycler view to display items
    @NonNull
    @Override
    public OwnerHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.station_detail_owner_card,parent,false);
        return  new ViewHolder(view);
    }


    //Bind values to the properties in the card view that display items in the recycler view
    @Override
    public void onBindViewHolder(@NonNull OwnerHomeAdapter.ViewHolder holder, int position) {
     String stationName = stationList.get(position).getName();
     String stationLocation = stationList.get(position).getLocation();
     String stationID = stationList.get(position).getId();
     String owner = stationList.get(position).getOwner();
     LocalDateTime arrivalTime = stationList.get(position).getFuelArrivalTime();
     LocalDateTime finishTime = stationList.get(position).getFuelFinishTime();

     List<FuelModel> fuelTypes = stationList.get(position).getFuelTypes();

     //Create FillingStation Object
        FillingStationModel fillingStationModel =new FillingStationModel();
        fillingStationModel.setName(stationName);
        fillingStationModel.setOwner(owner);
        fillingStationModel.setId(stationID);
        fillingStationModel.setLocation(stationLocation);
        fillingStationModel.setFuelTypes(fuelTypes);
        fillingStationModel.setFuelArrivalTime(arrivalTime);
        fillingStationModel.setFuelFinishTime(finishTime);
        //Create a listener for the card view
     holder.cardView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent=new Intent(view.getContext(), ViewStationActivity.class);
             intent.putExtra("station",fillingStationModel);
             view.getContext().startActivity(intent);
         }
     });
     //Create a listener for the delete button

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open a dialog box
                viewDialog(view.getContext(),stationID);


            }
        });

     //Set data to the card view
     holder.setData(stationName,stationLocation);
    }

    private void viewDialog(Context context, String Id) {
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Set the message show for the Alert time
        builder.setMessage(R.string.Message);

        // Set Alert Title
        builder.setTitle(R.string.Alert);

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            fillingStationService = new FillingStationService(context);
            fillingStationService.DeleteFillingStation(Id, new FillingStationService.DeleteFillingStationResponse() {
                @Override
                public void onError(String message) {
                    Toast.makeText(context, "Error Happend", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String successMessage) {
                    Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context, StationOwnerHomeActivity.class);
                    context.startActivity(intent);
                }
            });

        });

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }

    //Get count of the items
    @Override
    public int getItemCount() {
        return stationList.size();
    }

    //Get filtered the items
    @Override
    public Filter getFilter() {
        return stationFilter;
    }

    //Doing filter process based on the user search
    private Filter stationFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           List<FillingStationModel> filteredList = new ArrayList<>();
           if(constraint == null || constraint.length() == 0){
               filteredList.addAll(fullStationList);
           }else{
               String filterPattern = constraint.toString().toLowerCase().trim();
               for (FillingStationModel station: fullStationList){
                   if(station.getName().toLowerCase().contains(filterPattern)){
                       filteredList.add(station);
                   }
               }

           }
           FilterResults results = new FilterResults();
           results.values = filteredList;
           return results;
        }

        //Set filtered List of the station
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            stationList.clear();
            stationList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    //This class set the recycler view with the card
    public class ViewHolder extends RecyclerView.ViewHolder {

        //Initialize variables
        public TextView stationName;
        public TextView stationLocation;
        private Button delete;
        private CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            //Assign variables
            stationName =  itemView.findViewById(R.id.Txt_Owner_Card_Station_Name);
            stationLocation = itemView.findViewById(R.id.Txt_Owner_Card_Location);
            delete = itemView.findViewById(R.id.Btn_Owner_Card_Delete_Station);
            cardView = itemView.findViewById(R.id.Card_OwnerHomeScreen_Recycleview);
        }

        public void setData(String name, String location) {
            stationName.setText(name);
            stationLocation.setText(location);

        }
    }

}
