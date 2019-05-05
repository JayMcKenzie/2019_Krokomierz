package com.example.test;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Krokomierz.db";
    private static final String TABLE_NAME = "StepsTable";
    private static final String ID_COL = "ID";
    private static final String DATE_COL = "Date";
    private static final String STEPS_COL = "Steps";


    private static final String DB_CREATE =
            "CREATE TABLE " + TABLE_NAME + "( " + ID_COL + ", " +
                    DATE_COL + ", " + STEPS_COL + ");";


    private static final String DB_DROP =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);                          // View -> Tool Windows -> Device File Explorer -> Data -> Data -> com.example.test -> databases
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DB_DROP);
        onCreate(db);
    }

    public boolean insertData(String date, String steps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(DATE_COL, date);
        contentValue.put(STEPS_COL, steps);
        long result = db.insert(TABLE_NAME, null, contentValue);

        if (result == -1) {                                                                      // "insert" zwraca -1 jeśli jest error
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        return cursor;
    }

}