package com.example.tinkhomework6.MyObservable;

public class MyEmptyConsumer<T> implements MyConsumer<T> {
    @Override
    public void accept(T t) {
        //Nothing
    }
}
