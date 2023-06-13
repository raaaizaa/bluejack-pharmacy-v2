package com.example.bluejackpharmacyv2.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bluejackpharmacyv2.R;
import com.example.bluejackpharmacyv2.adapters.TransactionAdapter;
import com.example.bluejackpharmacyv2.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionFragment extends Fragment {

    private Context context;
    private List<Transaction> transactions;
    private TransactionAdapter adapter;
    private RecyclerView transactionRV;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        this.context = getContext();
        String email = "";

        transactionRV = view.findViewById(R.id.transaction_recyclerview);
        transactions = new ArrayList<>();
        setRecyclerview(transactions, context, email);

        return view;
    }

    public void setRecyclerview(List transactions, Context context, String email){

        adapter = new TransactionAdapter(transactions, context, email);
        transactionRV.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        transactionRV.setLayoutManager(layoutManager);
    }
}