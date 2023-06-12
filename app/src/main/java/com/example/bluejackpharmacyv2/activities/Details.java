package com.example.bluejackpharmacyv2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluejackpharmacyv2.R;
import com.example.bluejackpharmacyv2.utils.TransactionDatabaseHelper;
import com.example.bluejackpharmacyv2.utils.UserDatabaseHelper;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

public class Details extends AppCompatActivity {

    private Integer count = 0;
    private EditText counterField;
    private TextView medicineNameTextview, medicinePriceTextview, manufacturerTextview, medicineDescriptionTextview;
    private ImageView medicineImageview;
    private ImageButton backButton;
    private Button addToCartButton, minButton, plusButton;
    private TransactionDatabaseHelper transactionDb;
    private UserDatabaseHelper userDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initialize();
    }

    private void initialize(){
        medicineNameTextview = findViewById(R.id.medicine_name_detail);
        medicinePriceTextview = findViewById(R.id.medicine_price_detail);
        manufacturerTextview = findViewById(R.id.medicine_manufacturer_detail);
        medicineDescriptionTextview = findViewById(R.id.medicine_description_detail);
        medicineImageview = findViewById(R.id.medicine_image_detail);
        setDetails();

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

            if(counter.equals("0")){
                showToast("Quantity must be more than 0!");
            }else if(counter.isEmpty()){
                showToast("Quantity must be filled!");
            }else{
                String email = getIntent().getStringExtra("userEmail");
                Integer medicineId = Integer.valueOf(getIntent().getStringExtra("medicineId"));

                addToTransaction(email, medicineId, counter);
            }

        });
    }

    private void setDetails(){
        String medicineName = getIntent().getStringExtra("medicineName");
        String manufacturer = getIntent().getStringExtra("manufacturer");
        String medicinePrice = getIntent().getStringExtra("medicinePrice");
        String medicineImage = getIntent().getStringExtra("medicineImage");
        String medicineDescription = getIntent().getStringExtra("medicineDescription");

        medicineNameTextview.setText(medicineName);
        manufacturerTextview.setText(manufacturer);
        medicinePriceTextview.setText(medicinePrice);
        medicineDescriptionTextview.setText(medicineDescription);

        Picasso.get().load(medicineImage).into(medicineImageview);
    }

    private void addToTransaction(String email, Integer medicineId, String counter){
        transactionDb = new TransactionDatabaseHelper(this);
        userDb = new UserDatabaseHelper(this);
        Integer userId = userDb.getUserId(getIntent().getStringExtra(email));
        Integer quantity = Integer.parseInt(counter);

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        transactionDb.newTransaction(medicineId, userId, currentDate, quantity);
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}