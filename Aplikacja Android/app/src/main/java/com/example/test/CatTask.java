package com.example.test;

import android.content.Context;
import android.content.Intent;

public class CatTask implements Runnable {
    private Context kontekst;

    CatTask(Context context) {
        kontekst = context;
    }

    @Override
    public void run() {
        while (Integer.parseInt(BTStatic.steps.getText().toString()) < 20) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.print(e.getMessage());
            }
        }
        kontekst.sendBroadcast(new Intent("com.example.CAT_RESCUED"));
    }
}
