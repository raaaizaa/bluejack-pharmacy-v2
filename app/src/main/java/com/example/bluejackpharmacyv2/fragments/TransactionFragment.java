package com.example.bluejackpharmacyv2.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.bluejackpharmacyv2.R;
import com.example.bluejackpharmacyv2.adapters.TransactionAdapter;
import com.example.bluejackpharmacyv2.models.Transaction;
import com.example.bluejackpharmacyv2.utils.MedicineDatabaseHelper;
import com.example.bluejackpharmacyv2.utils.TransactionDatabaseHelper;
import com.example.bluejackpharmacyv2.utils.UserDatabaseHelper;

import java.util.List;

public class TransactionFragment extends Fragment {
    private Context context;
    private TransactionDatabaseHelper transactionsDb;
    private MedicineDatabaseHelper medicineDb;
    private UserDatabaseHelper userDb;
    private TransactionAdapter adapter;
    private RecyclerView transactionRV;
    private String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.context = getContext();

        Bundle args = getArguments();
        if(args != null){
            email = args.getString("email");
        }

        transactionRV = view.findViewById(R.id.transaction_recyclerview);
        getTransactions(email);
        setRecyclerview(getTransactions(email), context, email);

        return view;
    }

    private List getTransactions(String email){
        medicineDb = new MedicineDatabaseHelper(context);
        transactionsDb = new TransactionDatabaseHelper(context, medicineDb);
        userDb = new UserDatabaseHelper(context);

        Integer userId = userDb.getUserId(email);

        return transactionsDb.getAllTransactions(userId);
    }

    public void setRecyclerview(List transactions, Context context, String email){

        adapter = new TransactionAdapter(transactions, context, email);
        transactionRV.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        transactionRV.setLayoutManager(layoutManager);
    }
}