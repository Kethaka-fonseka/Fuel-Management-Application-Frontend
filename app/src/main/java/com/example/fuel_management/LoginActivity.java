package com.example.fuel_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private Button userlogin_btn;
    private TextView register_here_txt;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);

        userlogin_btn =(Button)findViewById(R.id.register_button);
        register_here_txt =(TextView)findViewById(R.id.login_here_text);

        register_here_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterAsActivity.class);
                startActivity(intent);
            }
        });

        userlogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, UserHomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
