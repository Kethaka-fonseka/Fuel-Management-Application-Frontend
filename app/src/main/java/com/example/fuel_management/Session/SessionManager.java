package com.example.fuel_management.Session;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class SessionManager {
    private static final String SESSION_ID ="id" ;
    private static final String SESSION_USERNAME = "username" ;
    private static final String SESSION_TYPE = "type";

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

    //This will return session for the user
    public String  getSession(){
      return sharedPreferences.getString(SESSION_ID,"NO");
    }

    //Remove the session stored when user logout
    public void removeSession(){
        editor.putString(SESSION_ID,"NO").commit();
    }
}
