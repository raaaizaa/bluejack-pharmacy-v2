package com.example.bluejackpharmacyv2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bluejackpharmacyv2.R;

public class Details extends AppCompatActivity {

    private Integer count = 0;
    private EditText counterField;
    private ImageButton backButton;
    private Button addToCartButton, minButton, plusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initialize();
    }

    private void initialize(){
        backButton = findViewById(R.id.back_button);
        counterField = findViewById(R.id. counter_field);
        addToCartButton = findViewById(R.id.add_to_cart_button);
        minButton = findViewById(R.id.count_min_button);
        plusButton = findViewById(R.id.count_plus_button);

        setListener();
    }

    private void setListener(){
        backButton.setForeground(ContextCompat.getDrawable(this, R.drawable.clicked_blue));

        backButton.setOnClickListener(v -> finish());

        minButton.setOnClickListener(v -> {
            if(count == 0){
                counterField.setText("0");
            }else{
                count--;
                counterField.setText(String.valueOf(count));
            }

        });

        plusButton.setOnClickListener(v -> {
            count++;
            counterField.setText(String.valueOf(count));
        });

        addToCartButton.setOnClickListener(e -> {
            String counter = counterField.getText().toString();
            showToast("Your count = " + counter);
        });
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}