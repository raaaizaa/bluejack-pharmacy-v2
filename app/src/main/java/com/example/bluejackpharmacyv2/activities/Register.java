package com.example.bluejackpharmacyv2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bluejackpharmacyv2.R;
import com.example.bluejackpharmacyv2.models.User;
import com.example.bluejackpharmacyv2.utils.UserDatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private UserDatabaseHelper userDb;
    private EditText nameField, emailField, passwordField, confirmPassField, phoneNumberField;
    private Button registerButton, goToLoginButton;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        users = new ArrayList<>();

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

            if(isAnyInputEmpty(name, email, password, confirmPass, phoneNumber)){
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
                insertUserToDatabase(name, email, password, phoneNumber);
                showToast("Login Success!");
                startLogin();
            }
        });

        goToLoginButton.setOnClickListener(e -> startLogin());
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

    private void insertUserToDatabase(String name, String email, String password, String phoneNumber){
        userDb = new UserDatabaseHelper(this);
        boolean usernameExists = userDb.checkUsername(name);
        boolean emailExists = userDb.checkEmail(email);
        boolean phoneNumberExists = userDb.checkPhoneNumber(phoneNumber);

        if(usernameExists){
            showToast("Username is already exists!");
        }else if(emailExists){
            showToast("Email is already registered!");
        }else if(phoneNumberExists){
            showToast("Phone Number is already registered!");
        }else{
            if(phoneNumber.contains("+62")){
                userDb.insertUser(name, email, password, phoneNumber);
                User user = new User(userDb.getUserId(email), name, email, password, phoneNumber, userDb.getVerified(name));
                users.add(user);
            }else{
                String countryCode = "+62";
                String fixedPhoneNumber = countryCode.concat(phoneNumber);

                userDb.insertUser(name, email, password, fixedPhoneNumber);
                User user = new User(userDb.getUserId(email), name, email, password, fixedPhoneNumber, userDb.getVerified(name));
                Log.i("ASD", userDb.getUserId(email) + " " + name + " " + email + " " + password + " " + fixedPhoneNumber + " " + userDb.getVerified(name));
                users.add(user);
            }
        }
    }

    private void startLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}