package com.tuan.coffeemanager.base;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tuan.coffeemanager.feature.coffeedetail.presenter.CoffeeDetailPresenter;
import com.tuan.coffeemanager.listener.FirebaseListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDataApp {

    private static DatabaseReference databaseReference;
    private FirebaseListener.ListDataListener listDataListener;
    private FirebaseListener.DataListener dataListener;

    public FirebaseDataApp(FirebaseListener.ListDataListener listDataListener) {
        this.listDataListener = listDataListener;
    }

    public FirebaseDataApp(FirebaseListener.DataListener dataListener) {
        this.dataListener = dataListener;
    }

    private static void newIntance() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public <T> void getListData(String node, final Class<T> tClass) {
        if (databaseReference == null) {
            newIntance();
        }
        databaseReference.child(node).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<T> tList = new ArrayList<>();
                for (DataSnapshot value : dataSnapshot.getChildren()) {
                    T t = value.getValue(tClass);
                    tList.add(t);
                }
                listDataListener.getDataSuccess(tList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listDataListener.getDataFailure(databaseError.getMessage());
            }
        });
    }

    public <T> void getData(String nodeParent, String nodeChild, final Class<T> tClass) {
        if (databaseReference == null) {
            newIntance();
        }
        databaseReference.child(nodeParent).child(nodeChild).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataListener.getDataSuccess(dataSnapshot.getValue(tClass));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dataListener.getDataFailure(databaseError.getMessage());
            }
        });
    }

}
