package com.example.test;
import android.content.Context;
import android.content.Intent;

public class CarTaskReady implements Runnable  {

    private Context kontekst;

    CarTaskReady(Context context) {
        kontekst = context;
    }

    @Override
    public void run() {
        while (Integer.parseInt(BTStatic.stepsCar.getText().toString()) < 2000) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.print(e.getMessage());
            }
        }

        kontekst.sendBroadcast(new Intent("com.example.FIXED"));
    }
}
