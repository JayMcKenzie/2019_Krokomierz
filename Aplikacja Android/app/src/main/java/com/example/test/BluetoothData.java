package com.example.test;

import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;


public class BluetoothData extends Thread {

    private static final String TAG = "3";

    private final InputStream inputstream;                       // pobiera dane z urządzenia
    private TextView currentsteps;
    public int steps_int;
    private ImageView image;



    public BluetoothData(BluetoothSocket socket, TextView tab) {

        currentsteps = tab;

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
                byte[] buffer = new byte[32];
                inputstream.read(buffer);
                UpdateSteps();
            }
            catch (IOException e)
            {
                Log.d(TAG, "Error!", e);
                break;
            }
        }
    }


    private void UpdateSteps() {

        steps_int = Integer.parseInt(currentsteps.getText().toString());
        steps_int = steps_int + 1;

        currentsteps.post(new Runnable() {
            public void run() {
                currentsteps.setText(Integer.toString(steps_int));
            }
        });
    }

}


