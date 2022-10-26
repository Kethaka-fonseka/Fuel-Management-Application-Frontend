package com.example.fuel_management.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuel_management.Activities.ViewStationActivity;
import com.example.fuel_management.Models.FillingStationModel;
import com.example.fuel_management.Models.FuelModel;
import com.example.fuel_management.R;
import com.example.fuel_management.Services.FillingStationService;

import java.util.ArrayList;
import java.util.List;
/**
 * Adapter class for the recycler view of the fuel types in the viewFuelStation interface .
 *
 * @version 1.0
 */

public class FuelTypeAdapter extends RecyclerView.Adapter<FuelTypeAdapter.ViewHolder>{

    //Initialize variables
    private List<FuelModel> fuelTypes;
    private FillingStationModel fillingStation;
    private FillingStationService fillingStationService;
    private Context context;

    //Constructor for the fuel type adapter
    public FuelTypeAdapter(List<FuelModel> fuelTypes, FillingStationModel fillingStation, Context context)
    {
        this.fuelTypes = fuelTypes;
        this.fillingStation = fillingStation;
        this.context = context;
        this.fillingStationService = new FillingStationService(context);
    }

    //Set card view for the recycler view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_fuel_type,parent,false);
        return  new FuelTypeAdapter.ViewHolder(view);
    }

    //Set values for the properties in the card view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = fuelTypes.get(position).getFuelName();
        String status = fuelTypes.get(position).getStatus();


      //When click on the radio button it wil trigger this methoid
        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedButton = group.findViewById(checkedId);
                if(!checkedButton.getText().equals(status)) {
                    updateFuelTypeStatus(name,checkedButton.getText().toString());
                }

            }
        });
         holder.setData(name,status);

    }

    //When user clicks on radio button it will change the status according to that
    private void updateFuelTypeStatus(String name, String status) {
        FuelModel updatedFuel = new FuelModel();
        List<FuelModel> updatedList = new ArrayList<FuelModel>();
        updatedList.addAll(fillingStation.getFuelTypes());

       updatedList.removeIf(fuelModel -> fuelModel.fuelName==name);
        updatedFuel.setFuelName(name);
        updatedFuel.setStatus(status);
        updatedList.add(updatedFuel);
        fillingStation.setFuelTypes(updatedList);
        fillingStationService.UpdateFillingStationDetails(fillingStation, new FillingStationService.UpdateFillingStationDetailsResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String successMessage) {
                Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(context, ViewStationActivity.class);
                intent.putExtra("station",fillingStation);
                context.startActivity(intent);
            }
        });
    }


    //Return Item count
    @Override
    public int getItemCount() {
        return fuelTypes.size();
    }



    //This class set the recycler view with the card

    public class ViewHolder extends RecyclerView.ViewHolder {

  //Initialize variables
        public TextView fuelTypeName;
        public RadioGroup radioGroup;
        public RadioButton available;
        public  RadioButton finished;


        public ViewHolder(View itemView) {
            super(itemView);


            //Assign variables
            fuelTypeName = itemView.findViewById(R.id.Txt_FuelTypeCard_Name);
            available = itemView.findViewById(R.id.Radio_FuelTypeCard_Available);
            finished = itemView.findViewById(R.id.Radio_FuelTypeCard_Finish);
            radioGroup = itemView.findViewById(R.id.RadioGroup_FuelTypeCard);
        }

        //This wil set radio button values according to previosly got values
        public void setData(String name,String status) {
            fuelTypeName.setText(name);
            available.setChecked(status.equals("Available"));
            finished.setChecked(status.equals("Finished"));

        }
    }
}
