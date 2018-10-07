package com.tuan.coffeemanager.interactor;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.Order;
import com.tuan.coffeemanager.model.OrderDetail;
import com.tuan.coffeemanager.model.Table;
import com.tuan.coffeemanager.model.User;

public class FirebasePostDataApp {

    private FirebaseListener.PostListener postListener;
    private static DatabaseReference databaseReference;

    public FirebasePostDataApp(FirebaseListener.PostListener postListener) {
        this.postListener = postListener;
    }

    private static void newInstance() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void postDataTable(final Activity activity, Table table){
        if (databaseReference == null) {
            newInstance();
        }
        String key = databaseReference.child(ConstApp.NODE_TABLE).push().getKey();
        table.setId(key);
        databaseReference.child(ConstApp.NODE_TABLE).child(table.getId()).setValue(table).addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    postListener.postSuccess(activity.getString(R.string.text_message_post_success));
                } else {
                    postListener.postFailure(task.getException().getMessage());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postListener.postFailure(e.getMessage());
            }
        });
    }

    public void postDataUser(final Activity activity, User user) {
        if (databaseReference == null) {
            newInstance();
        }
        databaseReference.child(ConstApp.NODE_USER).child(user.getId()).setValue(user).addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    postListener.postSuccess(activity.getString(R.string.text_message_post_success));
                } else {
                    postListener.postFailure(task.getException().getMessage());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postListener.postFailure(e.getMessage());
            }
        });
    }

    public void postDataDrink(final Activity activity, Drink drink) {
        if (databaseReference == null) {
            newInstance();
        }
        String key = databaseReference.child(ConstApp.NODE_DRINK).push().getKey();
        drink.setId(key);
        databaseReference.child(ConstApp.NODE_DRINK).child(key).setValue(drink).addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    postListener.postSuccess(activity.getString(R.string.text_message_post_success));
                } else {
                    postListener.postFailure(task.getException().getMessage());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postListener.postFailure(e.getMessage());
            }
        });
    }

    public void postDataOrderDetail(final Activity activity, final OrderDetail orderDetail, final String table_id) {
        if (databaseReference == null) {
            newInstance();
        }
        if (orderDetail == null || table_id.isEmpty()) {
            postListener.postFailure(activity.getString(R.string.text_message_post_failure));
        } else {
            final String key = databaseReference.child(ConstApp.NODE_ORDER_DETAIL).push().getKey();
            orderDetail.setOrder_detail_id(key);
            databaseReference.child(ConstApp.NODE_ORDER_DETAIL).child(key).setValue(orderDetail).addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        postDataOrder(activity, table_id, key);
                    } else {
                        postListener.postFailure(task.getException().getMessage());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    postListener.postFailure(e.getMessage());
                }
            });
        }
    }

    public void editDataDrink(final Activity activity, Drink drink) {
        if (databaseReference == null) {
            newInstance();
        }
        databaseReference.child(ConstApp.NODE_DRINK).child(drink.getId()).setValue(drink).addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    postListener.postSuccess(activity.getString(R.string.text_message_edit_success));
                } else {
                    postListener.postFailure(task.getException().getMessage());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postListener.postFailure(e.getMessage());
            }
        });
    }

    private void postDataOrder(final Activity activity, String table_id, String order_detail_id) {
        if (databaseReference == null) {
            newInstance();
        }
        Order order = new Order(table_id, order_detail_id);
        databaseReference.child(ConstApp.NODE_ORDER).child(table_id).setValue(order).addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                postListener.postSuccess(activity.getString(R.string.text_message_post_success));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postListener.postFailure(e.getMessage());
            }
        });
    }

    public void editOrderDetail(final Activity activity, OrderDetail orderDetail) {
        if (databaseReference == null) {
            newInstance();
        }
        databaseReference.child(ConstApp.NODE_ORDER_DETAIL).child(orderDetail.getOrder_detail_id()).setValue(orderDetail).addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    postListener.postSuccess(activity.getString(R.string.text_message_post_success));
                } else {
                    postListener.postFailure(activity.getString(R.string.text_message_post_failure));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postListener.postFailure(e.getMessage());
            }
        });
    }

    public void postTokenUser(final Activity activity, String id,  String token) {
        if (databaseReference == null) {
            newInstance();
        }
        databaseReference.child(ConstApp.NODE_USER).child(id).child("token").setValue(token).addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    postListener.postSuccess(activity.getString(R.string.text_message_post_success));
                } else {
                    postListener.postFailure(activity.getString(R.string.text_message_post_failure));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postListener.postFailure(e.getMessage());
            }
        });
    }
}
