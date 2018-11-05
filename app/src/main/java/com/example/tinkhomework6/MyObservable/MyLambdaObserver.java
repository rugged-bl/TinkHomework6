package com.example.tinkhomework6.MyObservable;

public class MyLambdaObserver<T> implements MyObserver<T> {
    private final MyConsumer<? super T> onNext;
    private final MyConsumer<? super Throwable> onError;
    private T value;
    private Throwable throwable;

    MyLambdaObserver(MyConsumer<? super T> onNext, MyConsumer<? super Throwable> onError) {
        this.onNext = onNext;
        this.onError = onError;
    }

    @Override
    public void onNext(T t) {
        value = t;
    }

    @Override
    public void onError(Throwable t) {
        throwable = t;
    }

    void acceptValue() {
        if (throwable == null)
            onNext.accept(value);
        else
            onError.accept(throwable);
    }
}
