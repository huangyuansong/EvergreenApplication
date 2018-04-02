package com.example.pc.evergreen.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 用于保存用户信息（用户名、密码、头像）
 */
public  class SaveUserInformation {


    //保存用户名、密码
    public static void saveLoginInfo(Context context,String username,String password) {
        //获取SharedPreferences对象
        SharedPreferences sharedPre = context.getSharedPreferences("config", context.MODE_PRIVATE);
        //获取Editor对象
        SharedPreferences.Editor editor = sharedPre.edit();
        //设置参数
        editor.putString("user", username);
        editor.putString("pass", password);
        //提交
        editor.commit();
    }
    //保存用户头像




    //清除保存的用户名密码
    public static void clearLoginInfo(Context context){
        //获取SharedPreferences对象
        SharedPreferences sharedPre = context.getSharedPreferences("config", context.MODE_PRIVATE);
        //获取Editor对象
        SharedPreferences.Editor editor = sharedPre.edit();
        editor.clear();
        editor.commit();
    }
}
