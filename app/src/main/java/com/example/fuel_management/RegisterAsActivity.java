package com.example.fuel_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterAsActivity extends AppCompatActivity {

    private Button register_as_user_btn;
    private Button register_as_owner_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_as_form);

        register_as_user_btn =(Button)findViewById(R.id.Btn_RegisterAs_User);
        register_as_owner_btn =(Button)findViewById(R.id.Btn_RegisterAs_Owner);

        register_as_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterAsActivity.this,RegisterActivity.class);
                intent.putExtra("user_type", "user");
                startActivity(intent);
            }
        });

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
