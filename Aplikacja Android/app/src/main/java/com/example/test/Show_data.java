package com.example.test;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Show_data extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_database);

        cursor();

        // tu jakoś to wyświetlić, ale nie oknem dialogowym :c

    }


    public void cursor(){
        Cursor res = BTStatic.database.getAllData();

        if(res.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Database is empty", Toast.LENGTH_LONG).show();
            return;
        }

        StringBuffer buffer = new StringBuffer();

        while(res.moveToNext()){
            buffer.append("Id: " + res.getString(0) + "\n");
            buffer.append("Date: " + res.getString(1) + "\n");
            buffer.append("Steps: " + res.getString(2) + "\n\n");
        }

    }

    public void showMessage(String title, String Message){

    }

}
