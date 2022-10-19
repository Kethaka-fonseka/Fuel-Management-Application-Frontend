package com.example.fuel_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private Button register_btn;
    private TextView login_here_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_form);

        register_btn =(Button)findViewById(R.id.register_button);
        login_here_txt =(TextView)findViewById(R.id.login_here_text);

        Intent intent = getIntent();
        String str = intent.getStringExtra("user_type");

        login_here_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this, UserHomeActivity.class);
                startActivity(intent);
            }
        });

    }
}
