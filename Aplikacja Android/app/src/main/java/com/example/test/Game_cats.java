package com.example.test;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Game_cats extends AppCompatActivity {

    private TextView steps;
    private Button save;

    ImageView image;

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    String currentDateandTime = sdf.format(new Date());


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

        String st = showSteps();
        steps.setText(st);
        int sti = Integer.parseInt(st);
        BTStatic.currentSteps = sti;

    }


    private void setSaveBut() {

        steps = (TextView)findViewById(R.id.stepsView);

        System.out.println("Steps: " + steps);

        if (steps != null){

            String savedsteps = steps.getText().toString();
            String savedsate = currentDateandTime;

            System.out.println("Date: " + savedsate);      // test
            System.out.println("Steps: " + savedsteps);       // test


            if(checkDate()){
                boolean isUpdated = BTStatic.database.updateData(savedsate, savedsteps);

                if (isUpdated = true){
                    Toast.makeText(Game_cats.this, "Data updated", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Game_cats.this, "Data not updated", Toast.LENGTH_LONG).show();
                }
            }
            else{
                boolean isInserted = BTStatic.database.insertData(savedsate, savedsteps);

                if (isInserted = true){
                    Toast.makeText(Game_cats.this, "Data inserted", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Game_cats.this, "Data not inserted", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    private boolean checkDate(){
        Cursor res = BTStatic.database.getAllData();

        if(res.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Database is empty", Toast.LENGTH_LONG).show();
            return false;
        }

        while(res.moveToNext()){
            if(currentDateandTime.equals(res.getString(1))){           // sprawdza, czy taka data jest już w bazie
                return true;
            }
        }
        return false;
    }


    private String showSteps(){

        Cursor res = BTStatic.database.getAllData();

        if(res.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Database is empty", Toast.LENGTH_LONG).show();
            return "0";
        }

        while(res.moveToNext()){
            if(currentDateandTime.equals(res.getString(1))){
                return res.getString(2);
            }
        }

        return "1";
    }

}
