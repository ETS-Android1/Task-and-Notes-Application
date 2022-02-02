package com.example.newapplication.DataAccessObject;

import com.example.newapplication.Models.AddTaskModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

/**
 * Data Access Object for Adding Tasks in Firebase RealTime Database
 */

public class AddTaskDAO {

    //Data Access Object

    DatabaseReference databaseReference;

    public AddTaskDAO(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(AddTaskModel.class.getSimpleName());
    }

    public Task<Void> add(AddTaskModel addTask){
        return databaseReference.push().setValue(addTask);
    }

    public Task<Void> update(String key, HashMap<String,Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key){
        return databaseReference.child(key).removeValue();
    }

    public Query display(String key){
        return databaseReference.child(key);
    }

    public Query get(){
        return databaseReference.orderByKey();
    }

}
