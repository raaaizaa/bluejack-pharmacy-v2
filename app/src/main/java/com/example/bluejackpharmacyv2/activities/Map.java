package com.example.bluejackpharmacyv2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.bluejackpharmacyv2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap gMap;
    private double LAT = -6.20201, LONG = 106.78113;
    private float ZOOM_LEVEL = 15.0f;
    private ImageButton backButton;
    private String MARKER_NAME = "Bluejack Pharmacy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        supportMapFragment.getMapAsync(this);

        initialize();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        LatLng latLng = new LatLng(LAT, LONG);
        gMap.addMarker(new MarkerOptions().position(latLng).title(MARKER_NAME));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_LEVEL));
    }

    public void initialize(){
        backButton = findViewById(R.id.back_button);

        setListener();
    }

    public void setListener(){
        backButton.setOnClickListener(e -> {
            finish();
        });
    }
}