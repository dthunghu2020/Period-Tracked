package com.hungdt.periodtracked.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hungdt.periodtracked.model.Paper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper instance;

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "PeriodTracked.db";

    public static final String TABLE_PERIOD_PAPER = "TB_PERIOD";
    public static final String COLUMN_ID = "ID_TABLE";
    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_BODY = "BODY";
    public static final String COLUMN_IMAGE = "IMAGE";


    public static final String SQL_CREATE_TABLE_PAPER = "CREATE TABLE " + TABLE_PERIOD_PAPER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TITLE + " INTERGER NOT NULL, "
            + COLUMN_BODY + " INTEGER NOT NULL, "
            + COLUMN_IMAGE + " INTEGER NOT NULL " + ");";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    public void addPeriodPaper(String title, String body, String image) {
        SQLiteDatabase database = instance.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_BODY, body);
        values.put(COLUMN_IMAGE, image);

        database.insert(TABLE_PERIOD_PAPER, null, values);
        database.close();
    }


    public List<Paper> getAllPaper() {
        SQLiteDatabase db = instance.getWritableDatabase();
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM '%s';", TABLE_PERIOD_PAPER), null);
        List<Paper> contacts = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                contacts.add(new Paper(cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_TITLE)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_BODY)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_IMAGE))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return contacts;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PAPER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERIOD_PAPER);
    }


}
