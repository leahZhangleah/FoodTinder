package com.example.android.foodtinder;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {
    private Executor mainThreadExecutor, singleThreadExecutor,threadPoolExecutor;
    private static AppExecutor mInstance;
    private static final Object LOCK = new Object();

    private AppExecutor(Executor mainThreadExecutor, Executor singleThreadExecutor, Executor threadPoolExecutor) {
        this.mainThreadExecutor = mainThreadExecutor;
        this.singleThreadExecutor = singleThreadExecutor;
        this.threadPoolExecutor = threadPoolExecutor;
    }

    public static AppExecutor getInstance(){
        if(mInstance==null){
            synchronized (LOCK){
                mInstance = new AppExecutor(
                        new MainThreadExecutor(),
                        Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3));
            }
        }
        return mInstance;
    }

    public static class MainThreadExecutor implements Executor{
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
    public Executor getMainThreadExecutor() {
        return mainThreadExecutor;
    }

    public Executor getSingleThreadExecutor() {
        return singleThreadExecutor;
    }

    public Executor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }
}
