package com.example.fuel_management.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fuel_management.Models.UserModel;
import com.example.fuel_management.R;
import com.example.fuel_management.Services.UserService;
import com.example.fuel_management.Session.SessionManager;

public class UserProfileActivity extends AppCompatActivity {

    UserService userService;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        sessionManager = new SessionManager(this);
        userService = new UserService(this);
        String userId= sessionManager.getSessionID();
//        userService.getUserByID(userId, new UserService.GetUserByIdResponse() {
//            @Override
//            public void onError(String message) {
//
//            }
//
//            @Override
//            public void onResponse(UserModel user) {
//
//            }
//        });
    }
}