package com.dazhi.renzhengtong.loading;

import android.content.Context;
import android.content.SharedPreferences;

import com.dazhi.renzhengtong.user.UserInfo;
import com.google.gson.Gson;

/**
 * Created by Admin on 2018/1/29 0029.
 */

public class SystemInfoManager {

    public static void saveInfo(Context context,SystemInfo user){
        Gson gson  =  new Gson();
        String json = gson.toJson(user);
        SharedPreferences sharedPreferences = context.getSharedPreferences("renzhengtong",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("systemInfo",json);
        editor.commit();
    }

    public static SystemInfo getInfo(Context context){
        Gson gson  =  new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences("renzhengtong",Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("systemInfo","");
        if (json.equals("")){
            return null;
        }
        try {
            SystemInfo info = gson.fromJson(json,SystemInfo.class);
            return info;
        }catch (Exception e){
            return null;
        }

    }

    public static void clearInfo(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("renzhengtong",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("systemInfo","");
        editor.commit();
    }

}
