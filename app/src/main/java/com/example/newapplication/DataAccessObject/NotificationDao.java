package com.example.newapplication.DataAccessObject;

import com.example.newapplication.Models.NotificationModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Data Access Object for Notification
 * UNDER CONSTRUCTION
 */

public class NotificationDao {
    DatabaseReference databaseReference;

    public NotificationDao(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(NotificationModel.class.getSimpleName());
    }

    public Task<Void> addNotify(NotificationModel notificationModel){
        return databaseReference.push().setValue(notificationModel);
    }

    public Query getNotify(){
        return databaseReference.orderByKey();
    }

}
