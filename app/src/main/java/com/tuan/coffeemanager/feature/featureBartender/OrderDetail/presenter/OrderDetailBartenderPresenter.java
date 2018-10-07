package com.tuan.coffeemanager.feature.featureBartender.OrderDetail.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.DrinkOrder;
import com.tuan.coffeemanager.model.OrderBartender;
import com.tuan.coffeemanager.model.OrderDetail;
import com.tuan.coffeemanager.model.User;

import java.util.List;

public class OrderDetailBartenderPresenter implements FirebaseListener.ListDataListener<User> {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private ViewListener.ViewDeleteListener viewDeleteListener;
    private ViewListener.ViewDataListener viewDataListener;
    private FirebaseDataApp firebaseDataApp;

    public OrderDetailBartenderPresenter(ViewListener.ViewDeleteListener viewDeleteListener, ViewListener.ViewDataListener viewDataListener) {
        this.viewDeleteListener = viewDeleteListener;
        this.viewDataListener = viewDataListener;
        firebaseDataApp = new FirebaseDataApp(this);
    }

    public void getDataWaiter() {
        firebaseDataApp.getListData(ConstApp.NODE_USER, User.class);
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
        databaseReference.child(ConstApp.NODE_ORDER_DETAIL).child(orderDetail.getOrder_detail_id()).setValue(orderDetail).addOnCompleteListener(activity, new OnCompleteListener<Void>() {
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

    @Override
    public void getDataSuccess(List<User> users) {
        for (User user : users) {
            if (user.getEmail().equals("lady@gmail.com")) {
                viewDataListener.onSuccess(user);
                break;
            }
        }
    }

    @Override
    public void getDataFailure(String error) {
        viewDataListener.onFailure(error);
    }
}
