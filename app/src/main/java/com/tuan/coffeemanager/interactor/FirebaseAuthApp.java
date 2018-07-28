package com.tuan.coffeemanager.interactor;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.model.User;

import java.util.Objects;

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

    private static void newInstance() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void signUpEmail(String email, String password, final String name, Activity activity) {
        if (firebaseAuth == null) {
            newInstance();
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User();
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    if (firebaseUser != null) {
                        user.setId(firebaseUser.getUid());
                        user.setEmail(firebaseUser.getEmail());
                        user.setName(name);
                    }
                    signUpListener.signUpSuccess(user);
                } else {
                    signUpListener.signUpFailure(Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                signUpListener.signUpFailure(e.getMessage());
            }
        });
    }

    public void signInEmail(String email, String password, Activity activity) {
        if (firebaseAuth == null) {
            newInstance();
        }
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    if (firebaseUser != null) {
                        signInListener.signInSuccess(firebaseAuth.getUid());
                    }
                } else {
                    signInListener.signInFailure(Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                signInListener.signInFailure(e.getMessage());
            }
        });
    }

}
