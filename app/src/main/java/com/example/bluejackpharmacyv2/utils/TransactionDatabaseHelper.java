package com.example.bluejackpharmacyv2.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

public class TransactionDatabaseHelper extends SQLiteOpenHelper {

    public static final String TRANSACTION_DB = "Transaction.db";

    public TransactionDatabaseHelper(Context context){
        super(context, TRANSACTION_DB, null, 1);
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
        Log.i("transactionDbHelper", "newTransaction: Creating new transaction success!");
        Log.i("transactionDbHelper", "transactionId: " + transactionId + " userId: " + userId + " transactionDate: " + transactionDate + " quantity: " + quantity);

        db.close();
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

        Log.i("transactionDbHelper", "generateTransactionId: latestTransactionId is = " + latestTransactionId);
        Integer newTransactionId = latestTransactionId + 1;

        Log.i("transactionDbHelper", "generateTransactionId: new generated ID is = " + newTransactionId);
        return newTransactionId;
    }

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

    public Integer getTotalPrice(Integer userId, Integer medicineId, Date transactionDate, Integer price){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT quantity FROM transactions WHERE userId = ? AND medicineId = ? OR transactionDate = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userId), String.valueOf(medicineId), String.valueOf(transactionDate)});

        Integer totalPrice = null;

        if(cursor.moveToFirst()){
            totalPrice = cursor.getInt(cursor.getColumnIndex("quantity")) * price;
        }

        cursor.close();

        return totalPrice;
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
