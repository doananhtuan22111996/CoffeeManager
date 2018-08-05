package com.tuan.coffeemanager.feature.featureBartender.OrderDetail.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.listener.ViewListener;

public class OrderDetailBartenderPresenter {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private ViewListener.ViewDeleteListener viewDeleteListener;

    public OrderDetailBartenderPresenter(ViewListener.ViewDeleteListener viewDeleteListener) {
        this.viewDeleteListener = viewDeleteListener;
    }

    public void doneBill(final Activity activity, String order_detail_id) {
        databaseReference.child(ContactBaseApp.NODE_ORDER_DETAIL).child(order_detail_id).child("isStatus").setValue(false).addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    viewDeleteListener.deleteSuccess(activity.getString(R.string.text_message_delete_success));
                } else {
                    viewDeleteListener.deleteFailure(task.getException().getMessage());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                viewDeleteListener.deleteFailure(e.getMessage());
            }
        });
    }
}
