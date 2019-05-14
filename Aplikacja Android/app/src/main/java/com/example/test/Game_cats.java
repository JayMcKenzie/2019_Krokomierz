package com.example.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
    private TextView stepsText;
    private Button save;
    private Thread uratujKotka;
    private BroadcastReceiver receiver;

    ImageView image;

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    String currentDateandTime = sdf.format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_cats);

        steps = (TextView)findViewById(R.id.stepsView);
        BTStatic.steps = steps;

        stepsText = (TextView)findViewById(R.id.stepsTextView);

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

       // BTStatic.database.deleteData("16.05.2019");

        checkSteps();

    }


    private void checkSteps(){       // sprawdza czy przez ostatnie 3 dni było 10 000 kroków

        Cursor res = BTStatic.database.getLast3();

        if(res.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Database is empty", Toast.LENGTH_LONG).show();
            return;
        }

        int licznik = 0;
        int a = 0, b = 0, c = 0, d = 0;                         // a - dni
        int aa = 0, bb = 0, cc = 0, dd = 0;                        // aa - kroki danego dnia
        int aaa = 0, bbb = 0, ccc = 0, ddd = 0;                       // aaa - 1 rescued, 0 died



            while(res.moveToNext()){                                // pobiera cztery ostatnie daty i zamienia je na inta
            String x = res.getString(1);
            String z = res.getString(2);
            String r = res.getString(3) == null ? "0" : res.getString(3);
            String xx = x.substring(0,2);

            int y,yy,yyy;
            y = Integer.parseInt(xx);
            yy = Integer.parseInt(z);
            yyy = Integer.parseInt(r);

            if(licznik == 0){ a = y; aa = yy; aaa = yyy;}
            if(licznik == 1){ b = y; bb = yy; bbb = yyy;}
            if(licznik == 2){ c = y; cc = yy; ccc = yyy;}
            if(licznik == 3){ d = y; dd = yy; ddd = yyy;}

            licznik ++;
        }


        BTStatic.rescued  = String.valueOf(aaa);

        System.out.println("a = " + a + " b = " + b + " c = " + c + " d " + d);            // test
        System.out.println("aa = " + aa + " bb = " + bb + " cc = " + cc + " dd " + dd);        // test
        System.out.println("aaa = " + aaa + " bbb = " + bbb + " ccc = " + ccc + " ddd " + ddd);      // test


        String today = currentDateandTime.substring(0,2);
        int today2 = Integer.parseInt(today);


        if(today2 != a){
            if(a == today2-1 && b == today2-2 && c == today2-3) {                                        // czy są wpisy z trzech ostatnich dni

                boolean dying;

                if(aaa == 1 && bbb == 1 && ccc == 1){ dead(); }

                if (aa < 100 && bb < 100 && cc < 100) { dying = true; }
                else { dying = false; }

                if (dying) { dying(); }  // umiera, ale można go odratować
            }
            else { dead(); }  // umiera od razu
        }
    }


    private void dying(){
        final String temp_steps = BTStatic.steps.getText().toString();
        BTStatic.steps.setText("0");

        save.setVisibility(View.GONE);
        image.setImageResource(R.drawable.kitty);
        image.setAlpha(160);

        Toast.makeText(getApplicationContext(), "Your cat is dying!", Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "To save him, you have to do 3000 steps more", Toast.LENGTH_LONG).show();

        BTStatic.howMany = 20;    // 3000

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                image.setImageResource(R.drawable.kitty);
                image.setAlpha(255);
                save.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "You saved your cat!", Toast.LENGTH_LONG).show();
                uratujKotka.interrupt();
                BTStatic.steps.setText(temp_steps);
                BTStatic.currentSteps = Integer.parseInt(temp_steps);
                BTStatic.rescued = "1";
            }
        };
        registerReceiver(receiver, new IntentFilter("com.example.CAT_RESCUED"));
        uratujKotka = new Thread(new CatTask(this));
        uratujKotka.start();
    }


    private void dead(){
        final String temp_steps = BTStatic.steps.getText().toString();
        BTStatic.steps.setText("0");
        BTStatic.currentSteps = 0;

        save.setVisibility(View.GONE);
        image.setImageResource(R.drawable.petstore);

        Toast.makeText(getApplicationContext(), "Your cat died...", Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "You have to buy a new cat! \n (The shop is 15000 steps away)", Toast.LENGTH_LONG).show();


        BTStatic.howMany = 30;        // 15000

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                image.setImageResource(R.drawable.kitty);
                save.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "You have a new cat!", Toast.LENGTH_LONG).show();
                uratujKotka.interrupt();
                BTStatic.steps.setText(temp_steps);
                BTStatic.currentSteps = Integer.parseInt(temp_steps);
                //BTStatic.rescued = "1"
            }
        };
        registerReceiver(receiver, new IntentFilter("com.example.CAT_RESCUED"));
        uratujKotka = new Thread(new CatTask(this));
        uratujKotka.start();
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
                boolean isUpdated = BTStatic.database.updateData(savedsate, savedsteps, BTStatic.rescued);

                if (isUpdated == true){
                    Toast.makeText(Game_cats.this, "Data updated", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Game_cats.this, "Data not updated", Toast.LENGTH_LONG).show();
                }
            }
            else{
                boolean isInserted = BTStatic.database.insertData(savedsate, savedsteps, BTStatic.rescued);

                if (isInserted == true){
                    Toast.makeText(Game_cats.this, "Data inserted", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Game_cats.this, "Data not inserted", Toast.LENGTH_LONG).show();
                }
            }

            BTStatic.rescued = "0";
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
                return res.getString(2) == null ? "0": res.getString(2);
            }
        }

        return "1";
    }


    @Override
    public void onBackPressed() {
        if(uratujKotka != null){
            uratujKotka.interrupt();
        }
        if(receiver != null){
            unregisterReceiver(receiver);
        }
        finish();
    }
}
