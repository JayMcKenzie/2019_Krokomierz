package com.example.test;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private TextView steps;
    private Button play;
    private Button settings;
    private Button exit;
    private Button connect;
    private Button reset;


    SharedPreferences SaveData;

    private static final String SaveGame = "Game saved";
    private static final String SaveSteps = "Steps saved";


    AlertDialog.Builder alertDialogBuilder;
    AlertDialog.Builder alertDialogBuilder2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        steps = (TextView)findViewById(R.id.stepsView);
        play = (Button)findViewById(R.id.play_but);
        settings = (Button)findViewById(R.id.settings_but);
        exit = (Button)findViewById(R.id.exit_but);
        connect = (Button)findViewById(R.id.connect_but);
        reset = (Button)findViewById(R.id.reset_but);


        SaveData = getApplicationContext().getSharedPreferences(SaveGame, 0);
        System.out.println(SaveData);


        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setPlayBut();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setExitBut();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setSettingsBut();
            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setConnectBut();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResetBut();
            }
        });

        alertReset();
        alertExit();

    }


    private void alertReset(){
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("RESET");
        alertDialogBuilder.setMessage("Do you want to reset your progress?");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {   // TO TEŻ NIE DZIAŁA
                //steps.setText("0");
                //setSaveBut();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // tu coś zrobić
            }
        });
    }           // to do

    private void alertExit(){
        alertDialogBuilder2 = new AlertDialog.Builder(this);
        alertDialogBuilder2.setTitle("EXIT");
        alertDialogBuilder2.setMessage("Do you want to save your progress?");
        alertDialogBuilder2.setCancelable(false);

        alertDialogBuilder2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                steps = (TextView)findViewById(R.id.stepsView);


                if (steps != null){
                    SharedPreferences.Editor preferencesEditor = SaveData.edit();

                    String savedsteps = steps.getText().toString();
                    preferencesEditor.putString(SaveSteps, savedsteps);
                    preferencesEditor.commit();
                }

                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        alertDialogBuilder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences.Editor preferencesEditor = SaveData.edit();
                preferencesEditor.putString(SaveSteps,"0");
                preferencesEditor.commit();

                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }

    private void setPlayBut() {
        Intent intent = new Intent(this, Game_mode.class);
        startActivity(intent);
    }

    private void setExitBut() {
        AlertDialog alertDialog = alertDialogBuilder2.create();
        alertDialog.show();
    }

    private void setSettingsBut() {


    }           // to do

    private void setConnectBut() {


    }           // to do

    private void setResetBut(){
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



}


