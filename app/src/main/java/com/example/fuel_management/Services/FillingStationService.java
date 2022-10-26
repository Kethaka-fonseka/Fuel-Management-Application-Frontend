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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
/**
 * This class  Includes all the services related filling station crud operations .
 *
 * @version 1.0
 */
public class FillingStationService {

    //Initialize variables
    public static final String FILLING_STATION_API_URL = ConnectionSettings.CONNECTION_URL+"fillingStations/";
    public static final String STATIONS = "stations";
    public ZoneId zone = ZoneId.of("Asia/Colombo");
    Context context;

    //Filling station service constructor
    public FillingStationService(Context context) {
        this.context = context;
    }


    //Add new filling station by the user response
    public interface  AddNewFillingStationResponse{
        void onError(String message);

        void onResponse(String successMessage);
    }

    //Add new filling station by the user
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
                    jsonBody.put("fuelArrivalTime", LocalDateTime.now().plusHours(5).plusMinutes(30));
                    jsonBody.put("fuelFinishTime", LocalDateTime.now().plusHours(5).plusMinutes(30));
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

    //Get all filling station that particular owner created response

    public interface GetAllFillingStationsByOwnerResponse{
        void onError(String message);

        void onResponse(List<FillingStationModel> stations);
    }


    //Get all filling station that particular owner created
    public void GetAllFillingStationsByOwner(String owner, GetAllFillingStationsByOwnerResponse getAllFillingStationsByOwnerResponse){
        String url = FILLING_STATION_API_URL+"owner/"+owner;
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
                        station.setFuelArrivalTime(LocalDateTime.parse(receivedStationData.getString("fuelArrivalTime").substring(0,19)));
                        station.setFuelFinishTime(LocalDateTime.parse(receivedStationData.getString("fuelFinishTime").substring(0,19)));


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

    //Delete a selected filling station response
    public interface  DeleteFillingStationResponse{
        void onError(String message);

        void onResponse(String successMessage);
    }

    //Delete a selected filling station
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


    //Update filling station details response
    public interface  UpdateFillingStationDetailsResponse{
        void onError(String message);

        void onResponse(String successMessage);
    }


    //Update filling station details
    public void UpdateFillingStationDetails(FillingStationModel stationModel, UpdateFillingStationDetailsResponse updateFillingStationDetailsResponse){
        String url = FILLING_STATION_API_URL+stationModel.getId();
        StringRequest request = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                updateFillingStationDetailsResponse.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "running error", Toast.LENGTH_SHORT).show();
                String errorData = new String(error.networkResponse.data);

                updateFillingStationDetailsResponse.onError(errorData);
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

                    jsonBody.put("id",stationModel.getId());
                    jsonBody.put("name", stationModel.getName());
                    jsonBody.put("owner", stationModel.getOwner());
                    jsonBody.put("location",stationModel.getLocation());
                    jsonBody.put("fuelArrivalTime",stationModel.getFuelArrivalTime().plusHours(5).plusMinutes(30));
                    jsonBody.put("fuelFinishTime", stationModel.getFuelFinishTime().plusHours(5).plusMinutes(30));
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

    //Get all  filling station details response
      public interface  GetAllFillingStationsByUserResponse{
        void onError(String message);

        void onResponse(ArrayList<FillingStationModel> fillingStationModelArrayList);
    }




    //Get all  filling station details
    public void getFillingStationDetails(GetAllFillingStationsByUserResponse fillingStationNewResponse){
        String url = FILLING_STATION_API_URL;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray jsonArray = response;
                    ArrayList<FillingStationModel> fillingStationModelArray = new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject fillingStation = jsonArray.getJSONObject(i);
                        FillingStationModel fillingStationModel = new FillingStationModel();
                        fillingStationModel.setName(fillingStation.getString("name"));
                        fillingStationModel.setOwner(fillingStation.getString("owner"));
                        fillingStationModel.setLocation(fillingStation.getString("location"));

                        JSONArray fuelTypesInJsonArray = fillingStation.getJSONArray("fuelTypes");
                        List<FuelModel> fuelTypes = new ArrayList<>();

                        for(int j=0; j<fuelTypesInJsonArray.length();j++){
                            JSONObject fuelType = (JSONObject) fuelTypesInJsonArray.get(j);
                            FuelModel fuel =new FuelModel();
                            fuel.setFuelName(fuelType.getString("fuelName"));
                            fuel.setStatus(fuelType.getString("status"));
                            fuelTypes.add(fuel);
                        }
                        fillingStationModel.setFuelTypes(fuelTypes);
                        fillingStationModelArray.add(fillingStationModel);
                    }
                    fillingStationNewResponse.onResponse(fillingStationModelArray);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                fillingStationNewResponse.onError(error.toString());
            }
        });
        RequestHandler.getInstance(context).addToRequestQueue(request);
    }

}
