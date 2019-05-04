package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;


public class Game_cats extends AppCompatActivity {

    private TextView steps;
    private TextView date;
    private Button save;

    ImageView image;

    //Database database;

    SharedPreferences SaveData;
    private static final String SaveGame = "Game saved";
    private static final String SaveSteps = "Steps saved";

  //  Date currentTime = Calendar.getInstance().getTime();

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    String currentDateandTime = sdf.format(new Date());

   // Hashtable <SimpleDateFormat, Boolean> checkdate = new Hashtable <SimpleDateFormat, Boolean>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_cats);

        steps = (TextView)findViewById(R.id.stepsView);
        BTStatic.steps = steps;

        image = (ImageView)findViewById(R.id.Pic);
        image.setImageResource(R.drawable.kitty);

        save = (Button)findViewById(R.id.save_but);

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setSaveBut();
            }
        });

        SaveData = getApplicationContext().getSharedPreferences(SaveGame, 0);                     // pobiera tekst z pliku i zapisze w apce
        String st1 = SaveData.getString(SaveSteps, "0");                                       // pobiera kroki z pliku i zapisze w apce
        steps.setText(st1);


        TextView yourtextview = (TextView) findViewById(R.id.dateView);
        yourtextview.setText(currentDateandTime);

        String savedsteps = steps.getText().toString();
        String savedsate = yourtextview.getText().toString();

        BTStatic.database = new Database(this);


       /* if(savedsteps == "14"){
             BTStatic.database.insertData(savedsteps, savedsate);
        }
        */

        //BTStatic.database







    }


    private void setSaveBut() {

        steps = (TextView)findViewById(R.id.stepsView);
        date = (TextView)findViewById(R.id.dateView);

        System.out.println("Steps: " + steps);

        if (steps != null){

            System.out.println("W ifie");

            String savedsteps = steps.getText().toString();
            String savedsate = date.getText().toString();


            System.out.println("Date: " + savedsate);
            System.out.println("Steps: " + savedsteps);

            System.out.println("Przed insert");

            boolean isInserted = BTStatic.database.insertData(savedsate, savedsteps);

            System.out.println("Po insert");

            if (isInserted = true){
                Toast.makeText(Game_cats.this, "Data Inserted", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(Game_cats.this, "Data not Inserted", Toast.LENGTH_LONG).show();
            }
        }
    }
}
