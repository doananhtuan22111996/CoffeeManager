package com.tuan.coffeemanager.feature.editProfile.presenter;

import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.User;

public class EditProfilePresenter implements FirebaseListener.DataListener<User> {

    private ViewListener.ViewDataListener viewDataListener;
    private FirebaseDataApp firebaseDataApp;

    public EditProfilePresenter(ViewListener.ViewDataListener viewDataListener) {
        this.viewDataListener = viewDataListener;
        firebaseDataApp = new FirebaseDataApp(this);
    }

    public void getDataUser(String id) {
        firebaseDataApp.getData(ContactBaseApp.NODE_USER, id, User.class);
    }

    @Override
    public void getDataSuccess(User user) {
        viewDataListener.onSuccess(user);
    }

    @Override
    public void getDataFailure(String error) {
        viewDataListener.onFailure(error);
    }
}
