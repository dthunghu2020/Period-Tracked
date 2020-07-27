package com.hungdt.periodtracked.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hungdt.periodtracked.model.Data;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper instance;

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "PeriodTracked.db";

    public static final String TABLE_PERIOD = "TB_PERIOD";
    public static final String COLUMN_ID = "ID_TABLE";
    public static final String COLUMN_DAY = "TITLE";
    public static final String COLUMN_TYPE_DAY = "TYPE_DAY";
    public static final String COLUMN_MOTIONS = "MOTION";
    public static final String COLUMN_SYMPTOMS = "SYMPTOM";
    public static final String COLUMN_PHYSICS = "PHYSIC";
    public static final String COLUMN_OVULATIONS = "OVULATION";


    public static final String SQL_CREATE_TABLE_PAPER = "CREATE TABLE " + TABLE_PERIOD + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DAY + " TEXT NOT NULL, "
            + COLUMN_TYPE_DAY + " TEXT NOT NULL, "
            + COLUMN_MOTIONS + " TEXT NOT NULL, "
            + COLUMN_SYMPTOMS + " TEXT NOT NULL, "
            + COLUMN_PHYSICS + " TEXT NOT NULL, "
            + COLUMN_OVULATIONS + " TEXT NOT NULL " + ");";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    public void addPeriodData(String day, String type, String motions,String symptoms,String physics,String ovulations) {
        SQLiteDatabase database = instance.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_DAY, day);
        values.put(COLUMN_TYPE_DAY, type);
        values.put(COLUMN_MOTIONS, motions);
        values.put(COLUMN_SYMPTOMS, symptoms);
        values.put(COLUMN_PHYSICS, physics);
        values.put(COLUMN_OVULATIONS, ovulations);

        database.insert(TABLE_PERIOD, null, values);
        database.close();
    }


    public List<Data> getAllData() {
        SQLiteDatabase db = instance.getWritableDatabase();
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM '%s';", TABLE_PERIOD), null);
        List<Data> contacts = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                contacts.add(new Data(cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DAY)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_DAY)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_MOTIONS)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_SYMPTOMS)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PHYSICS)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_OVULATIONS))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return contacts;
    }

    public void updatePeriod(String curDay, String type, String motions,String symptoms,String physics,String ovulations) {
        SQLiteDatabase db = instance.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE_DAY, type);
        values.put(COLUMN_MOTIONS, motions);
        values.put(COLUMN_SYMPTOMS, symptoms);
        values.put(COLUMN_PHYSICS, physics);
        values.put(COLUMN_OVULATIONS, ovulations);
        db.update(TABLE_PERIOD, values, COLUMN_DAY + "='" + curDay + "'", null);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PAPER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERIOD);
    }



}
