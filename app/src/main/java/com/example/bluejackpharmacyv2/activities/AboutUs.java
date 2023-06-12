package com.example.bluejackpharmacyv2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.bluejackpharmacyv2.R;
import com.example.bluejackpharmacyv2.utils.UserDatabaseHelper;

public class AboutUs extends AppCompatActivity {

    private Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        initialize();
        setListener();
    }

    private void initialize(){
        mapButton = findViewById(R.id.map_button);
    }

    private void setListener(){
        mapButton.setOnClickListener(e -> {
            Intent intent = new Intent(this, Map.class);
            startActivity(intent);
        });
    }
}