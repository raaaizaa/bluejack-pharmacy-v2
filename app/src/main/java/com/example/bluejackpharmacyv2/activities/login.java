package com.example.bluejackpharmacyv2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bluejackpharmacyv2.R;
import com.example.bluejackpharmacyv2.utils.user_database_helper;

public class login extends AppCompatActivity {

    private EditText emailField, passwordField;
    private Button loginButton, goToRegisterButton;
    private user_database_helper userDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();
    }

    private boolean loginValidation(String email, String password){
        userDb = new user_database_helper(this);
        boolean userExists = userDb.loginCheck(email, password);

        if(userExists){
            Log.i("login", "loginValidation: user exists!");
            return true;
        }else{
            Log.i("login", "loginValidation: user does not exists!");
            return false;
        }
    }

    private boolean isUserVerified(String email){
        userDb = new user_database_helper(this);
        boolean userIsVerified = userDb.checkVerified(email);

        if(userIsVerified){
            Log.i("login", "isUserVerified: User is verified!");
            return true;
        }else{
            Log.i("login", "isUserVerified: User isn't verified!");
            return false;
        }
    }

    private void initialize(){
        emailField = findViewById(R.id.login_email_field);
        passwordField = findViewById(R.id.login_password_field);
        goToRegisterButton = findViewById(R.id.go_to_register_button);
        loginButton = findViewById(R.id.login_button);

        setListener();
    }

    private void setListener(){
        loginButton.setOnClickListener(e -> {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();
            boolean loginIsValid = loginValidation(email, password);

            if(email.isEmpty() || password.isEmpty()){
                showToast("All fields must be filled!");
            }else{
                if(loginIsValid){
                    if(!isUserVerified(email)){
                        showToast("Your account hasn't been verified!");
                        goToOtp();
                    }else{
                        showToast("Login Success!");
                        goToHome();
                    }
                }else{
                    showToast("Invalid User!");
                }
            }
        });

        goToRegisterButton.setOnClickListener(e -> goToRegister());
    }

    private void goToRegister(){
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
    }

    private void goToHome(){
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
        finish();
    }

    private void goToOtp(){
        Intent intent = new Intent(this, otp.class);
        startActivity(intent);
        finish();
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}