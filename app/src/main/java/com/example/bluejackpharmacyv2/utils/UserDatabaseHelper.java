package com.example.bluejackpharmacyv2.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bluejackpharmacyv2.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    public static final String USER_DB = "User.db";
    private List<User> users;

    public UserDatabaseHelper(Context context){
        super(context, USER_DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE user(userId INTEGER PRIMARY KEY, name VARCHAR(50), email VARCHAR(50),password VARCHAR(20), phone VARCHAR(20), verified VARCHAR(10))";
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropQuery = "DROP TABLE IF EXISTS user";
        db.execSQL(dropQuery);
    }

    public void insertUser(String name, String email, String password, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean userExists = checkUser(name, password);

        if(!userExists){
            Integer userId = generateUserId();
            ContentValues contentValues = inputContent(userId, name, email, password, phone);

            long results = db.insert("user", null, contentValues);

            db.close();
            Log.d("Insertion Success", "User inserted successfully");
        }
    }

    public boolean checkUser(String name, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM user WHERE name = ? AND password = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{name, password});

        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public boolean loginCheck(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM user WHERE email = ? AND password = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email, password});

        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public Integer generateUserId(){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectMax = "SELECT MAX(userId) FROM user";
        Cursor cursor = db.rawQuery(selectMax, null);
        Integer latestUserId = 0;

        if(cursor.moveToFirst()){
            latestUserId = cursor.getInt(0);
        }

        Integer newUserId = latestUserId + 1;
        cursor.close();

        return newUserId;
    }

    public boolean checkUsername(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM user WHERE name = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{name});

        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public boolean checkEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM user WHERE email = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email});

        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public boolean checkPhoneNumber(String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM user WHERE phone = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{phone});

        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    @SuppressLint("Range")
    public boolean checkVerified(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT verified FROM user WHERE email = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email});

        boolean result = false;
        if (cursor.moveToFirst()) {
        String value = cursor.getString(cursor.getColumnIndex("verified"));
            result = value.equals("verified");
        }

        cursor.close();
        return result;
    }

    @SuppressLint("Range")
    public String getName(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT name FROM user WHERE email = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email});

        String name = null;
        if(cursor.moveToFirst()){
            name = cursor.getString(cursor.getColumnIndex("name"));
        }

        cursor.close();
        return name;
    }

    @SuppressLint("Range")
    public String getPhoneNumber(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT phone FROM user WHERE email = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email});

        String phoneNumber = null;
        if (cursor.moveToFirst()) {
            phoneNumber = cursor.getString(cursor.getColumnIndex("phone"));
        }
        cursor.close();
        return phoneNumber;
    }

    public void verificationCompleted(String email, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("verified", message);

        int rowsAffected = db.update("user", values, "email = ?", new String[]{email});
        db.close();

        if (rowsAffected > 0) {
            Log.d("Update Success", "Verification completed successfully");
        } else {
            Log.e("Update Error", "Failed to complete verification");
        }
    }

    public void insertDummyUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean userExists = checkUser("dummy", "dummy123");
        List<User> users = new ArrayList<>();

        if(!userExists){
            ContentValues contentValues = new ContentValues();

            contentValues.put("userId", 1);
            contentValues.put("name", "dummy");
            contentValues.put("email", "dummy@gmail.com");
            contentValues.put("password", "dummy123");
            contentValues.put("phone", "+6281314102381");
            contentValues.put("verified", "verified");

            long results = db.insert("user", null, contentValues);

            User user = new User(1, "dummy", "dummy@gmail.com", "dummy123", "+6281314102381", "verified");
            users.add(user);

            db.close();
        }
    }

    @SuppressLint("Range")
    public Integer getUserId(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT userId FROM user WHERE email = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email});

        Integer userId = null;

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex("userId"));
        }

        cursor.close();

        return userId;
    }

    public String getVerified(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT verified FROM user WHERE name = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{name});

        String verified = "";

        if (cursor.moveToFirst()) {
            verified = cursor.getString(cursor.getColumnIndexOrThrow("verified"));
        }

        cursor.close();

        return verified;
    }

    private ContentValues inputContent(Integer userId, String name, String email, String password, String phone){
        ContentValues contentValues = new ContentValues();

        contentValues.put("userId", userId);
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("phone", phone);
        contentValues.put("verified", "0");

        return contentValues;
    }
}
