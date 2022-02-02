package com.example.newapplication.DataAccessObject;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 *  Data Access Object for Adding Image in Firebase Storage
 */

public class AddImageDao {

    StorageReference storageReference;

    public AddImageDao(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    public UploadTask addImage(byte[] bytes,String path){
        return storageReference.child("TaskImages").child(path).putBytes(bytes);
    }

    public Task<Uri> displayImage(String path){
        return storageReference.child("TaskImages").child(path).getDownloadUrl();
    }

    public Task<Void> removeImage(String path,String childPath){
        return storageReference.child("TaskImages").child(path).child(childPath).delete();
    }

}
