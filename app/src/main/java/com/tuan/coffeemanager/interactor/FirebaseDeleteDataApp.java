package com.tuan.coffeemanager.interactor;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
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

    public void deleteDataStatus(final Activity activity, String nodeParent, String nodeChild){
        if (databaseReference == null) {
            newInstance();
        }
        databaseReference.child(nodeParent).child(nodeChild).child("isStatus").setValue(false).addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    deleteListener.deleteSuccess(activity.getString(R.string.text_message_delete_success));
                } else {
                    deleteListener.deleteFailure(task.getException().getMessage());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                deleteListener.deleteFailure(e.getMessage());
            }
        });
    }

    public void deleteData(final Activity activity, String nodeParent, String nodeChild){
        if (databaseReference == null){
            newInstance();
        }
        databaseReference.child(nodeParent).child(nodeChild).setValue(null).addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    deleteListener.deleteSuccess(activity.getString(R.string.text_message_delete_success));
                } else {
                    deleteListener.deleteFailure(task.getException().getMessage());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                deleteListener.deleteFailure(e.getMessage());
            }
        });
    }
}
