package com.tuan.coffeemanager.feature.main.fragment.presenter;

import android.app.Activity;

import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.interactor.FirebaseAuthApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.interactor.FirebasePostDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.User;

public class SignInPresenter implements FirebaseListener.SignInListener, FirebaseListener.DataListener<User>, FirebaseListener.PostListener {

    private FirebaseAuthApp firebaseAuthApp;
    private FirebaseDataApp firebaseDataApp;
    private FirebasePostDataApp firebasePostDataApp;
    private ViewListener.ViewSignInListener viewSignInListener;
    private ViewListener.ViewDataListener viewDataListener;
    private ViewListener.ViewPostListener viewPostListener;

    public SignInPresenter(ViewListener.ViewSignInListener viewSignInListener, ViewListener.ViewDataListener viewDataListener, ViewListener.ViewPostListener viewPostListener) {
        this.viewSignInListener = viewSignInListener;
        this.viewDataListener = viewDataListener;
        this.viewPostListener = viewPostListener;
        firebaseAuthApp = new FirebaseAuthApp(this);
        firebaseDataApp = new FirebaseDataApp(this);
        firebasePostDataApp = new FirebasePostDataApp(this);
    }

    public void signIn(String email, String password, Activity activity) {
        firebaseAuthApp.signInEmail(email, password, activity);
    }

    public void gerDataUser(String id) {
        firebaseDataApp.getData(ContactBaseApp.NODE_USER, id, User.class);
    }

    public void postTokenUser(Activity activity, String id, String token) {
        firebasePostDataApp.postTokenUser(activity, id, token);
    }

    @Override
    public void signInSuccess(String id) {
        viewSignInListener.onSuccess(id);
    }

    @Override
    public void signInFailure(String error) {
        viewSignInListener.onFailure(error);
    }

    @Override
    public void getDataSuccess(User user) {
        viewDataListener.onSuccess(user);
    }

    @Override
    public void getDataFailure(String error) {
        viewDataListener.onFailure(error);
    }

    @Override
    public void postSuccess(String message) {
        viewPostListener.postSuccess(message);
    }

    @Override
    public void postFailure(String error) {
        viewPostListener.postFailure(error);
    }
}
