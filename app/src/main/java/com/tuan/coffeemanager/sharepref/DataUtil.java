package com.tuan.coffeemanager.sharepref;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.model.User;

public class DataUtil {

    private static SharedPreferences sharedPreferences;
    private static DataUtil dataUtil = new DataUtil();

    public static DataUtil newInstance(Context context) {
        sharedPreferences = context.getSharedPreferences(ConstApp.SHARED_PREF, Context.MODE_PRIVATE);
        return dataUtil;
    }

    public void setDataUser(User user) {
        Gson gson = new Gson();
        String strUser = gson.toJson(user);
        SharedPreferences.Editor editor = sharedPreferences.edit().putString(ConstApp.NODE_USER, strUser);
        editor.apply();
    }

    public User getDataUser() {
        Gson gson = new Gson();
        String strUser = sharedPreferences.getString(ConstApp.NODE_USER, "");
        return gson.fromJson(strUser, User.class);
    }

}
