package com.example.fuel_management.Activities;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * All the Request that send to the API handle in this class  .
 *
 * @version 1.0
 */

public class RequestHandler {
    //Initialize variables
    private static RequestHandler instance;
    private RequestQueue requestQueue;
    private static Context ctx;



    //This is the constructor of the RequestHandler class
    private RequestHandler(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    //Get singleton instance od RequestHandler class
    public static synchronized RequestHandler getInstance(Context context) {
        if (instance == null) {
            instance = new RequestHandler(context);
        }
        return instance;
    }

    //When request for queue instance this will return queue instance
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    //Add a particular request to the request queue
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}