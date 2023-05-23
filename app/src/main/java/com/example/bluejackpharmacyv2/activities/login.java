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
        boolean userExists = userDb.checkEmail(email, password);

        if(userExists){
            Log.i("login", "validate: user exists!");
            return true;
        }else{
            Log.i("login", "validate: user does not exists!");
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
            String inputtedEmail = emailField.getText().toString();
            String inputtedPassword = passwordField.getText().toString();
            boolean loginIsValid = loginValidation(inputtedEmail, inputtedPassword);

            if(inputtedEmail.isEmpty() || inputtedPassword.isEmpty()){
                showToast("All fields must be filled!");
            }else{
                if(loginIsValid){
                    showToast("Login Success!");
                }else{
                    showToast("Invalid User!");
                }
            }

        });

        goToRegisterButton.setOnClickListener(e -> {
            Intent intent = new Intent(this, register.class);
            startActivity(intent);
        });
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}