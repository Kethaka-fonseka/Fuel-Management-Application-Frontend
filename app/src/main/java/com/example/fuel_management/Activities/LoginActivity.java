package com.example.fuel_management.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.fuel_management.R;
import com.example.fuel_management.SQLite.DatabaseHelper;
import com.example.fuel_management.Services.UserService;
import com.example.fuel_management.Session.SessionManager;

import org.json.JSONException;


public class LoginActivity extends AppCompatActivity {

    //Initialize variables
    private EditText edt_userName, edt_password;
    private Button btn_login;
    private TextView txt_registerHere;
    private AwesomeValidation awesomeValidation;
    private UserService userService;
    private DatabaseHelper databaseHelper;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);

        //Assign Variables
       edt_userName = findViewById(R.id.Edt_Login_Username);
       edt_password = findViewById(R.id.Edt_Login_Password);
       btn_login =(Button)findViewById(R.id.Btn_Login);
       txt_registerHere =(TextView)findViewById(R.id.Txt_Login_RegisterHere);
       userService = new UserService(LoginActivity.this);
       databaseHelper = new DatabaseHelper(LoginActivity.this);

       //Initialize the validation object
       awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

       //Add validation for username
       awesomeValidation.addValidation(this,R.id.Edt_Login_Username, RegexTemplate.NOT_EMPTY, R.string.Invalid_userName);

       //Add Validation for password
       awesomeValidation.addValidation(this,R.id.Edt_Login_Password, ".{6,}", R.string.Invalid_password);


       txt_registerHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, RegisterAsActivity.class);
                startActivity(intent);
            }
        });

       btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(awesomeValidation.validate()){
                   userService.userLogin(edt_userName.getText().toString(), edt_password.getText().toString(), new UserService.UserLoginResponse() {
                       @Override
                       public void onError(String message) {
                           edt_userName.setText("");
                           edt_password.setText("");
                           Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                       }

                       @Override
                       public void onResponse(String token) {
                           try {
                               //Store login credentials to SQLite database
                               boolean success = databaseHelper.insert(edt_userName.getText().toString(),edt_password.getText().toString());
                               if(success){
                                   Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                   userService.createUserSession(token);
                                   Intent intent=new Intent(LoginActivity.this, UserHomeActivity.class);
                                   startActivity(intent);
                               }
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                       }
                   });
               }
            }
        });
    }

    //Create on start method
    @Override
    protected void onStart() {
        super.onStart();

        //Checking whether user logged in or not
        //Already Logged user will move to Home Screen
        SessionManager sessionManager = new SessionManager(LoginActivity.this);
        String isUserLoggedIn = sessionManager.getSession();

        if (!isUserLoggedIn.equals("NO")){
            Intent intent=new Intent(LoginActivity.this, UserHomeActivity.class);
            startActivity(intent);
        }
    }
}
