package com.tuan.coffeemanager.base;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.model.User;

public class FirebasePostDataApp {

    private static DatabaseReference databaseReference;

    public static DatabaseReference newInstance() {
        if (databaseReference == null) {
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

    public static void postDataUser(User user) {
        databaseReference.child(ContactBaseApp.NODE_USER).child(user.getId()).setValue(user);
    }
}
