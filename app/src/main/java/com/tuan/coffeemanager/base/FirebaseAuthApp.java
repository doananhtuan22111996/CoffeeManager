package com.tuan.coffeemanager.base;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.model.User;

public class FirebaseAuthApp {

    private static FirebaseAuth firebaseAuth;
    private FirebaseListener.SignUpListener signUpListener;
    private FirebaseListener.SignInListener signInListener;

    public FirebaseAuthApp(FirebaseListener.SignUpListener signUpListener) {
        this.signUpListener = signUpListener;
    }

    public FirebaseAuthApp(FirebaseListener.SignInListener signInListener) {
        this.signInListener = signInListener;
    }

    public static FirebaseAuth newInstance() {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    public void signUpEmail(String email, String password, Activity activity) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User();
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    user.setId(firebaseUser.getUid());
                    user.setName(firebaseUser.getDisplayName());
                    user.setEmail(firebaseUser.getEmail());
                    Log.d("userrrrrrrrrr", firebaseUser.getUid());
                    signUpListener.signUpSuccess(user);
                } else {
                    signUpListener.signUpFailure(task.getException().getMessage());
                }
            }
        });
    }

}
