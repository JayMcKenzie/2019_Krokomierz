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

        // początek - czy są trzy daty wstecz, jak nie to od razu umiera
        // początek - czy są 10 k
        // na bieżąco - jak będzie 10 k to wypisze "kotek żyje"

        checkSteps();

    }

    private void checkSteps(){       // sprawdza czy przez ostatnie 3 dni było 10 000 kroków

        Cursor res = BTStatic.database.getLast3();

        if(res.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Database is empty", Toast.LENGTH_LONG).show();
            return;
        }

        int licznik = 0;
        int a = 0, b = 0, c = 0;                         // a - dni
        int aa = 0, bb = 0, cc = 0;                        // aa - kroki danego dnia


        while(res.moveToNext()){                                // pobiera trzy ostatnie daty i zamienia je na inta
            String x = res.getString(1);
            String z = res.getString(2);
            String xx = x.substring(0,2);
            int y = Integer.parseInt(xx);
            int yy = Integer.parseInt(z);

            if(licznik == 0){ a = y; aa = yy;}
            if(licznik == 1){ b = y; bb = yy;}
            if(licznik == 2){ c = y; cc = yy;}
            licznik ++;
        }

        System.out.println("a = " + a + " b = " + b + " c = " + c);   // test
        System.out.println("aa = " + aa + " bb = " + bb + " cc = " + cc);   // test


        if(b == a-1 && c == b-1){                           // czy są wpisy z trzech ostatnich dni
            System.out.println("Trzy pod rząd");

            if(aa < 50 && bb < 50 && cc < 50){
                //umiera
            }
            else{
                //zyje
            }
            // czy powyżej 10k w każdym z trzech dni
            // jak w c jest 10k a w a i b nie - żyje
            // jeśli we wszystkich trzech poniżej 10 k to umiera



        }
        else{

            // umiera od razu

            System.out.println("Ominięto");
        }








    }


    private void setSaveBut() {

        steps = (TextView)findViewById(R.id.stepsView);

        System.out.println("Steps: " + steps);

        if (steps != null){

            String savedsteps = steps.getText().toString();
            String savedsate = currentDateandTime;

            System.out.println("Date: " + savedsate);      // test
            System.out.println("Steps: " + savedsteps);       // test

            //BTStatic.database.deleteData(currentDateandTime);

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
