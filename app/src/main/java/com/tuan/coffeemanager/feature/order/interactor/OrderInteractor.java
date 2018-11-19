package com.tuan.coffeemanager.feature.order.interactor;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.feature.order.listener.IOrderListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.Order;
import com.tuan.coffeemanager.model.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class OrderInteractor {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private IOrderListener.IOrderPresenterListener iOrderPresenterListener;

    public OrderInteractor(IOrderListener.IOrderPresenterListener iOrderPresenterListener) {
        this.iOrderPresenterListener = iOrderPresenterListener;
    }

    public void getDrinkCoffee() {
        final List<Drink> drinkList = new ArrayList<>();
        //10.2.1.a Request Menu - Lệnh Query
        Query query = databaseReference.child(ConstApp.NODE_DRINK).orderByChild(ConstApp.NODE_STATUS).equalTo(true);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //10.2.1.a Request Menu - Trường hợp get menu thành công
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    drinkList.add(item.getValue(Drink.class));
                }
                iOrderPresenterListener.responseDrinkCoffeeSuccess(drinkList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //10.2.1.a Request Menu - Trường hợp get menu không thành công
                iOrderPresenterListener.responseDrinkCoffeeFailure(ConstApp.ORDER_COFFEE_E001);
            }
        });
    }

    public void orderCoffee(OrderDetail orderDetail, String tableId) {
        postOrderDetail(orderDetail, tableId);
    }

    //18.2.1 Xử lý tạo order - Xử lý tạo Order Detail (Click Save) - Request Database
    private void postOrderDetail(final OrderDetail orderDetail, final String tableId) {
        //18.2.1 Xử lý tạo order - Xử lý tạo Order Detail (Click Save) - Request Database - Kiểm tra request
        if (orderDetail == null || tableId.isEmpty()) {
            iOrderPresenterListener.responseOrderFailure(ConstApp.ORDER_COFFEE_E003);
        } else {
            //18.2.1 Xử lý tạo order - Xử lý tạo Order Detail (Click Save) - Request Database - Tạo id
            orderDetail.setOrderId(databaseReference.child(ConstApp.NODE_ORDER_DETAIL).push().getKey());
            //18.2.1 Xử lý tạo order - Xử lý tạo Order Detail (Click Save) - Request Database - Lệnh query
            databaseReference.child(ConstApp.NODE_ORDER_DETAIL).child(orderDetail.getOrderId()).setValue(orderDetail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //18.2.1 Xử lý tạo order - Xử lý tạo Order Detail (Click Save) - Request Database - Post order detail success
                            if (task.isSuccessful()) {
                                postOrder(tableId, orderDetail.getOrderId());
                            } else {
                                //18.2.1 Xử lý tạo order - Xử lý tạo Order Detail (Click Save) - Request Database - Post order detail failure
                                iOrderPresenterListener.responseOrderFailure(ConstApp.ORDER_COFFEE_E004);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //18.2.1 Xử lý tạo order - Xử lý tạo Order Detail (Click Save) - Request Database - Post order detail failure
                            iOrderPresenterListener.responseOrderFailure(ConstApp.ORDER_COFFEE_E004);
                        }
                    });
        }
    }

    //18.2.2 Xử lý tạo order - Tạo Order
    private void postOrder(String tableId, final String orderDetailId) {
        //18.2.2 Xử lý tạo order - Tạo Order
        Order order = new Order(tableId, orderDetailId);
        //18.2.2 Xử lý tạo order - Tạo Order - Request Database - Lênh query
        databaseReference.child(ConstApp.NODE_ORDER).child(tableId).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //18.2.2 Xử lý tạo order - Tạo Order - Request Database - Post order success
                if (task.isSuccessful()) {
                    iOrderPresenterListener.responseOrderSuccess(ConstApp.ORDER_COFFEE_E005);
                } else {
                    //18.2.2 Xử lý tạo order - Tạo Order - Request Database - Post order failure
                    deleteOrderDetail(orderDetailId);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //18.2.2 Xử lý tạo order - Tạo Order - Request Database - Post order failure
                deleteOrderDetail(orderDetailId);
            }
        });
    }

    //18.2.2 Xử lý tạo order - Tạo Order - Request Database - Post order failure - Lệnh Xóa
    private void deleteOrderDetail(String orderDetailId) {
        databaseReference.child(ConstApp.NODE_ORDER_DETAIL).child(orderDetailId).setValue(null);
        iOrderPresenterListener.responseOrderFailure(ConstApp.ORDER_COFFEE_E002);
    }
}
