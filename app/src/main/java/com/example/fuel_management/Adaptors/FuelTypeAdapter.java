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

public class FuelTypeAdapter extends RecyclerView.Adapter<FuelTypeAdapter.ViewHolder>{

    private List<FuelModel> fuelTypes;
    private FillingStationModel fillingStation;
    private FillingStationService fillingStationService;
    private Context context;

    public FuelTypeAdapter(List<FuelModel> fuelTypes, FillingStationModel fillingStation, Context context)
    {
        this.fuelTypes = fuelTypes;
        this.fillingStation = fillingStation;
        this.context = context;
        this.fillingStationService = new FillingStationService(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_fuel_type,parent,false);
        return  new FuelTypeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = fuelTypes.get(position).getFuelName();
        String status = fuelTypes.get(position).getStatus();



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


    @Override
    public int getItemCount() {
        return fuelTypes.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView fuelTypeName;
        RadioGroup radioGroup;
        public RadioButton available;
        public  RadioButton finished;


        public ViewHolder(View itemView) {
            super(itemView);

            fuelTypeName = itemView.findViewById(R.id.Txt_FuelTypeCard_Name);
            available = itemView.findViewById(R.id.Radio_FuelTypeCard_Available);
            finished = itemView.findViewById(R.id.Radio_FuelTypeCard_Finish);
            radioGroup = itemView.findViewById(R.id.RadioGroup_FuelTypeCard);
        }

        public void setData(String name,String status) {
            fuelTypeName.setText(name);
            available.setChecked(status.equals("Available"));
            finished.setChecked(status.equals("Finished"));

        }
    }
}
