package com.tuan.coffeemanager.widget;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyBoardUtil {

    private static InputMethodManager inputMethodManager;

    private static void newInstance(Activity activity) {
        inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
    }

    public static void hideKeyBoard(Activity activity) {
        if (inputMethodManager == null) {
            newInstance(activity);
        }
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
