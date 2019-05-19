package com.example.test;

import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;


public class BluetoothData extends Thread {

    private static final String TAG = "3";

    private final InputStream inputstream;                       // pobiera dane z urządzenia
    private TextView currentsteps;


    public BluetoothData(BluetoothSocket socket, TextView tab) {

        this.currentsteps = tab;

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
                byte[] buffer = new byte[4];
                inputstream.read(buffer);
                //byte[] steps_bytes = Arrays.copyOfRange(buffer, 0, 4);

                String pk = "Pakiet: ";                              // kontrolne wyświetlenie działania w konsoli
                for(byte b : buffer) {
                    pk += String.format(" %02x", (int)b);
                }
                Log.d(TAG, pk);

                if(true)
                {
                    Integer num = java.nio.ByteBuffer.wrap(buffer).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();       // odebrane dane - zamienia na int

                    if (num.equals(0))
                        continue;

                    BTStatic.currentSteps += num;
                    final String dane = BTStatic.currentSteps.toString();

                    if (BTStatic.steps != null) {
                        BTStatic.steps.post(new Runnable() {
                            public void run() {
                                BTStatic.steps.setText(dane);
                            }
                        });
                    }
                    if (BTStatic.stepsCar != null) {
                        BTStatic.stepsCar.post(new Runnable() {
                            public void run() {
                                BTStatic.stepsCar.setText(dane);
                            }
                        });
                    }
                }
            }
            catch (Throwable e)
            {
                Log.d(TAG, "Error!", e);
                break;
            }
        }
    }

}


