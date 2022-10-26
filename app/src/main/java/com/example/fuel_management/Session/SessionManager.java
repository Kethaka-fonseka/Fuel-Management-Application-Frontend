package com.example.fuel_management.Session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.fuel_management.Models.QueueModel;

import org.json.JSONException;
import org.json.JSONObject;

public class SessionManager {
    private static final String SESSION_ID ="id" ;
    private static final String SESSION_USERNAME = "username" ;
    private static final String SESSION_TYPE = "type";

    private static final String SESSION_QUEUE_ID = "queue_id";
    private static final String SESSION_QUEUE_STATUS = "status";
    private static final String SESSION_CUSTOMER ="customer";
    private static final String SESSION_QUEUE_FILLING_STATION = "filling_Station";
    private static final String SESSION_QUEUE_VEHICLE_TYPE = "vehicle_Type";
    private static final String SESSION_QUEUE_ARRIVAL_TIME = "vehicle_Type";
    private static final String SESSION_QUEUE_DEPART_TIME = "depart_Time";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREFERENCE_NAME = "session";

    //Initialize variables
    public SessionManager(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //Save the session for the successfully logged user
    public void saveSession(JSONObject user) throws JSONException {
         String id = user.getString("Id");
         String username = user.getString("userName");
         String type = user.getString("type");

         editor.putString(SESSION_ID,id).commit();
        editor.putString(SESSION_USERNAME, username).commit();
        editor.putString(SESSION_TYPE,type).commit();

    }

    //This will return session Id for the user
    public String getSessionID(){
      return sharedPreferences.getString(SESSION_ID,"NO");
    }

    //This will return session type for the user
    public String getSessionType(){
        return sharedPreferences.getString(SESSION_TYPE,"");
    }


    //Remove the session stored when user logout
    public void removeSession(){
        editor.putString(SESSION_ID,"NO").commit();
    }

    public String getSessionUsername(){
        return sharedPreferences.getString(SESSION_USERNAME,"");
    }

    public void saveQueueDetails(JSONObject queue)throws JSONException {
        String id = queue.getString("id");
        String customer = queue.getString("customer");
        String status  = queue.getString("status");
        String fillingStation = queue.getString("fillingStation");
        String vehicleType = queue.getString("vehicleType");
        String arrivalTime = queue.getString("arrivalTime");
        String deparTime = queue.getString("deparTime");
        editor.putString(SESSION_QUEUE_ID,id).commit();
        editor.putString(SESSION_QUEUE_STATUS,status).commit();
        editor.putString(SESSION_CUSTOMER,customer).commit();
        editor.putString(SESSION_QUEUE_FILLING_STATION,fillingStation).commit();
        editor.putString(SESSION_QUEUE_VEHICLE_TYPE,vehicleType).commit();
        editor.putString(SESSION_QUEUE_ARRIVAL_TIME,arrivalTime).commit();
        editor.putString(SESSION_QUEUE_DEPART_TIME,deparTime).commit();
    }

    public String getQueueSessionID(){
        return sharedPreferences.getString(SESSION_QUEUE_ID,"QUEUENO");
    }

    public String getQueueSessionStatus(){
        return sharedPreferences.getString(SESSION_QUEUE_STATUS,"QUEUESTATUS");
    }

    public String getQueueCustomer(){
        return sharedPreferences.getString(SESSION_CUSTOMER,"QUEUECUSTOMER");
    }

    public String getQueueFillingStation(){
        return sharedPreferences.getString(SESSION_QUEUE_FILLING_STATION,"QUEUEFILLING");
    }

    public String getQueueVehicleType(){
        return sharedPreferences.getString(SESSION_QUEUE_VEHICLE_TYPE,"QUEUETYPE");
    }

    public String getQueueArrivalTime(){
        return sharedPreferences.getString(SESSION_QUEUE_ARRIVAL_TIME,"QUEUEAT");
    }

    public String getQueueDepartTime(){
        return sharedPreferences.getString(SESSION_QUEUE_DEPART_TIME,"QUEUEDT");
    }

    public void removeQueueSession(){
        editor.putString(SESSION_QUEUE_ID,"NO").commit();
        editor.putString(SESSION_QUEUE_STATUS,"NO").commit();
        editor.putString(SESSION_CUSTOMER,"NO").commit();
        editor.putString(SESSION_QUEUE_FILLING_STATION,"NO").commit();
        editor.putString(SESSION_QUEUE_VEHICLE_TYPE,"NO").commit();
        editor.putString(SESSION_QUEUE_ARRIVAL_TIME,"NO").commit();
        editor.putString(SESSION_QUEUE_DEPART_TIME,"NO").commit();
    }
}
