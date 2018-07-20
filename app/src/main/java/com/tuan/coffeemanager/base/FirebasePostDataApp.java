package com.tuan.coffeemanager.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.User;

import java.util.Objects;

public class FirebasePostDataApp {

    private FirebaseListener.PostListener postListener;
    private static DatabaseReference databaseReference;

    public FirebasePostDataApp(FirebaseListener.PostListener postListener) {
        this.postListener = postListener;
    }

    private static void newInstance() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void postDataUser(final Activity activity, User user) {
        if (databaseReference == null) {
            newInstance();
        }
        databaseReference.child(ContactBaseApp.NODE_USER).child(user.getId()).setValue(user).addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    postListener.postSuccess(activity.getString(R.string.text_message_post_success));
                } else {
                    postListener.postFailure(task.getException().getMessage());
                }
            }
        });
    }

    public void postDataDrink(final Activity activity, Drink drink) {
        if (databaseReference == null) {
            newInstance();
        }
        String key = databaseReference.child(ContactBaseApp.NODE_DRINK).push().getKey();
        drink.setId(key);
        databaseReference.child(ContactBaseApp.NODE_DRINK).child(key).setValue(drink).addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    postListener.postSuccess(activity.getString(R.string.text_message_post_success));
                } else {
                    postListener.postFailure(task.getException().getMessage());
                }
            }
        });
    }

    public void editDataDrink(final Activity activity, Drink drink) {
        if (databaseReference == null) {
            newInstance();
        }
        databaseReference.child(ContactBaseApp.NODE_DRINK).child(drink.getId()).setValue(drink).addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    postListener.postSuccess(activity.getString(R.string.text_message_edit_success));
                } else {
                    postListener.postFailure(task.getException().getMessage());
                }
            }
        });
    }
}
