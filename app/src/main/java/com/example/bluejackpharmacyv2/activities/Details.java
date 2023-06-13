package com.example.bluejackpharmacyv2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluejackpharmacyv2.R;
import com.example.bluejackpharmacyv2.fragments.TransactionFragment;
import com.example.bluejackpharmacyv2.models.Transaction;
import com.example.bluejackpharmacyv2.utils.MedicineDatabaseHelper;
import com.example.bluejackpharmacyv2.utils.TransactionDatabaseHelper;
import com.example.bluejackpharmacyv2.utils.UserDatabaseHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Details extends AppCompatActivity {

    private Integer count = 0;
    private EditText counterField;
    private TextView medicineNameTextview, medicinePriceTextview, manufacturerTextview, medicineDescriptionTextview;
    private ImageView medicineImageview;
    private ImageButton backButton;
    private Button addToCartButton, minButton, plusButton;
    private TransactionDatabaseHelper transactionDb;
    private UserDatabaseHelper userDb;
    private MedicineDatabaseHelper medicineDb;
    private List<Transaction> transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        transactions = new ArrayList<>();
        String email = getIntent().getStringExtra("userEmail");
        Log.i("userEmail: ", email);

        initialize(email);
    }

    private void initialize(String email){
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

        setListener(email);
    }

    private void setListener(String email){
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
                String price = medicinePriceTextview.getText().toString();
                Integer medicineId = Integer.valueOf(getIntent().getStringExtra("medicineId"));
                String message = "Are you sure you want to buy this item?";

                new AlertDialog.Builder(this).setMessage(message).setPositiveButton("Yes", ((dialog, which) -> {
                    Integer totalPrice = Integer.parseInt(counter) * Integer.parseInt(price);
                    addToTransaction(email, medicineId, String.valueOf(totalPrice));
                    showToast("Success adding item!");
                }))
                        .setNegativeButton("No", null).show();

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
        transactionDb = new TransactionDatabaseHelper(this, medicineDb);
        userDb = new UserDatabaseHelper(this);

        Log.i("Details: addToTransaction", "email: " + email);
        Integer userId = userDb.getUserId(email);
        Log.i("Details: addToTransaction", "userId dari intent = " + userId);
        Integer quantity = Integer.parseInt(counter);
        Integer price = Integer.parseInt((String) medicinePriceTextview.getText());

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        transactionDb.newTransaction(medicineId, userId, currentDate, quantity);
        Transaction transaction = new Transaction(getMedicineImage(userId, currentDate), getManufacturer(medicineId), getMedicineName(medicineId), getTransactionId(medicineId, userId, currentDate), getTotalPrice(userId, medicineId, currentDate, price), medicineId, userId, quantity, currentDate.toString());
        transactions.add(transaction);
        Log.i("jumlah transaction: ", "nih " + String.valueOf(transactions.size()));
    }

    private Integer getTransactionId(Integer medicineId, Integer userId, Date transactionDate){
        transactionDb = new TransactionDatabaseHelper(this, medicineDb);
        Integer transactionId = transactionDb.getTransactionId(medicineId, userId, transactionDate);

        return transactionId;
    }

    private String getMedicineImage(Integer userId, Date transactionDate){
        transactionDb = new TransactionDatabaseHelper(this, medicineDb);
        String medicineImage = String.valueOf(transactionDb.getMedicineId(userId, transactionDate));

        return medicineImage;
    }

    private String getMedicineName(Integer medicineId){
        medicineDb = new MedicineDatabaseHelper(this);
        String medicineName = medicineDb.getMedicineName(medicineId);

        return medicineName;
    }

    private String getManufacturer(Integer medicineId){
        medicineDb = new MedicineDatabaseHelper(this);
        String manufacturer = medicineDb.getManufacturer(medicineId);

        return manufacturer;
    }

    private Integer getTotalPrice(Integer userId, Integer medicineId, Date transactionDate, Integer price){
        transactionDb = new TransactionDatabaseHelper(this, medicineDb);
        Integer totalPrice = transactionDb.getTotalPrice(userId, medicineId, transactionDate, price);

        return totalPrice;
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}