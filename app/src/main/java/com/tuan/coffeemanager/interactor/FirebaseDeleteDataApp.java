package com.tuan.coffeemanager.interactor;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.listener.FirebaseListener;

public class FirebaseDeleteDataApp {

    private FirebaseListener.DeleteListener deleteListener;
    private static DatabaseReference databaseReference;

    public FirebaseDeleteDataApp(FirebaseListener.DeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    private static void newInstance(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void deleteData(final Activity activity, String nodeParent, String nodeChild){
        if (databaseReference == null){
            newInstance();
        }
        databaseReference.child(nodeParent).child(nodeChild).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeValue();
                deleteListener.deleteSuccess(activity.getString(R.string.text_message_delete_success));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                deleteListener.deleteFailure(databaseError.getMessage());
            }
        });
    }
}
