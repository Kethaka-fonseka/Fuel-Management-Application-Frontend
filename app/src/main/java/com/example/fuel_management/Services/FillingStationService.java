package com.example.fuel_management.Services;

import android.content.Context;

public class FillingStationService {
    public static final String FILLING_STATION_API_URL =
//            "http://10.0.2.2:5234/api/users/";
            "https://34f2-2402-d000-8104-9a28-5c9b-d7bc-a6ed-7233.in.ngrok.io/api/queues/";
    Context context;

    public FillingStationService(Context context) {
        this.context = context;
    }

    public void getFillingStationDetails(){

    }
}
