package com.example.bluejackpharmacyv2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.bluejackpharmacyv2.R;

public class otp extends AppCompatActivity {

    private EditText otpField;
    private Button enterOtpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        initialize();
    }

    private void initialize(){
        otpField = findViewById(R.id.otp_field);
        enterOtpButton = findViewById(R.id.enter_otp_button);

        setListener();
    }

    private void setListener(){
        enterOtpButton.setOnClickListener(e -> {
            String otpCode = otpField.getText().toString();
        });
    }
}