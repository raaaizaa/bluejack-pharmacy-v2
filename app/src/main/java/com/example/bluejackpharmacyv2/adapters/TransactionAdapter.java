package com.example.bluejackpharmacyv2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluejackpharmacyv2.R;
import com.example.bluejackpharmacyv2.models.Transaction;
import com.example.bluejackpharmacyv2.utils.MedicineDatabaseHelper;
import com.example.bluejackpharmacyv2.utils.TransactionDatabaseHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private TransactionDatabaseHelper transactionDb;
    private MedicineDatabaseHelper medicineDb;
    private final Context context;
    private final List<Transaction> transactions;
    private final String email;

    public TransactionAdapter(List<Transaction> transactions, Context context, String email) {
        this.transactions = transactions;
        this.context = context;
        this.email = email;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView transactionIdTextview, transactionDateTextview, medicineNameTextview, manufacturerTextview, priceTextview, quantityTextview;
        private final ImageButton updateButton, deleteButton;
        private final EditText editQuantityField;
        private final ImageView medicineImage;

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
        medicineDb = new MedicineDatabaseHelper(context);
        transactionDb = new TransactionDatabaseHelper(context, medicineDb);

        holder.transactionIdTextview.setText(transaction.getTransactionId().toString());
        Picasso.get().load(transaction.getMedicineImage()).into(holder.medicineImage);
        holder.medicineNameTextview.setText(transaction.getMedicineName());
        holder.transactionDateTextview.setText(String.valueOf(transaction.getTransactionDate()));
        holder.manufacturerTextview.setText(transaction.getManufacturer());
        holder.priceTextview.setText(String.valueOf(transaction.getPrice() * transaction.getQuantity()));
        holder.quantityTextview.setText(transaction.getQuantity().toString());

        holder.itemView.setOnClickListener(e -> {
            // Cuman biar ripple effectnya jalan doang
        });

        holder.deleteButton.setOnClickListener(e -> {
            Integer transactionId = transactions.get(holder.getAdapterPosition()).getTransactionId();
            transactionDb.dropTransaction(transactionId);

            transactions.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
        });

        holder.updateButton.setOnClickListener(e ->{
            if(holder.quantityTextview.getVisibility() == View.VISIBLE){
                showEditQuantityField(holder);
            }else{
                updateTransactionQuantity(holder);
            }
        });
    }

    private void showEditQuantityField(ViewHolder holder) {
        holder.editQuantityField.setText(holder.quantityTextview.getText().toString());
        holder.quantityTextview.setVisibility(View.GONE);
        holder.editQuantityField.setVisibility(View.VISIBLE);
        holder.editQuantityField.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(holder.editQuantityField, InputMethodManager.SHOW_IMPLICIT);
    }

    private void updateTransactionQuantity(ViewHolder holder) {
        holder.quantityTextview.setText(holder.editQuantityField.getText().toString());
        Integer transactionId = transactions.get(holder.getAdapterPosition()).getTransactionId();
        String newQuantityInput = holder.editQuantityField.getText().toString().trim();

        if (newQuantityInput.isEmpty()) {
            showToast("Quantity must be filled!");
        } else {
            Integer newQuantity = Integer.parseInt(newQuantityInput);

            if (newQuantity.equals(0)) {
                showToast("You must input a quantity greater than 0!");
            } else {
                transactionDb.updateTransaction(transactionId, newQuantity);
                transactions.get(holder.getAdapterPosition()).setQuantity(newQuantity);

                holder.editQuantityField.setVisibility(View.GONE);
                holder.quantityTextview.setVisibility(View.VISIBLE);

                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(holder.editQuantityField.getWindowToken(), 0);
                notifyItemChanged(holder.getAdapterPosition());
                showToast("Success editing quantity!");
            }
        }
    }


    @Override
    public int getItemCount() {
        return transactions.size();
    }

    private void showToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
