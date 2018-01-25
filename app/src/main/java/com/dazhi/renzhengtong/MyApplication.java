package com.dazhi.renzhengtong;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by mac on 2018/1/25.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(getApplicationContext());
    }
}

