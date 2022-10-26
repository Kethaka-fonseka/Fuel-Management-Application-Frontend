package com.example.fuel_management.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.IOException;
/**
 * This class cOperations of SQLLITE database .
 *
 * @version 1.0
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //Initialize variables
    public static final String DATABASE_NAME = "FUEL_MANAGEMENT";
    public static final String USER_TABLE_NAME = "USER_TABLE";
    public static final String USER_TABLE_COLUMN_ID = "ID";
    public static final String USER_TABLE_COLUMN_USERNAME = "USER_NAME";
    public static final String USER_TABLE_COLUMN_PASSWORD = "USER_PASSWORD";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //This is called when first time database is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String createStatement = "create table "+ USER_TABLE_NAME + " (" + USER_TABLE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_TABLE_COLUMN_USERNAME + ", " + USER_TABLE_COLUMN_PASSWORD + " )";
            db.execSQL(createStatement);
        }catch (SQLException exception){
            try {
                 throw  new IOException();
            }catch (IOException ioException){
                exception.printStackTrace();
            }
        }
    }

    //This will called when database data is changed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
        onCreate(db);
    }

    //Insert operation to the local sqlite database
    public boolean insert(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_TABLE_COLUMN_USERNAME, username);
        contentValues.put(USER_TABLE_COLUMN_PASSWORD, password);
         long insert=  db.insert(USER_TABLE_NAME, null, contentValues);

        if( insert == -1) {
            return  false;
        }

        return true;
    }
}
