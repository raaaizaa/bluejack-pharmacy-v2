package com.example.bluejackpharmacyv2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluejackpharmacyv2.R;
import com.example.bluejackpharmacyv2.models.Transaction;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private final List<Transaction> transactions;
    private final Context context;
    private String email;

    public TransactionAdapter(List<Transaction> transactions, Context context, String email) {
        this.transactions = transactions;
        this.context = context;
        this.email = email;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView medicineImage;
        private TextView transactionIdTextview, transactionDateTextview, medicineNameTextview, manufacturerTextview, priceTextview, quantityTextview;
        private EditText editQuantityField;
        private Button updateButton, deleteButton;

        public ViewHolder(View view) {
            super(view);
            transactionIdTextview = view.findViewById(R.id.id_transaction);
            medicineImage = view.findViewById(R.id.medicine_image_transaction);
            transactionDateTextview = view.findViewById(R.id.date_transaction);
            medicineNameTextview = view.findViewById(R.id.medicine_name_transaction);
            manufacturerTextview = view.findViewById(R.id.medicine_manufacturer_transaction);
            priceTextview = view.findViewById(R.id.medicine_price_transaction);
            quantityTextview = view.findViewById(R.id.medicine_quantity_transaction);
            editQuantityField = view.findViewById(R.id.edit_quantity_transaction);
            updateButton = view.findViewById(R.id.update_button_transaction);
            deleteButton = view.findViewById(R.id.delete_button_transaction);
        }
    }


    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_card, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.transactionIdTextview.setText(transaction.getTransactionId().toString());
        Picasso.get().load(transaction.getMedicineImage()).into(holder.medicineImage);
        holder.medicineNameTextview.setText(transaction.getMedicineName());
        holder.transactionDateTextview.setText(String.valueOf(transaction.getTransactionDate()));
        holder.manufacturerTextview.setText(transaction.getManufacturer());
        holder.priceTextview.setText(String.valueOf(transaction.getPrice() * transaction.getQuantity()));
        holder.quantityTextview.setText(transaction.getQuantity().toString());
    }


    @Override
    public int getItemCount() {
        return transactions.size();
    }
}
