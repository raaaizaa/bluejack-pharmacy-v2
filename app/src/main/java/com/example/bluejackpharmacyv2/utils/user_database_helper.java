package com.example.bluejackpharmacyv2.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class user_database_helper extends SQLiteOpenHelper {

    public static final String USER_DB = "User.db";

    public user_database_helper(Context context){
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

    public boolean insertUser(String name, String email, String password, String phone, String verified){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean userExists = checkUser(name, password);

        if(userExists){
            Log.i("userDbHelper", "insertUser: User Already Exists!");
            return false;
        }else {
            Integer userId = generateUserId();
            ContentValues contentValues = inputContent(userId, name, email, password, phone, verified);

            long results = db.insert("user", null, contentValues);

            Log.i("userDbHelper", "insertUser: Inserting User Success!");
            Log.i("userDbHelper", "userId: " + userId.toString() + " name: " + name + " email: " + email + " password: " + password + " phone: " + phone + " verified: " + verified);

            db.close();
            return results != -1;
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

    public Integer generateUserId(){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectMax = "SELECT MAX(userId) FROM user";
        Cursor cursor = db.rawQuery(selectMax, null);
        Integer latestUserId = 0;

        if(cursor.moveToFirst()){
            latestUserId = cursor.getInt(0);
        }

        Log.i("userDbHelper", "generateUserId: latestUserId is = " + latestUserId);
        Integer newUserId = latestUserId + 1;
        cursor.close();

        Log.i("userDbHelper", "generateUserId: new generated ID is = " + newUserId);
        return newUserId;
    }

    private ContentValues inputContent(Integer userId, String name, String email, String password, String phone, String verified){
        ContentValues contentValues = new ContentValues();

        contentValues.put("userId", userId);
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("phone", phone);
        contentValues.put("userId", userId);
        contentValues.put("verified", verified);

        return contentValues;
    }
}
