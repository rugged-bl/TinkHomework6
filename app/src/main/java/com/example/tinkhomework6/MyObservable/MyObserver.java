package com.example.tinkhomework6.MyObservable;

import android.support.annotation.NonNull;

public interface MyObserver<T> {
    void onNext(@NonNull T t);
    void onError(@NonNull Throwable t);
}
