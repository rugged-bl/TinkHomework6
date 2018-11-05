package com.example.tinkhomework6;

import android.os.Looper;

public class LooperThread extends Thread {
    private Looper looper;

    @Override
    public void run() {
        Looper.prepare();

        looper = Looper.myLooper();

        Looper.loop();
    }

    public Looper getLooper() {
        return looper;
    }
}
