/*
package com.example.fuel_management;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
//This class Includes All Methods related to access the API
public class UserServices {

    public static final String USER_API_URL = "http://10.0.2.2:5234/api/User/";
    Context context;


    public UserServices(Context context) {
        this.context = context;
    }

    //Get a user by ID

    interface GetUserByIdResponse{
        void onError(String message);

        void onResponse(UserModel user);
    }


    public void getUserByID(String Id, GetUserByIdResponse getUserByIdResponse){
        String url = USER_API_URL+Id;
     //get the json object
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
           */
/*  Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();*//*


                //get the array of json objects
                try {
                    UserModel user =new UserModel();
                    String jsonData = "{\"users\" : ["+response+"]}";
                    Toast.makeText(context, jsonData, Toast.LENGTH_SHORT).show();

                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONArray jsonArray = jsonObject.getJSONArray("users");

                    JSONObject receivedUserData = (JSONObject) jsonArray.get(0);

                   */
/* Toast.makeText(context, receivedUserData.getString("firstName"), Toast.LENGTH_SHORT).show();*//*

                    user.setId(receivedUserData.getString("id"));
                    user.setFirstName(receivedUserData.getString("firstName"));
                    user.setLastName(receivedUserData.getString("lastName"));
                    user.setType(receivedUserData.getString("type"));
                    getUserByIdResponse.onResponse(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getUserByIdResponse.onError(error.toString());
            }
        });

        RequestHandler.getInstance(context).addToRequestQueue(request);

    }

    //Get all users

    interface GetAllUsersResponse{
        void onError(String message);

        void onResponse(List<UserModel> user);
    }



   public void getAllUsers(GetAllUsersResponse getAllUsersResponse){
       String url = USER_API_URL;
       JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
           @Override
           public void onResponse(JSONArray response) {

               try {
                   List<UserModel> user_list = new ArrayList<>();
                   String jsonData = "{\"users\" : "+response+"}";
                   JSONObject jsonObject = new JSONObject(jsonData);
                   JSONArray jsonArray = jsonObject.getJSONArray("users");
                   Toast.makeText(context,jsonArray.toString(), Toast.LENGTH_SHORT).show();



                  for (int i=0;i<jsonArray.length();i++){
                      UserModel user =new UserModel();
                      JSONObject receivedUserData = (JSONObject) jsonArray.get(0);
                      user.setId(receivedUserData.getString("id"));
                      user.setFirstName(receivedUserData.getString("firstName"));
                      user.setLastName(receivedUserData.getString("lastName"));
                      user.setType(receivedUserData.getString("type"));
                      user_list.add(user);

                   }
                   getAllUsersResponse.onResponse(user_list);
                   } catch (JSONException e) {
                   e.printStackTrace();
               }


           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               getAllUsersResponse.onError(error.toString());
           }
       });

       RequestHandler.getInstance(context).addToRequestQueue(request);

    }

///Create a new  user
    interface  AddUserResponse{
        void onError(String message);

        void onResponse(String successMessage);
    }


    public void addNewUser(UserModel user,AddUserResponse addUserResponse){
        String url = USER_API_URL;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject message = new JSONObject(response.toString());
                    addUserResponse.onResponse(response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              addUserResponse.onError(error.toString());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() {
                String requestBody = null;
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("firstName",user.getFirstName());
                    jsonBody.put("lastName",user.getLastName());
                    jsonBody.put("type",user.getType());
                    requestBody = jsonBody.toString();

                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (JSONException | UnsupportedEncodingException e) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }



            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(request);
    }

//Update user Details

    interface  UpdateUserResponse{
        void onError(String message);

        void onResponse(String successMessage);
    }


    public void UpdateExitingUser(UserModel user, UpdateUserResponse updateUserResponse){
        String url = USER_API_URL+user.getId();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject message = new JSONObject(response.toString());
                    updateUserResponse.onResponse(message.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                updateUserResponse.onError(error.toString());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() {
                String requestBody = null;
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("id",user.getId());
                    jsonBody.put("firstName",user.getFirstName());
                    jsonBody.put("lastName",user.getLastName());
                    jsonBody.put("type",user.getType());
                    requestBody = jsonBody.toString();

                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (JSONException | UnsupportedEncodingException e) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }



            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(request);
    }


    //Delete a Record
    interface  DeleteUserResponse{
        void onError(String message);

        void onResponse(String successMessage);
    }

    public void deleteUser(String Id,DeleteUserResponse deleteUserResponse){
        String url = USER_API_URL+Id;
        StringRequest request = new StringRequest(Request.Method.DELETE,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                deleteUserResponse.onResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                deleteUserResponse.onError(error.toString());
            }
        });
        RequestHandler.getInstance(context).addToRequestQueue(request);
    }
}
*/
