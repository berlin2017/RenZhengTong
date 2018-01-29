package com.dazhi.renzhengtong.user;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by Admin on 2018/1/29 0029.
 */

public class UserManager {

    public static void saveUser(Context context,UserInfo user){
        Gson gson  =  new Gson();
        String json = gson.toJson(user);
        SharedPreferences sharedPreferences = context.getSharedPreferences("renzhengtong",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userinfo",json);
        editor.commit();
    }

    public static UserInfo getUser(Context context){
        Gson gson  =  new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences("renzhengtong",Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("userinfo","");
        if (json.equals("")){
            return null;
        }
        try {
            UserInfo info = gson.fromJson(json,UserInfo.class);
            return info;
        }catch (Exception e){
            return null;
        }

    }

    public static void clearUser(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("renzhengtong",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userinfo","");
        editor.commit();
    }

}
