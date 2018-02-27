package com.dazhi.renzhengtong;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

/**
 * Created by mac on 2018/1/25.
 */

public class MyApplication extends LitePalApplication {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        Fresco.initialize(getApplicationContext());
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        LitePal.initialize(this);

//        FunctionOptions options = new FunctionOptions.Builder()
//                .setType(FunctionConfig.TYPE_IMAGE).setCompress(true).setGrade(Luban.THIRD_GEAR).create();
//        PictureConfig.getInstance().init(options);
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}

