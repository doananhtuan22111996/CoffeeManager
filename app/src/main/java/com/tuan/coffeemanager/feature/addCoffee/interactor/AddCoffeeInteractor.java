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

    //5. Xử lý thêm coffee
    public void addCoffee(Drink drink, Uri uri) {
        this.drink = drink;
        if (uri != null) {
            postImageCoffee(uri);
        } else {
            postCoffee(drink);
        }
    }

    //5.a Post image coffee
    private void postImageCoffee(Uri uri) {
        //Tạo id image
        final String uuid = UUID.randomUUID().toString();
        //Post image
        storageReference.child("images/" + uuid).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Post image success
                drink.setUuid(uuid);
                getUrlImageCoffee(uuid);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Post image failure
                iAddCoffeePresenterListener.responseFailure(ConstApp.ADD_COFFEE_E001);
            }
        });
    }

    //5.b Get url image
    private void getUrlImageCoffee(final String uuid) {
        //Get url
        storageReference.child("images/" + uuid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //Get image success
                drink.setUrl(uri.toString());
                postCoffee(drink);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Get image failure
                iAddCoffeePresenterListener.responseFailure(ConstApp.ADD_COFFEE_E002);
            }
        });
    }

    //5.c Post coffee
    private void postCoffee(Drink drink) {
        //Tạo id
        String key = databaseReference.child(ConstApp.NODE_DRINK).push().getKey();
        drink.setId(key);
        //Post coffee
        databaseReference.child(ConstApp.NODE_DRINK).child(key != null ? key : "").setValue(drink).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //Post coffee success
                    iAddCoffeePresenterListener.responseSuccess(ConstApp.ADD_COFFEE_E004);
                } else {
                    //Post coffee failure
                    iAddCoffeePresenterListener.responseFailure(ConstApp.ADD_COFFEE_E003);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Post coffee failure
                iAddCoffeePresenterListener.responseFailure(ConstApp.ADD_COFFEE_E003);
            }
        });
    }
}
