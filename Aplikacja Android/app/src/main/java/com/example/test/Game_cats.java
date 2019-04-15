package com.example.test;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Game_cats extends AppCompatActivity {

    private TextView steps;

    ImageView image;


    SharedPreferences SaveData;
    private static final String SaveGame = "Game saved";
    private static final String SaveSteps = "Steps saved";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_cats);

        steps = (TextView)findViewById(R.id.stepsView);

        image = (ImageView)findViewById(R.id.Pic);
        image.setImageResource(R.drawable.kitty);



        SaveData = getApplicationContext().getSharedPreferences(SaveGame, 0);                       // pobiera tekst z pliku i zapisze w apce
        String st1 = SaveData.getString(SaveSteps, "0");                                       // pobiera kroki z pliku i zapisze w apce
        steps.setText(st1);
    }
}
