package com.example.test;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.UUID;
import android.widget.TextView;
import java.io.IOException;



public class Bluetooth extends Thread{                            // Book: The Android Developer's Collection

    private static final String TAG = "2";

    private TextView currentsteps;
    private final BluetoothDevice dev;
    private BluetoothSocket socket;
    private final UUID MY_UUID = UUID.fromString("128b9355-c1a1-4491-bb6d-0cbc2093abac");          // https://www.uuidgenerator.net/


    public Bluetooth(BluetoothDevice blue, TextView tab) {

        this.currentsteps = tab;
        this.dev = blue;
        BluetoothSocket temp = null;

        try {
            temp = blue.createRfcommSocketToServiceRecord(MY_UUID);               // Otwiera Socket
        }
        catch (IOException e) { Log.e(TAG, "Error!", e); }

        socket = temp;
    }


    public void run() {
        try {
            socket.connect();                          // Aby połączyć się z urządzeniem
        }
        catch (IOException ee) { Reconnect(); }

        (new BluetoothData(socket, currentsteps)).start();
        (new BluetoothSender(socket)).start();
    }

    private void Reconnect() {
        try {
            socket = (BluetoothSocket) dev.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(dev, 1);          // Book: Human Activity Recognition: Using Wearable Sensors and Smartphones
            socket.connect();
        }
        catch (Exception ex) { cancel(); }
    }

    private void cancel() {
        try {
            socket.close();                                                  // Zamyka połączenie
        }
        catch (IOException e) { Log.e(TAG, "Error!", e); }
    }

}



