package com.tuan.coffeemanager.feature.featureManager.employeeManager.presenter;

import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.User;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManagerPresenter implements FirebaseListener.ListDataListener<User> {

    private ViewListener.ViewListDataListener viewListDataListener;
    private FirebaseDataApp firebaseDataApp;

    public EmployeeManagerPresenter(ViewListener.ViewListDataListener viewListDataListener) {
        this.viewListDataListener = viewListDataListener;
        this.firebaseDataApp = new FirebaseDataApp(this);
    }

    public void getDataUser() {
        firebaseDataApp.getListData(ConstApp.NODE_USER, User.class);
    }

    @Override
    public void getDataSuccess(List<User> users) {
        List<User> userList = new ArrayList<>();
        for (User user : users){
            if (user.getPosition().equals(ConstApp.EMPLOYEE) && user.getStatus()){
                userList.add(user);
            }
        }
        viewListDataListener.onSuccess(userList);
    }

    @Override
    public void getDataFailure(String error) {
        viewListDataListener.onFailure(error);
    }
}
