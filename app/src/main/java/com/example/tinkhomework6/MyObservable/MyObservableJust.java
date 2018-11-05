package com.example.tinkhomework6.MyObservable;

import java.util.Objects;
import java.util.concurrent.Callable;

public class MyObservableJust<T> extends MyObservable<T> {
    private Callable callable;

    private MyObservableJust(Callable<T> callable) {
        this.callable = callable;
    }

    @Override
    protected void subscribeActual(MyObserver<? super T> observer) {
        if (handlerSubscribeOn == null || handlerObserveOn == null)
            throw new RuntimeException("Loopers must be set");

        handlerSubscribeOn.sendMessage(handlerSubscribeOn.obtainMessage(0, callable));
    }

    public static <T> MyObservable<T> just(Callable<T> item) {
        Objects.requireNonNull(item, "The item is null");
        return new MyObservableJust<>(item);
    }
}
