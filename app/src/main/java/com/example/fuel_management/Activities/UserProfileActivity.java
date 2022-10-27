package com.example.fuel_management.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.fuel_management.Models.UserModel;
import com.example.fuel_management.R;
import com.example.fuel_management.Services.UserService;
import com.example.fuel_management.Session.SessionManager;

public class UserProfileActivity extends AppCompatActivity {

    UserService userService;
    SessionManager sessionManager;
    EditText edt_User_Profile_Firstname,edt_User_Profile_Lastname,edt_User_Profile_Username,
    edt_User_Profile_Email;
    Button update_User_Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        sessionManager = new SessionManager(this);
        userService = new UserService(this);
        edt_User_Profile_Firstname = (EditText)findViewById(R.id.Edt_User_Profile_Firstname);
        edt_User_Profile_Lastname = (EditText)findViewById(R.id.Edt_User_Profile_Lastname);
        edt_User_Profile_Username = (EditText)findViewById(R.id.Edt_User_Profile_Username);
        edt_User_Profile_Email = (EditText)findViewById(R.id.Edt_User_Profile_Email);
        update_User_Data = (Button)findViewById(R.id.Btn_User_Profile);
        initData();
        update_User_Data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpdateUserData();
            }
        });
    }

    public void initData(){
        String userId= sessionManager.getSessionID();
        userService.getUserByID(userId, new UserService.GetUserByIdResponse() {
            @Override
            public void onError(String message) {
                System.out.println("user===>"+message);

            }

            @Override
            public void onResponse(UserModel user) {
                System.out.println("user===>"+user.getEmail());
                setData(user);
            }
        });
    }

    public void setData(UserModel user){
        edt_User_Profile_Firstname.setText(user.getFirstName());
        edt_User_Profile_Lastname.setText(user.getLastName());
        edt_User_Profile_Username.setText(user.getUserName());
        edt_User_Profile_Email.setText(user.getEmail());
    }

    public void setUpdateUserData(){
        UserModel userData = new UserModel();
        userData.setFirstName(edt_User_Profile_Firstname.getText().toString());
        userData.setLastName(edt_User_Profile_Lastname.getText().toString());
        userData.setUserName(edt_User_Profile_Username.getText().toString());
        userData.setEmail(edt_User_Profile_Email.getText().toString());
        userService.updateExitingUser(userData, new UserService.UpdateUserResponse() {
            @Override
            public void onError(String message) {
                System.out.println("user===>"+message);
            }

            @Override
            public void onResponse(String successMessage) {
                System.out.println("user===>"+successMessage);
            }
        });
    }

}
