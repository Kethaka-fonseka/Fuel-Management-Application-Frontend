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
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuel_management.Activities.FillingStationRegisterActivity;
import com.example.fuel_management.Activities.StationOwnerHomeActivity;
import com.example.fuel_management.Models.FillingStationModel;
import com.example.fuel_management.R;
import com.example.fuel_management.Services.FillingStationService;

import java.util.ArrayList;
import java.util.List;

public class OwnerHomeAdapter extends RecyclerView.Adapter<OwnerHomeAdapter.ViewHolder> implements Filterable {

    //Store list of filling stations

    private List<FillingStationModel> stationList;
    private List<FillingStationModel> fullStationList;
    private FillingStationModel fillingStation;
    private FillingStationService fillingStationService;

    public OwnerHomeAdapter(List<FillingStationModel> stationList) {
        this.stationList = stationList;
        this.fullStationList = new ArrayList<>(stationList);
    }

    @NonNull
    @Override
    public OwnerHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.station_detail_owner_card,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerHomeAdapter.ViewHolder holder, int position) {
     String stationName = stationList.get(position).getName();
     String stationLocation = stationList.get(position).getLocation();
     String stationID = stationList.get(position).getId();

        //Create a listener for the card view
     holder.cardView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Toast.makeText(view.getContext(), "Clicked", Toast.LENGTH_SHORT).show();
             Toast.makeText(view.getContext(),stationID.toString(), Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return stationList.size();
    }

    @Override
    public Filter getFilter() {
        return stationFilter;
    }

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

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            stationList.clear();
            stationList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView stationName;
        public TextView stationLocation;
        private Button delete;
        private CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);

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
