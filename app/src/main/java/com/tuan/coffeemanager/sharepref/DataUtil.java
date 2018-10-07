package com.tuan.coffeemanager.sharepref;

import android.content.Context;
import android.content.SharedPreferences;

import com.tuan.coffeemanager.constant.ConstApp;

public class DataUtil {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static SharedPreferences initPref(Context context) {
        sharedPreferences = context.getSharedPreferences(ConstApp.SHARED_PREF, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    private static SharedPreferences.Editor initEdit() {
        editor = sharedPreferences.edit();
        return editor;
    }

    private static void newInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = initPref(context);
        }
        if (editor == null) {
            editor = initEdit();
        }
    }

    public static void setIdUser(Context context, String id) {
        if (editor == null) {
            newInstance(context);
        }
        editor.putString(ConstApp.ID_USER, id);
        editor.apply();
    }

    public static void setNameUser(Context context, String name) {
        if (editor == null) {
            newInstance(context);
        }
        editor.putString(ConstApp.NAME_USER, name);
        editor.apply();
    }

    public static String getIdUser(Context context) {
        if (sharedPreferences == null) {
            initPref(context);
        }
        return sharedPreferences.getString(ConstApp.ID_USER, "");
    }

    public static String getNameUser(Context context) {
        if (sharedPreferences == null) {
            initPref(context);
        }
        return sharedPreferences.getString(ConstApp.NAME_USER, "");
    }

    public static void setPosition(Context context, String position){
        if (editor == null) {
            newInstance(context);
        }
        editor.putString(ConstApp.POSITION_USER, position);
        editor.apply();
    }

    public static String getPisitionUser(Context context) {
        if (sharedPreferences == null) {
            initPref(context);
        }
        return sharedPreferences.getString(ConstApp.POSITION_USER, "");
    }
}
