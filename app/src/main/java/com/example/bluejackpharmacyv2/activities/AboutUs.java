package com.example.bluejackpharmacyv2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.bluejackpharmacyv2.R;

public class AboutUs extends AppCompatActivity {
    private Button mapButton;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        initialize();
    }

    private void initialize(){
        mapButton = findViewById(R.id.map_button);
        backButton = findViewById(R.id.back_button);

        setListener();
    }

    private void setListener(){
        mapButton.setOnClickListener(e -> {
            Intent intent = new Intent(this, Map.class);
            startActivity(intent);
        });

        backButton.setForeground(ContextCompat.getDrawable(this, R.drawable.clicked_blue));
        backButton.setOnClickListener(e -> finish());
    }
}