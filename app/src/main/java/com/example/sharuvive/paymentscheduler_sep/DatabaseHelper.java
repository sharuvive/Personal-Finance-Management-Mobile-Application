package com.example.sharuvive.paymentscheduler_sep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sharu Vive on 2/7/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "finance.db";

    public static final String TABLE_NAME = "paymentscheduler";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "DATE";
    public static final String COL_3 = "CATEGORY";
    public static final String COL_4 = "SUBCATEGORY";
    public static final String COL_5 = "DESCRIPTION";
    public static final String COL_6 = "AMOUNT";
    public static final String COL_7 = "STATUS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT, " + COL_3 + " TEXT, " + COL_4 + " TEXT, " + COL_5 + " TEXT, " + COL_6 + " TEXT, " + COL_7 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        onCreate(db);

    }

    String status = "onDue";

    public boolean insertData(String _date, String _category, String _subcategory, String _description, String _amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, _date);
        contentValues.put(COL_3, _category);
        contentValues.put(COL_4, _subcategory);
        contentValues.put(COL_5, _description);
        contentValues.put(COL_6, _amount);
        contentValues.put(COL_7, status);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery( "select rowid _id,* from "+TABLE_NAME, null);
        return result;
    }
}
