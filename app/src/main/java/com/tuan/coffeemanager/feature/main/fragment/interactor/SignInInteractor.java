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

    public void signIn(String email, String password) {
        requestSignIn(email, password);
    }

    private void requestSignIn(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                user.setId(authResult.getUser().getUid());
                getCurrentToken();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                signInPresenterListener.getFailure(ConstApp.SIGN_IN_E004);
            }
        });
    }

    private void getCurrentToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    updateCurrentToken(user.getId(), task.getResult().getToken());
                    Log.e(ConstApp.TOKEN, task.getResult().getToken());
                }else {
                    signInPresenterListener.getFailure(ConstApp.SIGN_IN_E004);
                }
            }
        });
    }

    private void updateCurrentToken(String id, String token) {
        databaseReference.child(ConstApp.NODE_USER).child(id).child(ConstApp.TOKEN).setValue(token).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    getDataUser(user.getId());
                }else {
                    signInPresenterListener.getFailure(ConstApp.SIGN_IN_E004);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                signInPresenterListener.getFailure(ConstApp.SIGN_IN_E004);
            }
        });
    }

    private void getDataUser(String id) {
        databaseReference.child(ConstApp.NODE_USER).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    user = dataSnapshot.getValue(User.class);
                    signInPresenterListener.getSuccess(user);
                }else {
                    signInPresenterListener.getFailure(ConstApp.SIGN_IN_E004);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                signInPresenterListener.getFailure(ConstApp.SIGN_IN_E004);
            }
        });
    }

}
