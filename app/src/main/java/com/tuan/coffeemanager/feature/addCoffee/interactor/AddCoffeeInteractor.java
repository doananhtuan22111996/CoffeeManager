package com.tuan.coffeemanager.feature.addCoffee.interactor;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.feature.addCoffee.listener.IAddCoffeeListener;
import com.tuan.coffeemanager.model.Drink;

import java.util.UUID;

public class AddCoffeeInteractor {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private IAddCoffeeListener.IAddCoffeePresenterListener iAddCoffeePresenterListener;
    private Drink drink = null;

    public AddCoffeeInteractor(IAddCoffeeListener.IAddCoffeePresenterListener iAddCoffeePresenterListener) {
        this.iAddCoffeePresenterListener = iAddCoffeePresenterListener;
    }

    public void addCoffee(Drink drink, Uri uri) {
        this.drink = drink;
        if (uri != null) {
            postImageCoffee(uri);
        } else {
            postCoffee(drink);
        }
    }

    private void postImageCoffee(Uri uri) {
        final String uuid = UUID.randomUUID().toString();
        storageReference.child("images/" + uuid).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                drink.setUuid(uuid);
                getUrlImageCoffee(uuid);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iAddCoffeePresenterListener.responseFailure(ConstApp.ADD_COFFEE_E001);
            }
        });
    }

    private void getUrlImageCoffee(final String uuid) {
        storageReference.child("images/" + uuid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                drink.setUrl(uri.toString());
                postCoffee(drink);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iAddCoffeePresenterListener.responseFailure(ConstApp.ADD_COFFEE_E002);
            }
        });
    }

    private void postCoffee(Drink drink) {
        String key = databaseReference.child(ConstApp.NODE_DRINK).push().getKey();
        drink.setId(key);
        databaseReference.child(ConstApp.NODE_DRINK).child(key != null ? key : "").setValue(drink).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    iAddCoffeePresenterListener.responseSuccess(ConstApp.ADD_COFFEE_E004);
                } else {
                    iAddCoffeePresenterListener.responseFailure(ConstApp.ADD_COFFEE_E003);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iAddCoffeePresenterListener.responseFailure(ConstApp.ADD_COFFEE_E003);
            }
        });
    }
}
