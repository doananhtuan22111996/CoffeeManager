package com.tuan.coffeemanager.base;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tuan.coffeemanager.listener.FirebaseListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseApp {

    private static DatabaseReference databaseReference;
    private  FirebaseListener.ListDataListener firebaseListener;

    public FirebaseApp(FirebaseListener.ListDataListener firebaseListener) {
        this.firebaseListener = firebaseListener;
    }

    public static DatabaseReference newIntance() {
        if (databaseReference == null) {
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

    public <T> void getListData(String node, final Class<T> tClass) {
        databaseReference.child(node).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<T> tList = new ArrayList<>();
                for (DataSnapshot value : dataSnapshot.getChildren()) {
                    T t = value.getValue(tClass);
                    tList.add(t);
                }
                firebaseListener.getDataSuccess(tList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                firebaseListener.getDataFailure(databaseError.getMessage());
            }
        });
    }

}
