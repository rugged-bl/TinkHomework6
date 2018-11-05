package com.example.tinkhomework6;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

import com.example.tinkhomework6.MyObservable.MyObservable;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LooperThread looperThread = new LooperThread();
        looperThread.setName("looper thread");
        looperThread.start();

        //Both loopers for observeOn and subscribeOn must be set
        //uncomment underlying code to check exception in callable handling
        MyObservable.just(() -> {
            System.out.println("callable executes on " + Thread.currentThread().getName());
            /*if (looperThread != null)
                throw new RuntimeException("exception in callable ('if' because of unreachable statement)");*/
            return "observed on ";
        })
                .observeOn(Looper.getMainLooper())
                .subscribeOn(looperThread.getLooper())
                .subscribe(str -> System.out.println(str + Thread.currentThread().getName())/*, Throwable::printStackTrace*/);
    }
}
