package com.tuan.coffeemanager.interactor;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tuan.coffeemanager.listener.FirebaseListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDataApp {

    private static DatabaseReference databaseReference;
    public static Boolean isActivity = false;
    private FirebaseListener.ListDataListener listDataListener;
    private FirebaseListener.DataListener dataListener;
    private FirebaseListener.ListDataDoubleListener listDataDoubleListener;
    private FirebaseListener.ListDataObjectDoubleListener listDataObjectDoubleListener;

    public FirebaseDataApp(FirebaseListener.ListDataListener listDataListener) {
        this.listDataListener = listDataListener;
    }

    public FirebaseDataApp(FirebaseListener.DataListener dataListener) {
        this.dataListener = dataListener;
    }

    public FirebaseDataApp(FirebaseListener.ListDataDoubleListener listDataDoubleListener) {
        this.listDataDoubleListener = listDataDoubleListener;
    }

    public FirebaseDataApp(FirebaseListener.ListDataObjectDoubleListener listDataObjectDoubleListener) {
        this.listDataObjectDoubleListener = listDataObjectDoubleListener;
    }

    public FirebaseDataApp(FirebaseListener.DataListener dataListener, FirebaseListener.ListDataDoubleListener listDataDoubleListener) {
        this.dataListener = dataListener;
        this.listDataDoubleListener = listDataDoubleListener;
    }

    public FirebaseDataApp(FirebaseListener.ListDataListener listDataListener, FirebaseListener.ListDataDoubleListener listDataDoubleListener) {
        this.listDataListener = listDataListener;
        this.listDataDoubleListener = listDataDoubleListener;
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
                if (isActivity) {
                    dataListener.getDataSuccess(dataSnapshot.getValue(tClass));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (isActivity) {
                    dataListener.getDataFailure(databaseError.getMessage());
                }
            }
        });
    }

    public <T, K> void getListDataDouble(String firstNode, final String secondNode, final Class<T> tClass, final Class<K> kClass) {
        if (databaseReference == null) {
            newIntance();
        }
        databaseReference.child(firstNode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<T> tList = new ArrayList<>();
                for (DataSnapshot value : dataSnapshot.getChildren()) {
                    T t = value.getValue(tClass);
                    tList.add(t);
                }
                databaseReference.child(secondNode).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<K> kList = new ArrayList<>();
                        for (DataSnapshot value : dataSnapshot.getChildren()) {
                            K k = value.getValue(kClass);
                            kList.add(k);
                        }
                        if (isActivity) {
                            listDataDoubleListener.getDataSuccess(tList, kList);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        if (isActivity) {
                            listDataDoubleListener.getDataFailure(databaseError.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (isActivity) {
                    listDataDoubleListener.getDataFailure(databaseError.getMessage());
                }
            }
        });
    }

    public <T, K> void getListDataObjectDouble(String firstNode, String firstNodeChild, final String secondNode, final Class<T> tClass, final Class<K> kClass) {
        if (databaseReference == null) {
            newIntance();
        }
        databaseReference.child(firstNode).child(firstNodeChild).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final T t = dataSnapshot.getValue(tClass);
                databaseReference.child(secondNode).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<K> kList = new ArrayList<>();
                        for (DataSnapshot value : dataSnapshot.getChildren()) {
                            K k = value.getValue(kClass);
                            kList.add(k);
                        }
                        if (isActivity) {
                            listDataObjectDoubleListener.getDataSuccess(t, kList);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        if (isActivity) {
                            listDataObjectDoubleListener.getDataFailure(databaseError.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (isActivity) {
                    listDataObjectDoubleListener.getDataFailure(databaseError.getMessage());
                }
            }
        });
    }

}
