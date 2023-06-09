package com.example.bluejackpharmacyv2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.bluejackpharmacyv2.R;
import com.example.bluejackpharmacyv2.fragments.MedicineFragment;
import com.example.bluejackpharmacyv2.fragments.ProfileFragment;
import com.example.bluejackpharmacyv2.fragments.TransactionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImageButton infoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialize();
    }

    private void initialize(){
        String email = getIntent().getStringExtra("email");
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        infoButton = findViewById(R.id.info_button);

        setListener(email);
    }

    @SuppressLint("NonConstantResourceId")
    private void setListener(String email){
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_medicine:
                    MedicineFragment medicineFragment = new MedicineFragment();
                    replaceFragment(medicineFragment, email);
                    return true;
                case R.id.navigation_transaction:
                    TransactionFragment transactionFragment = new TransactionFragment();
                    replaceFragment(transactionFragment, email);
                    return true;
                case R.id.navigation_profile:
                    ProfileFragment profileFragment = new ProfileFragment();
                    replaceFragment(profileFragment, email);
                    return true;
            }

            return false;
        });
        bottomNavigationView.setSelectedItemId(R.id.navigation_medicine);

        infoButton.setForeground(ContextCompat.getDrawable(this, R.drawable.clicked_blue));
        infoButton.setOnClickListener(v -> startAboutUs());
    }

    private void replaceFragment(Fragment fragment, String email){
        Bundle args = new Bundle();
        args.putString("email", email);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void startAboutUs(){

    }
}