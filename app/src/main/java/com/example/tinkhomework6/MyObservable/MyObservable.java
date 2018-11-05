package com.example.tinkhomework6.MyObservable;

import android.os.Handler;
import android.os.Looper;

import java.util.Objects;

public abstract class MyObservable<T> {
    private Handler handlerSubscribeOn;
    private Handler handlerObserveOn;
    MyLambdaObserver<? super T> observer;

    protected abstract void subscribeActual();

    void onObserve() {
        if (handlerObserveOn == null || handlerObserveOn.getLooper().getThread() == Thread.currentThread())
            observer.acceptValue();
        else
            handlerObserveOn.sendEmptyMessage(0);
    }

    void onSubscribe(Runnable runnable) {
        if (handlerSubscribeOn == null || handlerSubscribeOn.getLooper().getThread() == Thread.currentThread()) {
            runnable.run();
            onObserve();
        } else
            handlerSubscribeOn.sendMessage(handlerSubscribeOn.obtainMessage(0, runnable));
    }

    public final MyObservable<T> subscribeOn(Looper looper) {
        Objects.requireNonNull(looper, "scheduler is null");

        handlerSubscribeOn = new Handler(looper, msg -> {
            //noinspection unchecked
            System.out.println("**************subscribeOn handler involved");
            ((Runnable) msg.obj).run();
            onObserve();
            return true;
        });

        return this;
    }

    public final MyObservable<T> observeOn(Looper looper) {
        Objects.requireNonNull(looper, "scheduler is null");

        handlerObserveOn = new Handler(looper, msg -> {
            System.out.println("**************observeOn handler involved");
            observer.acceptValue();
            return true;
        });

        return this;
    }

    public final void subscribe(MyConsumer<? super T> onSuccess) {
        subscribe(onSuccess, new MyEmptyConsumer<>());
    }

    public final void subscribe(MyConsumer<? super T> onSuccess, MyConsumer<? super Throwable> onError) {
        Objects.requireNonNull(onSuccess, "consumer is null");
        Objects.requireNonNull(onError, "consumer is null");

        observer = new MyLambdaObserver<>(onSuccess, onError);

        subscribeActual();
    }

    public static <T> MyObservable<T> from(MyCallable<T> item) {
        Objects.requireNonNull(item, "The item is null");
        return new MyObservableFromCallable<>(item);
    }
}
