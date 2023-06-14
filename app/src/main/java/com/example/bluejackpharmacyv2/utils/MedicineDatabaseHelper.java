package com.example.bluejackpharmacyv2.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bluejackpharmacyv2.models.Medicine;

public class MedicineDatabaseHelper extends SQLiteOpenHelper {
    public static final String MEDICINE_DB = "Medicine.db";

    public MedicineDatabaseHelper(Context context){
        super(context, MEDICINE_DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE medicine(medicineId INTEGER PRIMARY KEY, medicineName VARCHAR(50), manufacturer VARCHAR(50), price INTEGER, image VARCHAR(50), description VARCHAR(255))";
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropQuery = "DROP TABLE IF EXISTS medicine";
        db.execSQL(dropQuery);
    }

    public void insertMedicine(String medicineName, String manufacturer, Integer price, String image, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean medicineExists = checkMedicine(medicineName, manufacturer);

        if (!medicineExists) {
            Integer medicineId = generateMedicineId();
            ContentValues contentValues = inputContent(medicineId, medicineName, manufacturer, price, image, description);

            long result = db.insert("medicine", null, contentValues);
            db.close();

            if (result == -1) {
                Log.e("Insertion Error", "Failed to insert medicine");
            } else {
                Log.d("Insertion Success", "Medicine inserted successfully");
            }
        } else {
            Log.d("Medicine Exists", "Medicine already exists");
        }
    }


    public boolean checkMedicine(String medicineName, String manufacturer){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM medicine WHERE medicineName = ? AND manufacturer = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{medicineName, manufacturer});

        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public Integer generateMedicineId(){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectMax = "SELECT MAX(medicineId) FROM medicine";
        Cursor cursor = db.rawQuery(selectMax, null);
        Integer latestMedicineId = 2023001;

        if(cursor.moveToFirst()){
            latestMedicineId = cursor.getInt(0);
        }

        Integer newMedicineId = latestMedicineId + 1;
        cursor.close();

        return newMedicineId;
    }

    @SuppressLint("Range")
    public Integer getMedicineId(String medicineName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT medicineId FROM medicine WHERE medicineName = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{medicineName});

        Integer medicineId = null;

        if (cursor.moveToFirst()) {
            medicineId = cursor.getInt(cursor.getColumnIndex("medicineId"));
        }

        cursor.close();

        return medicineId;
    }

    @SuppressLint("Range")
    public String getMedicineName(Integer medicineId){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT medicineName FROM medicine WHERE medicineId = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(medicineId)});

        String medicineName = "";

        if(cursor.moveToFirst()){
            medicineName = cursor.getString(cursor.getColumnIndex("medicineName"));
        }

        cursor.close();

        return medicineName;
    }

    @SuppressLint("Range")
    public String getManufacturer(Integer medicineId){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT manufacturer FROM medicine WHERE medicineId = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(medicineId)});

        String manufacturer = "";

        if(cursor.moveToFirst()){
            manufacturer = cursor.getString(cursor.getColumnIndex("manufacturer"));
        }

        cursor.close();

        return manufacturer;
    }

    @SuppressLint("Range")
    public Medicine getMedicineById(int medicineId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM medicine WHERE medicineId = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(medicineId)});

        Medicine medicine = null;

        if (cursor.moveToFirst()) {
            String medicineName = cursor.getString(cursor.getColumnIndex("medicineName"));
            String manufacturer = cursor.getString(cursor.getColumnIndex("manufacturer"));
            int price = cursor.getInt(cursor.getColumnIndex("price"));
            String image = cursor.getString(cursor.getColumnIndex("image"));
            String description = cursor.getString(cursor.getColumnIndex("description"));

            medicine = new Medicine(medicineId, price, medicineName, manufacturer, image, description);
        }

        cursor.close();

        return medicine;
    }


    private ContentValues inputContent(Integer medicineId, String medicineName, String manufacturer, Integer price, String image, String description) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("medicineId", medicineId);
        contentValues.put("medicineName", medicineName);
        contentValues.put("manufacturer", manufacturer);
        contentValues.put("price", price);
        contentValues.put("image", image);
        contentValues.put("description", description);

        return contentValues;
    }
}
