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
import java.util.Random;


public class Game_cars extends AppCompatActivity {

    private TextView steps;
    private TextView stepsText;
    private TextView carText;
    private Button save;
    private Thread fuelYourCar;
    private BroadcastReceiver receiver;
    private BroadcastReceiver receiver2;

    ImageView image;

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    String currentDateandTime = sdf.format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_cars);

        steps = (TextView)findViewById(R.id.stepsView);
        BTStatic.stepsCar = steps;

        stepsText = (TextView)findViewById(R.id.stepsTextView);

        BTStatic.car = drawCar();

        image = (ImageView)findViewById(R.id.Pic);

        changeImage();

        carText = (TextView)findViewById(R.id.carView);
        carText.setText(BTStatic.car);

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

        startChecking();
    }


    private String drawCar(){

        Random rand = new Random();
        int car = rand.nextInt(9);
        System.out.println("Wylosowano: " + car);

        switch (car) {
            case 0: {
                System.out.println("Alfa Romeo");
                BTStatic.car = "Alfa Romeo";
                BTStatic.scale = 25;
                BTStatic.required = "3500";
                break;
            }

            case 1: {
                System.out.println("Ferrari");
                BTStatic.car = "Ferrari";
                BTStatic.scale = 8;
                BTStatic.required = "9000";
                break;
            }

            case 2: {
                System.out.println("Haas");
                BTStatic.car = "Haas";
                BTStatic.scale = 18;
                BTStatic.required = "5000";
                break;
            }

            case 3: {
                System.out.println("McLaren");
                BTStatic.car = "McLaren";
                BTStatic.scale = 15;
                BTStatic.required = "6000";
                break;
            }

            case 4: {
                System.out.println("Mercedes");
                BTStatic.car = "Mercedes";
                BTStatic.scale = 5;
                BTStatic.required = "10000";
                break;
            }

            case 5: {
                System.out.println("Racing Point");
                BTStatic.car = "Racing Point";
                BTStatic.scale = 20;
                BTStatic.required = "4500";
                break;
            }

            case 6: {
                System.out.println("Red Bull");
                BTStatic.car = "Red Bull";
                BTStatic.scale = 10;
                BTStatic.required = "8000";
                break;
            }

            case 7: {
                System.out.println("Renault");
                BTStatic.car = "Renault";
                BTStatic.scale = 12;
                BTStatic.required = "7000";
                break;
            }

            case 8: {
                System.out.println("Toro Rosso");
                BTStatic.car = "Toro Rosso";
                BTStatic.scale = 22;
                BTStatic.required = "4500";
                break;
            }

            case 9: {
                System.out.println("Williams");
                BTStatic.car = "Williams";
                BTStatic.scale = 30;
                BTStatic.required = "3000";
                break;
            }

            default: {
                System.out.println("default");
                break;
            }
        }

        return BTStatic.car;
    }


    private void changeImage(){

        switch (BTStatic.car) {
            case "Alfa Romeo": {
                image.setImageResource(R.drawable.alfa);
                break;
            }

            case "Ferrari": {
                image.setImageResource(R.drawable.ferrari);
                break;
            }

            case "Haas": {
                image.setImageResource(R.drawable.haas);
                break;
            }

            case "McLaren": {
                image.setImageResource(R.drawable.mclaren);
                break;
            }

            case "Mercedes": {
                image.setImageResource(R.drawable.mercedes);
                break;
            }

            case "Racing Point": {
                image.setImageResource(R.drawable.racingpoint);
                break;
            }

            case "Red Bull": {
                image.setImageResource(R.drawable.redbull);
                break;
            }

            case "Renault": {
                image.setImageResource(R.drawable.renault);
                break;
            }

            case "Toro Rosso": {
                image.setImageResource(R.drawable.tororosso);
                break;
            }

            case "Williams": {
                image.setImageResource(R.drawable.williams);
                break;
            }

            default: {
                System.out.println("default");
                break;
            }
        }
    }


    private void isDatabaseEmpty(){

        Cursor res = BTStatic.databaseCar.getAllData();

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

            BTStatic.databaseCar.insertData(d1, "0", "empty", "0", "0");
            BTStatic.databaseCar.insertData(d2, "0", "empty", "0", "0");
            BTStatic.databaseCar.insertData(d3, "0", "empty", "0", "0");
        }
    }


    private Date yesterday(int nr) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, nr);
        return cal.getTime();
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
                boolean isUpdated = BTStatic.databaseCar.updateData(savedsate, savedsteps, BTStatic.car, BTStatic.required, BTStatic.fueled);

                if (isUpdated == true){
                    Toast.makeText(Game_cars.this, "Data updated", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Game_cars.this, "Data not updated", Toast.LENGTH_LONG).show();
                }
            }
            else{
                boolean isInserted = BTStatic.databaseCar.insertData(savedsate, savedsteps, BTStatic.car, BTStatic.required, BTStatic.fueled);

                if (isInserted == true){
                    Toast.makeText(Game_cars.this, "Data inserted", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Game_cars.this, "Data not inserted", Toast.LENGTH_LONG).show();
                }
            }

            BTStatic.fueled = "0";
        }
    }


    private boolean checkDate(){
        Cursor res = BTStatic.databaseCar.getAllData();

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

        Cursor res = BTStatic.databaseCar.getAllData();

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
                fuelYourCar.interrupt();
                Toast.makeText(getApplicationContext(), "You fueled your car today!", Toast.LENGTH_LONG).show();
            }
        };
        registerReceiver(receiver2, new IntentFilter("com.example.FUELED"));
        fuelYourCar = new Thread(new CheckTaskCars(this));
        fuelYourCar.start();
    }

    @Override
    public void onBackPressed() {
        if(fuelYourCar != null){
            fuelYourCar.interrupt();
        }
        if(receiver2 != null){
            unregisterReceiver(receiver2);
        }
        finish();
    }
}
