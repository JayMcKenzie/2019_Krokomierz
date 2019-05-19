package com.example.test;

import android.content.Context;
import android.content.Intent;

public class CheckTaskCars  implements Runnable{

    private Context kontekst;
    private int n = 1;

    CheckTaskCars(Context context) {
        kontekst = context;
    }

    @Override
    public void run() {
        while (Integer.parseInt(BTStatic.stepsCar.getText().toString()) < n*Integer.parseInt(BTStatic.required ) || BTStatic.repairing) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.print(e.getMessage());
            }
        }

        n++;
        String str1 = Integer.toString(n);
        BTStatic.fueled = str1;

        kontekst.sendBroadcast(new Intent("com.example.FUELED"));
    }
}
