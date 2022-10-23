package com.example.fuel_management.Services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.fuel_management.Activities.RequestHandler;
import com.example.fuel_management.Models.FillingStationModel;
import com.example.fuel_management.Models.FuelModel;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FillingStationService {
    public static final String FILLING_STATION_API_URL = "http://10.0.2.2:5234/api/fillingStations/";
    public static final String STATIONS = "stations";
    Context context;

    //Filling station service constructor
    public FillingStationService(Context context) {

        this.context = context;
    }


    //Add new filling station by the user
    public interface  AddNewFillingStationResponse{
        void onError(String message);

        void onResponse(String successMessage);
    }


    public void AddNewFillingStation(FillingStationModel stationModel, AddNewFillingStationResponse addNewFillingStationResponse){
        String url = FILLING_STATION_API_URL;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                addNewFillingStationResponse.onResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorData = new String(error.networkResponse.data);

                addNewFillingStationResponse.onError(errorData);
            }
        }){
            @Override
            public String getBodyContentType() {

                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() {
                //Request body contain the data tobe pass to the API.
                String requestBody = null;

                //Create list to store fuel types
                List fuelTypes = new ArrayList<FuelModel>();

                //This contain all the data need to be send in Json Object format
                JSONObject jsonBody = new JSONObject();
                try {
                    //This is basically mapping filling station data to json body
                   for( FuelModel fuel : stationModel.getFuelTypes()){
                       JSONObject fuelType = new JSONObject();
                       fuelType.put("fuelName",fuel.getFuelName());
                       fuelType.put("status", fuel.getStatus());
                       //Toast.makeText(context, fuelType.toString(), Toast.LENGTH_SHORT).show();
                       fuelTypes.add(fuelType);
                   }
                   //covert fuel list to json array type
                    JSONArray fuelJsonArray = new JSONArray(fuelTypes);

                    jsonBody.put("name", stationModel.getName());
                    jsonBody.put("owner", stationModel.getOwner());
                    jsonBody.put("location",stationModel.getLocation());
                    jsonBody.put("fuelTypes", fuelJsonArray);
                    requestBody = jsonBody.toString();
                    Log.d("KEthaka",requestBody);
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (JSONException | UnsupportedEncodingException e) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }



            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(request);
    }

    //Get all filling station that particular owner created

    public interface GetAllFillingStationsByOwnerResponse{
        void onError(String message);

        void onResponse(List<FillingStationModel> stations);
    }



    public void GetAllFillingStationsByOwner(String owner, GetAllFillingStationsByOwnerResponse getAllFillingStationsByOwnerResponse){
        String url = FILLING_STATION_API_URL;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    List<FillingStationModel>  station_list = new ArrayList<>();
                    String stationListAsJson = "{\"" + STATIONS + "\" : " +response+"}";
                    JSONObject stationJsonObject = new JSONObject(stationListAsJson);
                    JSONArray stationJsonArray = stationJsonObject.getJSONArray(STATIONS);


                    for (int i=0;i<stationJsonArray.length();i++){
                        FillingStationModel station =new FillingStationModel();
                        JSONObject receivedStationData = (JSONObject) stationJsonArray.get(i);
                        station.setId(receivedStationData.getString("id"));
                        station.setName(receivedStationData.getString("name"));
                        station.setLocation(receivedStationData.getString("location"));
                        station.setOwner(receivedStationData.getString("owner"));

                        JSONArray fuelTypesInJsonArray = receivedStationData.getJSONArray("fuelTypes");
                        List<FuelModel> fuelTypes = new ArrayList<>();


                        for(int j=0; j<fuelTypesInJsonArray.length();j++){
                            JSONObject fuelType = (JSONObject) fuelTypesInJsonArray.get(j);
                            FuelModel fuel =new FuelModel();
                            fuel.setFuelName(fuelType.getString("fuelName"));
                            fuel.setStatus(fuelType.getString("status"));
                            fuelTypes.add(fuel);
                        }
                        station.setFuelTypes(fuelTypes);

                        station_list.add(station);

                    }
                    getAllFillingStationsByOwnerResponse.onResponse(station_list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getAllFillingStationsByOwnerResponse.onError(error.toString());
            }
        });

        RequestHandler.getInstance(context).addToRequestQueue(request);

    }

    //Delete a selected filling station
    public interface  DeleteFillingStationResponse{
        void onError(String message);

        void onResponse(String successMessage);
    }

    public void DeleteFillingStation(String Id,DeleteFillingStationResponse deleteFillingStationResponse){
        String url = FILLING_STATION_API_URL+Id;
        StringRequest request = new StringRequest(Request.Method.DELETE,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                deleteFillingStationResponse.onResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                deleteFillingStationResponse.onError(error.toString());
            }
        });
        RequestHandler.getInstance(context).addToRequestQueue(request);
    }
}
