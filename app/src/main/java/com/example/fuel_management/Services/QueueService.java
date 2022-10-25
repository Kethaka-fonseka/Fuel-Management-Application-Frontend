package com.example.fuel_management.Services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.fuel_management.Activities.RequestHandler;
import com.example.fuel_management.Models.FillingStationModel;
import com.example.fuel_management.Models.QueueModel;
import com.example.fuel_management.Models.TimeFormatDTO;
import com.example.fuel_management.Models.VehicleTypeDTO;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class QueueService {

    public static final String QUEUE_API_URL = "https://94be-2402-d000-8104-fb5f-5c9b-d7bc-a6ed-7233.in.ngrok.io/api/queues/";

    Context context;

    public QueueService(Context context) {
        this.context = context;
    }

    public interface GetQueueLengthByStationResponse{
        void onError(String message);

        void onResponse(List<VehicleTypeDTO> VehicleTypes);
    }

    public void getQueueLengthByStation(String name,GetQueueLengthByStationResponse getQueueLengthByStationResponse){
        String url = QUEUE_API_URL+"getcount/"+name;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray jsonArray = response;
                    ArrayList<VehicleTypeDTO> vehicleTypeDTOArrayList = new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject vehicleTypeJson = jsonArray.getJSONObject(i);
                        VehicleTypeDTO vehicleTypeDTO = new VehicleTypeDTO();
                        vehicleTypeDTO.setVehicleType(vehicleTypeJson.getString("vehicleType"));
                        vehicleTypeDTO.setTotal(vehicleTypeJson.getInt("total"));
                        vehicleTypeDTOArrayList.add(vehicleTypeDTO);
                    }
                    getQueueLengthByStationResponse.onResponse(vehicleTypeDTOArrayList);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorData = new String(error.networkResponse.data);
                getQueueLengthByStationResponse.onError(errorData);
            }
        });
        RequestHandler.getInstance(context).addToRequestQueue(request);
    }

    public interface GetTimeWaitingAtQueueByStationResponse{
        void onError(String message);

        void onResponse(TimeFormatDTO timeFormat);
    }

    public void getTimeWaitingAtQueueByStation(String name,GetTimeWaitingAtQueueByStationResponse getTimeWaitingAtQueueByStationResponse){
        String url = QUEUE_API_URL+"gettime/"+name;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                TimeFormatDTO timeFormatDTO = new TimeFormatDTO();
                try {
                    timeFormatDTO.setHours(response.getInt("Hours"));
                    timeFormatDTO.setMinutes(response.getInt("Minutes"));
                    timeFormatDTO.setSeconds(response.getInt("Seconds"));
                    getTimeWaitingAtQueueByStationResponse.onResponse(timeFormatDTO);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getTimeWaitingAtQueueByStationResponse.onError(error.toString());
            }
        });
        RequestHandler.getInstance(context).addToRequestQueue(request);
    }

    public interface AddCustomerToQueueResponse{
        void onError(String message);

        void onResponse(JSONObject response);
    }

    public void addCustomerToQueueByStation(QueueModel queueModel, AddCustomerToQueueResponse addCustomerToQueueResponse){
        String url = QUEUE_API_URL;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                addCustomerToQueueResponse.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                addCustomerToQueueResponse.onError(error.toString());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() {
                String requestBody = null;
                JSONObject queueData = new JSONObject();
                try {
                    queueData.put("customer",queueModel.getCustomer());
                    queueData.put("fillingStation",queueModel.getFillingStation());
                    queueData.put("vehicleType",queueModel.getVehicleType());
                    queueData.put("status",queueModel.getStatus());
                    requestBody = queueData.toString();
                    System.out.println("requestBody===>"+requestBody);
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (JSONException | UnsupportedEncodingException e) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(request);
    }

    public interface UpdateCustomerToQueueResponse{
        void onError(String message);

        void onResponse(String successMessage);
    }

    public void updateCustomerToQueueByStation(QueueModel queueModel, UpdateCustomerToQueueResponse updateCustomerToQueueResponse){
        String url = QUEUE_API_URL+queueModel.id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                updateCustomerToQueueResponse.onResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                updateCustomerToQueueResponse.onError(error.toString());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() {
                String requestBody = null;
                JSONObject queueData = new JSONObject();
                try {
                    queueData.put("id",queueModel.getId());
                    queueData.put("customer",queueModel.getCustomer());
                    queueData.put("fillingStation",queueModel.getFillingStation());
                    queueData.put("vehicleType",queueModel.getVehicleType());
                    queueData.put("status",queueModel.getStatus());
                    queueData.put("arrivalTime",queueModel.getArrivalTime());
                    queueData.put("deparTime",queueModel.getDeparTime());
                    requestBody = queueData.toString();
                    System.out.println("requestBody===>"+requestBody);
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (JSONException | UnsupportedEncodingException e) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(request);
    }

    public interface QetQueueByIdResponse{
        void onError(String message);
        void onResponse(QueueModel queueModel);
    }

    public void getQueueByIdService(String id,QetQueueByIdResponse getQueueByIdResponse){
        String url = QUEUE_API_URL + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    QueueModel queueModel = new QueueModel();
                    queueModel.setId(response.getString("id"));
                    queueModel.setCustomer(response.getString("customer"));
                    queueModel.setFillingStation(response.getString("fillingStation"));
                    queueModel.setVehicleType(response.getString("vehicleType"));
                    queueModel.setArrivalTime(response.getString("arrivalTime"));
                    queueModel.setDeparTime(response.getString("deparTime"));
                    queueModel.setStatus(response.getString("status"));
                    getQueueByIdResponse.onResponse(queueModel);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorData = new String(error.networkResponse.data);
                getQueueByIdResponse.onError(errorData);
            }
        });
        RequestHandler.getInstance(context).addToRequestQueue(request);
    }

}
