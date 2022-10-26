package com.example.fuel_management.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fuel_management.R;

/**
 * All the activities related to Login as interface  .
 *
 * @version 1.0
 */
public class RegisterAsActivity extends AppCompatActivity {


    //Initialize variables
    private Button register_as_user_btn;
    private Button register_as_owner_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_as_form);

        //Assign variables
        register_as_user_btn =(Button)findViewById(R.id.Btn_RegisterAs_User);
        register_as_owner_btn =(Button)findViewById(R.id.Btn_RegisterAs_Owner);


        //If user want to register as a normal user type will change to User
        register_as_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterAsActivity.this, RegisterActivity.class);
                intent.putExtra("user_type", "user");
                startActivity(intent);
            }
        });

        //If user want to register as a normal user type will change to Owner
        register_as_owner_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterAsActivity.this,RegisterActivity.class);
                intent.putExtra("user_type", "owner");
                startActivity(intent);
            }
        });
    }

}
