package com.yaopb.gifdemo;

import android.app.Application;

import com.bumptech.glide.Glide;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Glide.get(getApplicationContext());
    }
}

