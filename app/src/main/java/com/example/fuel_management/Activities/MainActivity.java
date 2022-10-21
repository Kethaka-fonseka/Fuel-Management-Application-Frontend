package com.example.fuel_management.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuel_management.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText FirstName, LastName, Type;
    Button  Register_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

//        FirstName = findViewById(R.id.edt_FirstName);
//
//        LastName = findViewById(R.id.edt_LastName);
//
//        Type = findViewById(R.id.edt_Type);
//
//        Register_Btn = findViewById(R.id.btn_Register);
//
//        Register_Btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {;
//            UserServices userServices = new UserServices(MainActivity.this);
//
//           /*
//           //For Get User By Id
//           userServices.getUserByID(FirstName.getText().toString(), new UserServices.GetUserByIdResponse() {
//                @Override
//                public void onError(String message) {
//                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onResponse(UserModel user) {
//                    Toast.makeText(MainActivity.this, user.getFirstName().toString(), Toast.LENGTH_SHORT).show();
//                }
//            });*/
//
//               /*
//               //For get All Users
//               userServices.getAllUsers(new UserServices.GetAllUsersResponse() {
//                    @Override
//                    public void onError(String message) {
//                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onResponse(List<UserModel> user) {
//                        Toast.makeText(MainActivity.this, user.get(0).getFirstName().toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });*/
//
//
//               /*
//               //For Add User
//               UserModel user = new UserModel();
//                user.setFirstName(FirstName.getText().toString());
//                user.setLastName(LastName.getText().toString());
//                user.setType(Type.getText().toString());
//
//                userServices.addNewUser(user, new UserServices.AddUserResponse() {
//                    @Override
//                    public void onError(String message) {
//                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onResponse(String successMessage) {
//                        Toast.makeText(MainActivity.this, successMessage, Toast.LENGTH_SHORT).show();
//                    }
//                });
//*/
//              /*
//
//               //For Update Data
//               UserModel user = new UserModel();
//                user.setId("634c234f7863d0e188f242a0");
//                user.setFirstName(FirstName.getText().toString());
//                user.setLastName(LastName.getText().toString());
//                user.setType(Type.getText().toString());
//                userServices.UpdateExitingUser(user, new UserServices.UpdateUserResponse() {
//                    @Override
//                    public void onError(String message) {
//                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onResponse(String successMessage) {
//                        Toast.makeText(MainActivity.this, successMessage, Toast.LENGTH_SHORT).show();
//                    }
//                });*/
//
//
//                //For Delete
//                userServices.deleteUser("634c234f7863d0e188f242a0", new UserServices.DeleteUserResponse() {
//                    @Override
//                    public void onError(String message) {
//                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onResponse(String successMessage) {
//                        Toast.makeText(MainActivity.this, successMessage, Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//        });
    }
}