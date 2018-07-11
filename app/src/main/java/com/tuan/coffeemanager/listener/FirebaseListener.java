package com.tuan.coffeemanager.listener;

import java.util.List;

public interface FirebaseListener {
    <T> void getDataSuccess(List<T> tList);

    void getDataFailure(String error);
}
