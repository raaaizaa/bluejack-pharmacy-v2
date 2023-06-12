package com.example.bluejackpharmacyv2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.example.bluejackpharmacyv2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap map;
    private final float ZOOM_LEVEL = 15.0f;
    private final double LAT = -6.20201, LONG = 106.78113;
    private final String MARKER_NAME = "Bluejack Pharmacy";
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private void initialize(){
        backButton = findViewById(R.id.back_button);

        setListener();
    }

    private void setListener(){
        backButton.setOnClickListener(e -> finish());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap){
        map = googleMap;

        LatLng latlng = new LatLng(LAT, LONG);
        map.addMarker(new MarkerOptions().position(latlng).title(MARKER_NAME));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, ZOOM_LEVEL));
        Log.i("map", "onMapReady: Success!");
        Log.i("map", "onMapReady: LAT: " + LAT + " LONG: " + LONG);
    }
}