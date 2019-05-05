package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class Show_data extends AppCompatActivity {

    private Button cats;
    private Button cars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);


        cats = (Button)findViewById(R.id.cats_but);
        cars = (Button)findViewById(R.id.cars_but);

        cats.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {setCatsBut();}
        });

        cars.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {setCarsBut();}
        });
    }


    private void setCatsBut() {
        Intent intent = new Intent(this, Show_data_cats.class);
        startActivity(intent);
    }

    private void setCarsBut() {


    }


}
