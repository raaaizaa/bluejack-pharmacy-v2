package com.example.bluejackpharmacyv2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bluejackpharmacyv2.R;
import com.example.bluejackpharmacyv2.utils.user_database_helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class register extends AppCompatActivity {

    private EditText nameField, emailField, passwordField, confirmPassField, phoneNumberField;
    private Button registerButton, goToLoginButton;
    user_database_helper userDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialize();
    }

    private void initialize(){
        nameField = findViewById(R.id.register_name_field);
        emailField = findViewById(R.id.register_email_field);
        passwordField = findViewById(R.id.register_password_field);
        confirmPassField = findViewById(R.id.register_confirm_password_field);
        phoneNumberField = findViewById(R.id.register_phone_number_field);

        registerButton = findViewById(R.id.register_button);
        goToLoginButton = findViewById(R.id.go_to_login_button);

        setListener();
    }

    private void setListener(){
        registerButton.setOnClickListener(e -> {
            String name = nameField.getText().toString();
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();
            String confirmPass = confirmPassField.getText().toString();
            String phoneNumber = phoneNumberField.getText().toString();

            if(isAnyInputEmpty(email, email, password, confirmPass, phoneNumber)){
                showToast("All fields must be filled!");
            }else if(!isUsernameLengthValid(name)){
                showToast("Name must be at least 5 characters!");
            }else if(!isEmailValid(email)){
                showToast("Email is not valid!");
            }else if(!isPasswordAlphanumeric(password)){
                showToast("Password must be alphanumeric!");
            }else if(!isConfirmPasswordSameWithPassword(password, confirmPass)){
                showToast("Password doesn't match!");
            }else if(!isPhoneNumberValid(phoneNumber)){
                showToast("Phone Number is not valid!");
            }else{
                if(insertUserToDatabase(name, email, password, phoneNumber)){
                    showToast("Login Success!");
                    goToLogin();
                }
            }
        });

        goToLoginButton.setOnClickListener(e -> {
            goToLogin();
        });
    }

    private boolean isAnyInputEmpty(String... inputs) {
        for (String input : inputs) {
            if (TextUtils.isEmpty(input)) {
                return true;
            }
        }
        return false;
    }

    private boolean isEmailValid(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordAlphanumeric(String password){
        String regex = "^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);

        return m.matches();
    }

    private boolean isUsernameLengthValid(String name){
        return name.length() >= 5;
    }

    private boolean isConfirmPasswordSameWithPassword(String password, String confirmPassword){
        return confirmPassword.equals(password);
    }

    private boolean isPhoneNumberValid(String phoneNumber){
        return PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber);
    }

    private boolean insertUserToDatabase(String name, String email, String password, String phoneNumber){
        userDb = new user_database_helper(this);
        boolean usernameExists = userDb.checkUsername(name);
        boolean emailExists = userDb.checkEmail(email);
        boolean phoneNumberExists = userDb.checkPhoneNumber(phoneNumber);

        if(usernameExists){
            showToast("Username is already exists!");
            return false;
        }else if(emailExists){
            showToast("Email is already registered!");
            return false;
        }else if(phoneNumberExists){
            showToast("Phone Number is already registered!");
            return false;
        }else{
            userDb.insertUser(name, email, password, phoneNumber);
            return true;
        }
    }

    private void goToLogin(){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}