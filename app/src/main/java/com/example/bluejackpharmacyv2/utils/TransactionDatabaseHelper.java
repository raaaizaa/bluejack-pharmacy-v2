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

    public boolean newTransaction(Integer medicineId, Integer userId, Date transactionDate, Integer quantity){
        SQLiteDatabase db = this.getWritableDatabase();

        Integer transactionId = generateTransactionId();
        ContentValues contentValues = inputContent(transactionId, medicineId, userId, transactionDate, quantity);

        long results = db.insert("transactions", null, contentValues);
        Log.i("transactionDbHelper", "newTransaction: Creating new transaction success!");
        Log.i("transactionDbHelper", "transactionId: " + transactionId + " userId: " + userId + " transactionDate: " + transactionDate + " quantity: " + quantity);

        db.close();
        return results != -1;
    }

    public Integer generateTransactionId() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectMax = "SELECT MAX(transactionId) FROM transactions WHERE userId = ?";
        Cursor cursor = db.rawQuery(selectMax, null);
        Integer latestTransactionId = 0;

        if(cursor.moveToFirst()){
            latestTransactionId = cursor.getInt(0);
        }

        Log.i("transactionDbHelper", "generateTransactionId: latestTransactionId is = " + latestTransactionId);
        Integer newTransactionId = latestTransactionId + 1;
        cursor.close();

        Log.i("transactionDbHelper", "generateTransactionId: new generated ID is = " + newTransactionId);
        return newTransactionId;
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
