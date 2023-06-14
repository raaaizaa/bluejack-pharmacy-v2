package com.example.bluejackpharmacyv2.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bluejackpharmacyv2.models.Medicine;
import com.example.bluejackpharmacyv2.models.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionDatabaseHelper extends SQLiteOpenHelper {
    private final MedicineDatabaseHelper medicineDb;
    public static final String TRANSACTION_DB = "Transaction.db";

    public TransactionDatabaseHelper(Context context, MedicineDatabaseHelper medicineDb){
        super(context, TRANSACTION_DB, null, 1);
        this.medicineDb = medicineDb;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE transactions(transactionId INTEGER PRIMARY KEY, medicineId INTEGER, userId INTEGER, transactionDate DATE, quantity INTEGER, FOREIGN KEY (medicineId) REFERENCES medicine(medicineId), FOREIGN KEY (userId) REFERENCES user(userId))";
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropQuery = "DROP TABLE IF EXISTS transactions";
        db.execSQL(dropQuery);
    }

    public void newTransaction(Integer medicineId, Integer userId, Date transactionDate, Integer quantity){
        SQLiteDatabase db = this.getWritableDatabase();

        Integer transactionId = generateTransactionId();
        ContentValues contentValues = inputContent(transactionId, medicineId, userId, transactionDate, quantity);

        long results = db.insert("transactions", null, contentValues);
        db.close();

        Log.d("Insertion Success", "Transaction inserted successfully");
    }

    public Integer generateTransactionId() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectMax = "SELECT MAX(transactionId) FROM transactions";
        Cursor cursor = db.rawQuery(selectMax, null);
        Integer latestTransactionId = null;

        if (cursor.moveToFirst()) {
            Integer columnIndex = cursor.getColumnIndex("MAX(transactionId)");
            if (!cursor.isNull(columnIndex)) {
                latestTransactionId = cursor.getInt(columnIndex);
            }
        }

        cursor.close();

        if (latestTransactionId == null) {
            latestTransactionId = 2023000;
        }

        return latestTransactionId + 1;
    }

    @SuppressLint("Range")
    public List<Transaction> getAllTransactions(Integer userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectTransactionQuery = "SELECT * FROM transactions WHERE userId = ?";
        Cursor transactionCursor = db.rawQuery(selectTransactionQuery, new String[]{String.valueOf(userId)});

        List<Transaction> transactions = new ArrayList<>();

        if (transactionCursor.moveToFirst()) {
            do {
                String transactionDateString = transactionCursor.getString(transactionCursor.getColumnIndex("transactionDate"));
                Date transactionDate = null;
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault());
                    transactionDate = dateFormat.parse(transactionDateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                int transactionId = transactionCursor.getInt(transactionCursor.getColumnIndex("transactionId"));
                int medicineId = transactionCursor.getInt(transactionCursor.getColumnIndex("medicineId"));
                int quantity = transactionCursor.getInt(transactionCursor.getColumnIndex("quantity"));

                // Use the medicineDbHelper to query the medicine table
                Medicine medicine = medicineDb.getMedicineById(medicineId);

                if (medicine != null) {
                    String medicineImage = medicine.getImage();
                    String medicineName = medicine.getMedicineName();
                    String manufacturer = medicine.getManufacturer();
                    int medicinePrice = medicine.getPrice();

                    Transaction transaction = new Transaction(medicineImage, medicineName, manufacturer, transactionId, medicinePrice, medicineId, userId, quantity, transactionDate);
                    transactions.add(transaction);
                }
            } while (transactionCursor.moveToNext());
        }

        transactionCursor.close();

        return transactions;
    }


    @SuppressLint("Range")
    public Integer getTransactionId(Integer medicineId, Integer userId, Date transactionDate){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT transactionId FROM transactions WHERE medicineId = ? AND userId = ? OR transactionDate = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(medicineId), String.valueOf(userId), String.valueOf(transactionDate)});

        Integer transactionId = null;

        if(cursor.moveToFirst()){
            transactionId = cursor.getInt(cursor.getColumnIndex("transactionId"));
        }

        cursor.close();

        return transactionId;
    }

    @SuppressLint("Range")
    public Integer getMedicineId(Integer userId, Date transactionDate){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT medicineId FROM transactions WHERE userId = ? AND transactionDate = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userId), String.valueOf(transactionDate)});

        Integer medicineId = null;

        if(cursor.moveToFirst()){
            medicineId = cursor.getInt(cursor.getColumnIndex("medicineId"));
        }

        cursor.close();

        return medicineId;
    }

    @SuppressLint("Range")
    public Integer getTotalPrice(Integer userId, Integer medicineId, Date transactionDate, Integer price){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT quantity FROM transactions WHERE (userId = ? AND medicineId = ?) AND transactionDate = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userId), String.valueOf(medicineId), String.valueOf(transactionDate)});

        Integer totalPrice = null;

        if(cursor.moveToFirst()){
            totalPrice = cursor.getInt(cursor.getColumnIndex("quantity")) * price;
        }

        cursor.close();

        return totalPrice;
    }

    public void dropTransaction(Integer transactionId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("transactions", "transactionId = ?", new String[]{String.valueOf(transactionId)});
    }

    public void updateTransaction(Integer transactionId, Integer newQuantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("quantity", newQuantity);
        db.update("transactions", contentValues,"transactionId = ?", new String[]{String.valueOf(transactionId)});
    }

    private ContentValues inputContent(Integer transactionId, Integer medicineId, Integer userId, Date transactionDate, Integer quantity) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("transactionId", transactionId);
        contentValues.put("medicineId", medicineId);
        contentValues.put("userId", userId);
        contentValues.put("transactionDate", String.valueOf(transactionDate));
        contentValues.put("quantity", quantity);

        return contentValues;
    }
}
