package com.example.bluejackpharmacyv2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bluejackpharmacyv2.R;
import com.example.bluejackpharmacyv2.fragments.MedicineFragment;
import com.example.bluejackpharmacyv2.fragments.ProfileFragment;
import com.example.bluejackpharmacyv2.fragments.TransactionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialize();
    }

    private void initialize(){
        String email = getIntent().getStringExtra("email");
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

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
}