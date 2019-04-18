package com.example.test;

import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Hashtable;


public class BluetoothData extends Thread {

    private static final String TAG = "3";

    private final InputStream inputstream;                       // pobiera dane z urządzenia
    private TextView currentsteps;
    public int steps_int;
    private ImageView image;
    Hashtable<SimpleDateFormat, Boolean> checkdate;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");



    public BluetoothData(BluetoothSocket socket, TextView tab, Hashtable<SimpleDateFormat, Boolean> checkdate) {

        this.currentsteps = tab;
        this.checkdate = checkdate;


        InputStream in = null;

        try
        {
            in = socket.getInputStream();
        }
        catch (IOException e) { Log.e(TAG, "Error!", e); }

        inputstream = in;

    }


    public void run() {
        while (true) {
            try
            {
                //Thread.sleep(1000);
                byte[] buffer = new byte[4];
                inputstream.read(buffer);
                //byte[] steps_bytes = Arrays.copyOfRange(buffer, 0, 4);
                String pk = "Pakiet: ";
                for(byte b : buffer) {
                    pk += String.format(" %02x", (int)b);
                }
                Log.d(TAG, pk);
                if(true)
                {
                    Integer num = java.nio.ByteBuffer.wrap(buffer).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();

                    if (num.equals(0))
                        continue;

                    BTStatic.currentSteps += num;
                    final String dane = BTStatic.currentSteps.toString();

                    //UpdateSteps();
                    if (BTStatic.steps != null) {
                        BTStatic.steps.post(new Runnable() {
                            public void run() {
                                BTStatic.steps.setText(dane);
                            }
                        });
                    }

                    // 1 bajt char - typ wiadomosci
                    // - Zapytanie o stan (albo stm zeruje licznik albo osobny pakiet z resetem licznika)
                    // - Liczba krokow (wysyla stm)
                    // 4 bajty - libzba krokow int
                    // apka pyta stma o ilosc krokow - raz na sekunde - zeruje sie licznik stma
                    // dodaje to do krokow w apce
                    // apka ma umiec wysyłać dane
                    // :)
                }

            }
            catch (Throwable e)
            {
                Log.d(TAG, "Error!", e);
                break;
            }
        }
    }


    private void UpdateSteps() {

        steps_int = Integer.parseInt(currentsteps.getText().toString());

        //steps_int = steps_int + 1;
        System.out.println("kroki: "+steps_int);
        currentsteps.post(new Runnable() {
            public void run() {
                currentsteps.setText(Integer.toString(steps_int));

                if(steps_int >= 10000){
                    checkdate.put(sdf, true);
                }

            }
        });

    }

}


