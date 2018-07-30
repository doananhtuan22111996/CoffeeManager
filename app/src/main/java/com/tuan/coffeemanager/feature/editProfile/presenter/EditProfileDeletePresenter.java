package com.tuan.coffeemanager.feature.editProfile.presenter;

import android.app.Activity;

import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.interactor.FirebaseDeleteDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;

public class EditProfileDeletePresenter implements FirebaseListener.DeleteListener {

    private ViewListener.ViewDeleteListener viewDeleteListener;
    private FirebaseDeleteDataApp firebaseDeleteDataApp;

    public EditProfileDeletePresenter(ViewListener.ViewDeleteListener viewDeleteListener) {
        this.viewDeleteListener = viewDeleteListener;
        firebaseDeleteDataApp = new FirebaseDeleteDataApp(this);
    }

    public void deleteDataUser(Activity activity, String id) {
        firebaseDeleteDataApp.deleteDataStatus(activity, ContactBaseApp.NODE_USER, id);
    }

    @Override
    public void deleteSuccess(String message) {
        viewDeleteListener.deleteSuccess(message);
    }

    @Override
    public void deleteFailure(String error) {
        viewDeleteListener.deleteFailure(error);
    }
}
