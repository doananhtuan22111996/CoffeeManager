package com.tuan.coffeemanager.feature.main.fragment.interactor;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.feature.main.fragment.listener.ISignInListener;
import com.tuan.coffeemanager.model.User;

public class SignInInteractor {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private ISignInListener.ISignInPresenterListener signInPresenterListener;
    private User user = new User();

    public SignInInteractor(ISignInListener.ISignInPresenterListener signInPresenterListener) {
        this.signInPresenterListener = signInPresenterListener;
    }

    //4. Xử lý check đăng nhập
    public void signIn(String email, String password) {
        requestSignIn(email, password);
    }

    //4.a Request Sign in
    private void requestSignIn(String email, String password) {
        //Hàm đăng nhập email và  password của FirebaseAuth
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                //4.a.1 Sign in success
                user.setId(authResult.getUser().getUid()); //Get User Id
                getCurrentToken();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //4.a.2 Sign in failure
                signInPresenterListener.getFailure(ConstApp.SIGN_IN_E004);
            }
        });
    }

    //4.b Get current device token
    private void getCurrentToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    //4.b.1 Get device token success
                    updateCurrentToken(user.getId(), task.getResult().getToken());
                    Log.e(ConstApp.TOKEN, task.getResult().getToken());
                } else {
                    //4.b.2 Get device token failure
                    signInPresenterListener.getFailure(ConstApp.SIGN_IN_E006);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //4.b.2 Get device token failure
                signInPresenterListener.getFailure(ConstApp.SIGN_IN_E006);
            }
        });
    }

    //4.c Update current device token on database
    private void updateCurrentToken(String id, String token) {
        databaseReference.child(ConstApp.NODE_USER).child(id).child(ConstApp.TOKEN).setValue(token)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //4.c.1 Update token success
                            getDataUser(user.getId());
                        } else {
                            //4.c.2 Update token failure
                            signInPresenterListener.getFailure(ConstApp.SIGN_IN_E007);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //4.c.2 Update token failure
                signInPresenterListener.getFailure(ConstApp.SIGN_IN_E007);
            }
        });
    }

    //4.d Get data user
    private void getDataUser(String id) {
        databaseReference.child(ConstApp.NODE_USER).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    //4.d.1 Get data user success
                    user = dataSnapshot.getValue(User.class);
                    signInPresenterListener.getSuccess(user);
                } else {
                    //4.d.2 Get data user failure
                    signInPresenterListener.getFailure(ConstApp.SIGN_IN_E008);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //4.d.2 Get data user failure
                signInPresenterListener.getFailure(ConstApp.SIGN_IN_E008);
            }
        });
    }

}
