package com.tuan.coffeemanager.feature.editProfileEmployee.presenter;

import com.tuan.coffeemanager.feature.editProfileEmployee.interactor.EditProfileEmployeeInteractor;
import com.tuan.coffeemanager.feature.editProfileEmployee.listener.IEditProfileEmployeeListener;
import com.tuan.coffeemanager.model.User;

public class EditProfileEmployeePresenter implements IEditProfileEmployeeListener.IEditProfilePresenterListener {

    private EditProfileEmployeeInteractor editProfileEmployeeInteractor;
    private IEditProfileEmployeeListener.IEditProfileViewListener editProfileViewListener;

    public EditProfileEmployeePresenter(IEditProfileEmployeeListener.IEditProfileViewListener editProfileViewListener) {
        this.editProfileViewListener = editProfileViewListener;
        editProfileEmployeeInteractor = new EditProfileEmployeeInteractor(this);
    }

    public void requestUpdateProfile(User user) {
        editProfileEmployeeInteractor.updateProfile(user);
    }

    @Override
    public void responseSuccess(String error) {
        editProfileViewListener.updateSuccess(error);
    }

    @Override
    public void responseFailure(String error) {
        editProfileViewListener.updateFailure(error);
    }
}
