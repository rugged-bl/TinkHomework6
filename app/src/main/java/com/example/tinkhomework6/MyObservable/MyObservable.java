package com.example.tinkhomework6.MyObservable;

import android.os.Handler;
import android.os.Looper;

import java.util.Objects;
import java.util.concurrent.Callable;

public abstract class MyObservable<T> {
    Handler handlerSubscribeOn;

    Handler handlerObserveOn;
    private MyLambdaObserver<? super T> observer;

    protected abstract void subscribeActual(MyObserver<? super T> observer);

    public final MyObservable<T> subscribeOn(Looper looper) {
        Objects.requireNonNull(looper, "scheduler is null");

        handlerSubscribeOn = new Handler(looper, msg -> {
            try {
                //noinspection unchecked
                observer.onNext(((Callable<T>) (msg.obj)).call());
            } catch (Throwable t) {
                observer.onError(t);
            }
            handlerObserveOn.sendEmptyMessage(0);
            return true;
        });

        return this;
    }

    public final MyObservable<T> observeOn(Looper looper) {
        Objects.requireNonNull(looper, "scheduler is null");

        handlerObserveOn = new Handler(looper, msg -> {
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

        subscribeActual(observer);
    }

    public static <T> MyObservable<T> just(Callable<T> item) {
        return MyObservableJust.just(item);
    }
}
