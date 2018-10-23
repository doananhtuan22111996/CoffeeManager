package com.tuan.coffeemanager.feature.editProfileEmployee.listener;

public interface IEditProfileEmployeeListener {

    interface IEditProfilePresenterListener {
        void responseSuccess(String error);

        void responseFailure(String error);
    }

    interface IEditProfileViewListener {
        void updateSuccess(String error);

        void updateFailure(String error);
    }
}
