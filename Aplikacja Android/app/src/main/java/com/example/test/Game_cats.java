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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Game_cats extends AppCompatActivity {

    private TextView steps;
    private TextView stepsText;
    private Button save;
    private Thread uratujKotka;
    private Thread nakarmKotka;
    private BroadcastReceiver receiver;
    private BroadcastReceiver receiver2;

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

        //BTStatic.database.deleteData("19.05.2019");

        isDatabaseEmpty();

        checkSteps();

        startChecking();
    }


    private void isDatabaseEmpty(){

        Cursor res = BTStatic.database.getAllData();

        if(res.getCount() == 0) {

            DateFormat dateFormat1 = new SimpleDateFormat("dd.MM.yyyy");
            String d1 = dateFormat1.format(yesterday(-1));
            System.out.println(d1);

            DateFormat dateFormat2 = new SimpleDateFormat("dd.MM.yyyy");
            String d2 = dateFormat2.format(yesterday(-2));
            System.out.println(d2);

            DateFormat dateFormat3 = new SimpleDateFormat("dd.MM.yyyy");
            String d3 = dateFormat3.format(yesterday(-3));
            System.out.println(d3);

            BTStatic.database.insertData(d1, "0", "0");
            BTStatic.database.insertData(d2, "0", "0");
            BTStatic.database.insertData(d3, "0", "0");
        }
    }


    private Date yesterday(int nr) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, nr);
        return cal.getTime();
    }


    private void checkSteps(){       // sprawdza czy przez ostatnie 3 dni było 10 000 kroków

        Cursor res = BTStatic.database.getLast3();

        if(res.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Database is empty", Toast.LENGTH_LONG).show();
            return;
        }

        int licznik = 0;
        String a = "0", b = "0", c = "0";                         // a - dni
        int aa = 0, bb = 0, cc = 0;                        // aa - kroki danego dnia
        int aaa = 0, bbb = 0, ccc = 0;                       // aaa - 1 rescued, 0 died



        while(res.moveToNext()){                                // pobiera cztery ostatnie daty i zamienia je na inta
            String x = res.getString(1);
            String z = res.getString(2);
            String r = res.getString(3) == null ? "0" : res.getString(3);

            int yy,yyy;
            yy = Integer.parseInt(z);
            yyy = Integer.parseInt(r);

            if(licznik == 0){ a = x; aa = yy; aaa = yyy;}
            if(licznik == 1){ b = x; bb = yy; bbb = yyy;}
            if(licznik == 2){ c = x; cc = yy; ccc = yyy;}

            licznik ++;
        }


        BTStatic.rescued  = String.valueOf(aaa);

        System.out.println("a = " + a + " b = " + b + " c = " + c);            // test
        System.out.println("aa = " + aa + " bb = " + bb + " cc = " + cc);        // test
        System.out.println("aaa = " + aaa + " bbb = " + bbb + " ccc = " + ccc );      // test

        Date today2 = new Date();
        System.out.println("today = " + today2);

        Date day1 = yesterday(-1);
        Date day2 = yesterday(-2);
        Date day3 = yesterday(-3);

        System.out.println("day1 = " + day1 + " day2 = " + day2 + " day3 = " + day3 );    // test
        System.out.println("today2" + today2.getDate());                                  // test
        System.out.println("Pierwszy w bazie" + a);                                                      // test

        if(!CompareTwoDates(today2,a)){

            if(CompareThreeDates(a,b,c)) {                                        // czy są wpisy z trzech ostatnich dni

                boolean dying = false;

                if(aaa == 1 && bbb == 1 && ccc == 1){ BTStatic.rescued = "0"; dead(); }
                else if (aa < 100 && bb < 100 && cc < 100) { dying = true; }
                else { BTStatic.rescued = "0"; dying = false; }

                if (dying) { dying(); dying = false; }  // umiera, ale można go odratować
            }
            else { BTStatic.rescued = "0"; dead(); }  // umiera od razu
        }
    }


    private static boolean CompareThreeDates(String a, String b, String c){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try{
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            Calendar calendar3 = Calendar.getInstance();
            calendar1.setTime(format.parse(a));
            calendar2.setTime(format.parse(b));
            calendar3.setTime(format.parse(c));

            calendar3.add(Calendar.DAY_OF_MONTH,2);
            calendar2.add(Calendar.DAY_OF_MONTH,1);

            if(calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
                    && calendar2.get(Calendar.DAY_OF_MONTH) == calendar3.get(Calendar.DAY_OF_MONTH)){

                System.out.println(calendar1);
                System.out.println(calendar2);
                System.out.println("Trzy daty identyczne");
                return true;
            }
        }
        catch(ParseException e){
            System.out.println(e.getMessage());
        }

        return false;
    }


    private static boolean CompareTwoDates(Date a, String b){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try{
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            calendar1.setTime(a);
            calendar2.setTime(format.parse(b));

            if(calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)){
                System.out.println("Dwie daty identyczne");
                return true;
            }
        }
        catch(ParseException e){
            System.out.println(e.getMessage());
        }
        return false;
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

        return "0";
    }


    private void startChecking(){
        receiver2 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                nakarmKotka.interrupt();
                Toast.makeText(getApplicationContext(), "You feed your cat today!", Toast.LENGTH_LONG).show();
            }
        };
        registerReceiver(receiver2, new IntentFilter("com.example.CAT_FEED"));
        nakarmKotka = new Thread(new CheckTask(this));
        nakarmKotka.start();
    }


    @Override
    public void onBackPressed() {
        if(uratujKotka != null){
            uratujKotka.interrupt();
        }
        if(nakarmKotka != null){
            nakarmKotka.interrupt();
        }
        if(receiver != null){
            unregisterReceiver(receiver);
        }
        if(receiver2 != null){
            unregisterReceiver(receiver2);
        }
        finish();
    }
}
