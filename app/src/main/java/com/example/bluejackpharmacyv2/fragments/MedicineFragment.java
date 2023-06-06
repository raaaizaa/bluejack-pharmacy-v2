package com.example.bluejackpharmacyv2.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.bluejackpharmacyv2.R;
import com.example.bluejackpharmacyv2.models.Medicine;

import java.util.List;

public class MedicineFragment extends Fragment {

    private RequestQueue requestQueue;
    private Context context;
    RecyclerView medicineRV;
    List<Medicine> medicines;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_medicine, container, false);
        Context context = getContext();

        initialize();
        return view;
    }

    private void initialize(){
        medicineRV = view.findViewById(R.id.medicines_recyclerview);
    }

    private void setAdapter(){

    }
}