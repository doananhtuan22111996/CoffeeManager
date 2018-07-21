package com.tuan.coffeemanager.base;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.listener.FirebaseListener;

import java.util.UUID;

public class FirebaseStorageApp {

    private FirebaseListener.PostImageListener postImageListener;
    private static StorageReference storageReference;

    public FirebaseStorageApp(FirebaseListener.PostImageListener postImageListener) {
        this.postImageListener = postImageListener;
    }

    private static void newInstance() {
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public void postDataImage(final Activity activity, Uri uri) {
        if (storageReference == null) {
            newInstance();
        }
        final String uuid = UUID.randomUUID().toString();
        storageReference.child("images/" + uuid).putFile(uri).addOnSuccessListener(activity, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                dowloadImageUrl(activity, uuid);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postImageListener.postImageFailure(e.getMessage());
            }
        });
    }

    public void editDataImage(final Activity activity, Uri uri, final String uuid) {
        if (storageReference == null) {
            newInstance();
        }
        storageReference.child("images/" + uuid).putFile(uri).addOnSuccessListener(activity, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                dowloadImageUrl(activity, uuid);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postImageListener.postImageFailure(e.getMessage());
            }
        });
    }

    public void dowloadImageUrl(Activity activity, final String uuid) {
        if (storageReference == null) {
            newInstance();
        }
        storageReference.child("images/" + uuid).getDownloadUrl().addOnSuccessListener(activity, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                postImageListener.postImageSuccess(uuid, uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postImageListener.postImageFailure(e.getMessage());
            }
        });
    }
}
