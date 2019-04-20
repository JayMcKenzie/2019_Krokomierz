package com.example.test;
import android.content.Context;
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
        SQLiteDatabase db = this.getWritableDatabase();
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
}
