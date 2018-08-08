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
import com.tuan.coffeemanager.model.DrinkOrder;
import com.tuan.coffeemanager.model.OrderBartender;
import com.tuan.coffeemanager.model.OrderDetail;

public class OrderDetailBartenderPresenter {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private ViewListener.ViewDeleteListener viewDeleteListener;

    public OrderDetailBartenderPresenter(ViewListener.ViewDeleteListener viewDeleteListener) {
        this.viewDeleteListener = viewDeleteListener;
    }

    public void doneBill(final Activity activity, OrderBartender orderBartender) {
        orderBartender.setStatus(false);
        for (DrinkOrder drinkOrder : orderBartender.getDrinkOrderList()) {
            drinkOrder.setIsStatus_detail(true);
        }
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder_detail_id(orderBartender.getOrder_detail_id());
        orderDetail.setDate(orderBartender.getDate());
        orderDetail.setDrinkOrderList(orderBartender.getDrinkOrderList());
        orderDetail.setIsStatus(orderBartender.getStatus());
        orderDetail.setUser_id(orderBartender.getUser_id());
        databaseReference.child(ContactBaseApp.NODE_ORDER_DETAIL).child(orderDetail.getOrder_detail_id()).setValue(orderDetail).addOnCompleteListener(activity, new OnCompleteListener<Void>() {
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
