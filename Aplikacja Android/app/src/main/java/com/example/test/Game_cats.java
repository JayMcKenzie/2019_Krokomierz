package com.example.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;


public class Game_cats extends AppCompatActivity {

    private TextView steps;

    ImageView image;

    SharedPreferences SaveData;
    private static final String SaveGame = "Game saved";
    private static final String SaveSteps = "Steps saved";

    Date currentTime = Calendar.getInstance().getTime();

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    String currentDateandTime = sdf.format(new Date());

    Hashtable <SimpleDateFormat, Boolean> checkdate = new Hashtable <SimpleDateFormat, Boolean>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_cats);

        steps = (TextView)findViewById(R.id.stepsView);
        BTStatic.steps = steps;

        image = (ImageView)findViewById(R.id.Pic);
        image.setImageResource(R.drawable.kitty);

        SaveData = getApplicationContext().getSharedPreferences(SaveGame, 0);                     // pobiera tekst z pliku i zapisze w apce
        String st1 = SaveData.getString(SaveSteps, "0");                                       // pobiera kroki z pliku i zapisze w apce
        steps.setText(st1);


        TextView yourtextview = (TextView) findViewById(R.id.dateView);
        yourtextview.setText(currentDateandTime);







    }
}
