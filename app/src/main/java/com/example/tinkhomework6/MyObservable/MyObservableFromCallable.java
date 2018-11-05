package com.example.tinkhomework6.MyObservable;

public class MyObservableFromCallable<T> extends MyObservable<T> {
    private MyCallable callable;

    MyObservableFromCallable(MyCallable<T> callable) {
        this.callable = callable;
    }

    @Override
    protected void subscribeActual() {
        Runnable runnable = () -> {
            try {
                //noinspection unchecked
                observer.onNext((T) callable.call());
            } catch (Throwable t) {
                observer.onError(t);
            }
        };

        onSubscribe(runnable);
    }
}
