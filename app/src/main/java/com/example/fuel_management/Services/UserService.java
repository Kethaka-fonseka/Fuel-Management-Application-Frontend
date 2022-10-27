package com.example.fuel_management.Services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.fuel_management.Activities.RequestHandler;
import com.example.fuel_management.Session.SessionManager;
import com.example.fuel_management.Models.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
/**
 * This class Includes All Methods related to access the API .
 *
 * @version 1.0
 */
public class UserService {
    public static final String USER_API_URL = ConnectionSettings.CONNECTION_URL+ "users/";
    Context context;

    //User service constructor
    public UserService(Context context) {
        this.context = context;
    }

    //New user register ot the application response

    public interface  RegisterNewUserResponse{
        void onError(String message);

        void onResponse(String successMessage);
    }

    //New user register ot the application
    public void registerNewUserUser(UserModel user, String password, RegisterNewUserResponse registerNewUserResponse){
        String url = USER_API_URL+"register";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                registerNewUserResponse.onResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorData = new String(error.networkResponse.data);

                registerNewUserResponse.onError(errorData);
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
                //This contain the user data that got form the form
                JSONObject userData = new JSONObject();
                //This contain all the data need to be send in Json Object format
                JSONObject jsonBody = new JSONObject();
                try {
                    //This is basically mapping user to json body
                    userData.put("firstName",user.getFirstName());
                    userData.put("lastName",user.getLastName());
                    userData.put("userName",user.getUserName());
                    userData.put("email",user.getEmail());
                    userData.put("type",user.getType());

                    jsonBody.put("userData",userData);
                    jsonBody.put("password",password);
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



 //This interface handle responses
 public interface  UserLoginResponse{
       void onError(String message);

      void onResponse(String token);
     }


    //Login to the application
    public void userLogin(String username, String password, UserLoginResponse userLoginResponse){
        String url = USER_API_URL+"login";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                userLoginResponse.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorData = new String(error.networkResponse.data);
                userLoginResponse.onError(errorData);
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
                //This contain all the data need to be send in Json Object format
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("userName",username);
                    jsonBody.put("password",password);
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

 //Decoder for decode the token
 private static String decoder(String encodedString) {
     return new String(Base64.getUrlDecoder().decode(encodedString));
 }

//Create session for the user
public void createUserSession(String token) throws JSONException {
        String [] tokenParts = token.split("\\.");

    JSONObject header = new JSONObject(decoder(tokenParts[0]));
    JSONObject payload = new JSONObject(decoder(tokenParts[1]));
    String signature = decoder(tokenParts[2]);

    //Save the session for the user
    SessionManager sessionManager = new SessionManager(context);
    sessionManager.saveSession(payload);

}

    //Get a user by ID

    public interface GetUserByIdResponse{
        void onError(String message);

        void onResponse(UserModel user);
    }


    public void getUserByID(String Id, GetUserByIdResponse getUserByIdResponse){
        String url = USER_API_URL+Id;
        //get the json object
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //get the array of json objects
                try {
                    UserModel user =new UserModel();
                    String jsonData = "{\"users\" : ["+response+"]}";

                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONArray jsonArray = jsonObject.getJSONArray("users");

                    JSONObject receivedUserData = (JSONObject) jsonArray.get(0);

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

    //Update user Details
    public interface UpdateUserResponse{
        void onError(String message);

        void onResponse(String successMessage);
    }

    public void updateExitingUser(UserModel user, UpdateUserResponse updateUserResponse){
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

}
