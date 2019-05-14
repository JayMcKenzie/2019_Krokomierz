package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CheckTask  implements Runnable{

    private Context kontekst;

    CheckTask(Context context) {
        kontekst = context;
    }

    @Override
    public void run() {
        while (Integer.parseInt(BTStatic.steps.getText().toString()) < 100) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.print(e.getMessage());
            }
        }

        if(Integer.parseInt(BTStatic.steps.getText().toString()) >= 100){
            BTStatic.feed = true;
        }

        kontekst.sendBroadcast(new Intent("com.example.CAT_FEED"));
    }
}
