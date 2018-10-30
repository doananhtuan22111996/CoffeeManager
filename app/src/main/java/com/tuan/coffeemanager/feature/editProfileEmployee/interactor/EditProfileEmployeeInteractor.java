package com.tuan.coffeemanager.feature.editProfileEmployee.interactor;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.feature.editProfileEmployee.listener.IEditProfileEmployeeListener;
import com.tuan.coffeemanager.model.User;

public class EditProfileEmployeeInteractor {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private IEditProfileEmployeeListener.IEditProfilePresenterListener editProfilePresenterListener;

    public EditProfileEmployeeInteractor(IEditProfileEmployeeListener.IEditProfilePresenterListener editProfilePresenterListener) {
        this.editProfilePresenterListener = editProfilePresenterListener;
    }

    //4. Xử lý cập nhật thông tin
    //4.a Request update profile
    public void updateProfile(User user) {
        databaseReference.child(ConstApp.NODE_USER).child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //4.a.1 Update profile success
                    editProfilePresenterListener.responseSuccess(ConstApp.EDIT_PROFILE_E002);
                } else {
                    //4.a.2 Update profile failure
                    editProfilePresenterListener.responseFailure(ConstApp.EDIT_PROFILE_E001);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //4.a.2 Update profile failure
                editProfilePresenterListener.responseFailure(ConstApp.EDIT_PROFILE_E001);
            }
        });
    }
}
