package com.example.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database_cars extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "KrokomierzCars.db";
    private static final String TABLE_NAME = "StepsTable";
    private static final String ID_COL = "ID";
    private static final String DATE_COL = "Date";
    private static final String STEPS_COL = "Steps";
    private static final String CAR_COL = "Car";
    private static final String REQUIRED_COL = "Required";
    private static final String FUELED_COL = "Fueled";
    private static final String READY_COL = "Ready";


    private static final String DB_CREATE =
            "CREATE TABLE " + TABLE_NAME + "( " + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT" + ", " +
                    DATE_COL + ", " + STEPS_COL + "," + CAR_COL + "," + REQUIRED_COL + "," + FUELED_COL + "," + READY_COL + ");";


    private static final String DB_DROP =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public Database_cars(Context context) {
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

    public boolean insertData(String date, String steps, String car, String required, String fueled, String ready) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(DATE_COL, date);
        contentValue.put(STEPS_COL, steps);
        contentValue.put(CAR_COL, car);
        contentValue.put(REQUIRED_COL, required);
        contentValue.put(FUELED_COL, fueled);
        contentValue.put(READY_COL, ready);
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
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " ORDER BY date(substr(Date,7)||'-'||substr(Date,4,2)||'-'||substr(Date,1,2)) DESC;", null);
        return cursor;
    }


    public boolean updateData(String date, String steps, String car, String required, String fueled, String ready) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(DATE_COL, date);
        contentValue.put(STEPS_COL, steps);
        contentValue.put(CAR_COL, car);
        contentValue.put(REQUIRED_COL, required);
        contentValue.put(FUELED_COL, fueled);
        contentValue.put(READY_COL, ready);
        db.update(TABLE_NAME, contentValue, "Date = ?", new String[]{date} );
        return true;
    }


    public Integer deleteData(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "Date = ?", new String[] {date});
    }


    public Cursor getLast3(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + TABLE_NAME + " ORDER BY date(substr(Date,7)||'-'||substr(Date,4,2)||'-'||substr(Date,1,2)) DESC limit 4;";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getTop(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + TABLE_NAME + " ORDER BY date(substr(Date,7)||'-'||substr(Date,4,2)||'-'||substr(Date,1,2)) DESC  limit 1;";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

}
