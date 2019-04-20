package com.example.test;

import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Hashtable;


public class BluetoothSender extends Thread {

    private static final String TAG = "4";
    private final OutputStream outputstream;                       // wysyla dane do urządzenia

    public BluetoothSender(BluetoothSocket socket) {

        OutputStream out = null;

        try
        {
            out = socket.getOutputStream();
        }
        catch (IOException e) { Log.e(TAG, "Error!", e); }

        outputstream = out;
    }


    public void run() {

        while (true) try {
            String zapytanie = "Z";
            outputstream.write(zapytanie.getBytes());
            Thread.sleep(1000);
        } catch (Throwable e) {
            Log.d(TAG, "Error!", e);
            break;
        }
    }


}


