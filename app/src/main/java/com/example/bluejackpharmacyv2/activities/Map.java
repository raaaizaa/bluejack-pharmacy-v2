package com.example.bluejackpharmacyv2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);
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