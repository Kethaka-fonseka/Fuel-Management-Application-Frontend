package com.example.fuel_management;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class RegisterActivity extends AppCompatActivity {

    //Initialize variables
    private Button btn_register;
    private EditText edt_firstName, edt_lastName, edt_userName, edt_email, edt_password, edt_confirmPassword;
    private TextView txt_login_here;
    private AwesomeValidation awesomeValidation;
    private UserModel user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_form);

        //Assign variables
        btn_register =(Button)findViewById(R.id.Btn_Register);
        txt_login_here =(TextView)findViewById(R.id.Txt_Regitser_LoginHere);
        edt_firstName = findViewById(R.id.Edt_Register_Firstname);
        edt_lastName = findViewById(R.id.Edt_Register_Lastname);
        edt_userName = findViewById(R.id.Edt_Register_Username);
        edt_email = findViewById(R.id.Edt_Register_Email);
        edt_password = findViewById(R.id.Edt_Register_Password);
        edt_confirmPassword = findViewById(R.id.Edt_Register_ConfirmPassword);

        //Initialize the validation object
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Add validation for firstname
        awesomeValidation.addValidation(this,R.id.Edt_Register_Firstname, RegexTemplate.NOT_EMPTY,R.string.Invalid_firstName);

        //Add validation for lastname
        awesomeValidation.addValidation(this,R.id.Edt_Register_Lastname, RegexTemplate.NOT_EMPTY,R.string.Invalid_lastName);

        //Add validation for username
        awesomeValidation.addValidation(this,R.id.Edt_Register_Username, RegexTemplate.NOT_EMPTY,R.string.Invalid_userName);

        //Add validation for email
        awesomeValidation.addValidation(this,R.id.Edt_Register_Email, Patterns.EMAIL_ADDRESS, R.string.Invalid_email);

        //Add Validation for password
        awesomeValidation.addValidation(this,R.id.Edt_Register_Password, ".{6,}", R.string.Invalid_password);

        //Add validation for confirm password
        awesomeValidation.addValidation(this,R.id.Edt_Register_ConfirmPassword, R.id.Edt_Register_Password, R.string.Invalid_confirmPassword);

        Intent intent = getIntent();
        String user_type = intent.getStringExtra("user_type");

        txt_login_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check  validation
                if(awesomeValidation.validate()) {

                    //Initialize user
                    user = new UserModel();
                    user.setFirstName(edt_firstName.getText().toString());
                    user.setLastName(edt_lastName.getText().toString());
                    user.setUserName(edt_userName.getText().toString());
                    user.setType(user_type);
                    user.setEmail(edt_email.getText().toString());
                    //Create instance of user service class
                    UserService userService = new UserService(RegisterActivity.this);

                    userService.registerNewUserUser(user, edt_password.getText().toString(), new UserService.RegisterNewUserResponse() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String successMessage) {
                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });

                }
            }
        });

    }
}
