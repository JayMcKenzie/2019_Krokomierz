package com.example.test;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    private TextView steps;
    private TextView date;
    private Button play;
    private Button settings;
    private Button exit;
    private Button connect;
    private Button reset;

    private BluetoothAdapter blueAdapt;

    SharedPreferences SaveData;

    private static final String SaveGame = "Game saved";
    private static final String SaveSteps = "Steps saved";
    private static final String SaveDate = "Date saved";

    AlertDialog.Builder alertDialogBuilder;
    AlertDialog.Builder alertDialogBuilder2;

    Hashtable <SimpleDateFormat, Boolean> checkdate = new Hashtable <SimpleDateFormat, Boolean>();

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    String str_date;


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


        checkdate.put(sdf, false);


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
            public void onClick(DialogInterface arg0, int arg1) {
                SharedPreferences.Editor preferencesEditor = SaveData.edit();
                preferencesEditor.putString(SaveSteps,"0");
                preferencesEditor.commit();
                Toast.makeText(getApplicationContext(), "Data has been reset", Toast.LENGTH_LONG).show();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Data has not been reset", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void alertExit(){                              // zmienione do testów       // save hashtable
        alertDialogBuilder2 = new AlertDialog.Builder(this);
        alertDialogBuilder2.setTitle("EXIT");
        alertDialogBuilder2.setMessage("Do you want to save your progress?");
        alertDialogBuilder2.setCancelable(false);

        alertDialogBuilder2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                steps = (TextView)findViewById(R.id.stepsView);
                date = (TextView)findViewById(R.id.dateView);

                //SharedPreferences.Editor preferencesEditor = SaveData.edit();   // tylko do testu

                if (steps != null){
                    SharedPreferences.Editor preferencesEditor = SaveData.edit();

                    String savedsteps = steps.getText().toString();
                    preferencesEditor.putString(SaveSteps, savedsteps);

                    SharedPreferences.Editor editor = SaveData.edit();

                    //editor.putString("Date", str_date);
                    //Boolean type_bool = checkdate.get(sdf);
                    //String type_str =
                    //editor.putString("Type", type_str);

                    // save hashtable

                    preferencesEditor.commit();
                }

                //preferencesEditor.putString(SaveSteps, "502");             // tylko do testu
               // preferencesEditor.commit();                           // tylko do testu

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



    private void ConnectBluetooth(){                                                    // https://developer.android.com/reference/android/bluetooth/BluetoothAdapter

        blueAdapt = BluetoothAdapter.getDefaultAdapter();                               // wyszukuje dostępne urządzenia Bluetooth

        if (blueAdapt == null) { Toast.makeText(getApplicationContext(), "Bluetooth is not supported", Toast.LENGTH_LONG).show();  return ;}

        if (!IsBlueEnabled()) {                                                         // jeśli nie jest dostępny, sprawdza co jakiś czas czy się pojawił

            Intent enab = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);           // Pozwala użytkownikowi włączyć Bluetooth
            startActivityForResult(enab, 1);                                //  REQUEST_ENABLE_BT

            try { TimeUnit.MILLISECONDS.sleep(250); }                           // Czas po jakim próbuje połączyć się ponownie
            catch (InterruptedException e) { e.printStackTrace(); }
        }

        BluetoothDevice device = getBlue();

        if (device == null) {
            Toast.makeText(getApplicationContext(), "Connection is not possible", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(getApplicationContext(), device.getName() + " connected!", Toast.LENGTH_LONG).show();
        (new Bluetooth(device, steps, checkdate)).start();

    }

    private boolean IsBlueEnabled() {

        if (!blueAdapt.isEnabled()) { return false; }

        Toast.makeText(getApplicationContext(), "Bluetooth enabled", Toast.LENGTH_LONG).show();
        return true;
    }

    private BluetoothDevice getBlue() {
        Set <BluetoothDevice> dev = blueAdapt.getBondedDevices();        // Return the set of BluetoothDevice objects that are bonded (paired) to the local adapter.
        if (dev.size() > 0) {
            for (BluetoothDevice d : dev) {
                if (d.getName().equals("HC-05")) {
                    return d;
                }
            }
        }
        return null;
    }



    private void setPlayBut() {
        Intent intent = new Intent(this, Game_mode.class);
        startActivity(intent);
    }

    private void setExitBut() {
        AlertDialog alertDialog = alertDialogBuilder2.create();
        alertDialog.show();
    }           // to do - przy yes nadal resetuje

    private void setSettingsBut() {


    }           // to do LATER

    private void setConnectBut() {

        ConnectBluetooth();
    }

    private void setResetBut(){
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }          // to do - przy yes nie resetuje


    private void isAlive(){


    }

}


