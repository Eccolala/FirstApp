package com.just.firstapp;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by woops on 16-5-27.
 */
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
