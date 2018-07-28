package com.tuan.coffeemanager.interactor;

import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.model.Order;
import com.tuan.coffeemanager.model.Table;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDataApp {

    private static DatabaseReference databaseReference;
    public static Boolean isActivity = false;
    private FirebaseListener.ListDataListener listDataListener;
    private FirebaseListener.DataListener dataListener;
    private FirebaseListener.ListDataTableOrderListener listDataTableOrderListener;

    public FirebaseDataApp(FirebaseListener.ListDataListener listDataListener) {
        this.listDataListener = listDataListener;
    }

    public FirebaseDataApp(FirebaseListener.DataListener dataListener) {
        this.dataListener = dataListener;
    }

    public FirebaseDataApp(FirebaseListener.ListDataTableOrderListener listDataTableOrderListener) {
        this.listDataTableOrderListener = listDataTableOrderListener;
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
                if (isActivity) {
                    listDataListener.getDataSuccess(tList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (isActivity) {
                    listDataListener.getDataFailure(databaseError.getMessage());
                }
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
                if (isActivity){
                    dataListener.getDataSuccess(dataSnapshot.getValue(tClass));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (isActivity){
                    dataListener.getDataFailure(databaseError.getMessage());
                }
            }
        });
    }

    public void getDataTableOrder(){
        if (databaseReference == null) {
            newIntance();
        }
        databaseReference.child(ContactBaseApp.NODE_TABLE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<Table> tableList = new ArrayList<>();
                for (DataSnapshot value : dataSnapshot.getChildren()) {
                    Table table = value.getValue(Table.class);
                    tableList.add(table);
                }
                databaseReference.child(ContactBaseApp.NODE_ORDER).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Order> orderList = new ArrayList<>();
                        for (DataSnapshot value : dataSnapshot.getChildren()) {
                            Order order = value.getValue(Order.class);
                            orderList.add(order);
                        }
                        if (isActivity) {
                            listDataTableOrderListener.getDataSuccess(tableList, orderList);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        if (isActivity) {
                            listDataTableOrderListener.getDataFailure(databaseError.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (isActivity) {
                    listDataTableOrderListener.getDataFailure(databaseError.getMessage());
                }
            }
        });
    }

}
