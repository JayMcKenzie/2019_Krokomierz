package com.example.test;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;


public class Show_data_cars extends AppCompatActivity {

    private TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_data_cars);

        data = (TextView)findViewById(R.id.dataView);

        cursor();
    }


    public void cursor(){
        Cursor res = BTStatic.databaseCar.getAllData();

        if(res.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Database is empty", Toast.LENGTH_LONG).show();
            return;
        }

        StringBuffer buffer = new StringBuffer();

        while(res.moveToNext()){
            buffer.append("Date: " + res.getString(1) + "\n");
            buffer.append("Steps: " + res.getString(2) + "\n");
            buffer.append("Car: " + res.getString(3) + "\n");
            buffer.append("Required: " + res.getString(4) + "\n");
            buffer.append("Fueled: " + res.getString(5) + "\n\n");

        }

        data.setText(buffer.toString());

    }

}
