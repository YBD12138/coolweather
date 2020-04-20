package com.coolweather.android;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        LitePal.initialize(getContext());
        context = getApplicationContext();
        super.onCreate();
    }
    public static Context getContext(){
        return context;
    }
}
