package com.example.bluejackpharmacyv2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bluejackpharmacyv2.R;

public class register extends AppCompatActivity {

    private EditText nameField, emailField, passwordField, confirmPassField, phoneNumberField;
    private Button registerButton, goToLoginButton;

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
            String inputtedName = nameField.getText().toString();
            String inputtedEmail = emailField.getText().toString();
            String inputtedPassword = passwordField.getText().toString();
            String inputtedConfirmPass = confirmPassField.getText().toString();
            String inputtedPhoneNum = phoneNumberField.getText().toString();

            if(isAnyInputEmpty(inputtedEmail, inputtedEmail, inputtedPassword, inputtedConfirmPass, inputtedPhoneNum)){
                showToast("All fields must be filled!");
            }else if(inputtedName.length() <= 5){
                showToast("Name must be at least 5 characters!");
            } // tar dulu ah cape :v
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

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}