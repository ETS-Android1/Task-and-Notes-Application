package com.example.newapplication.DataAccessObject;

import com.example.newapplication.Models.AddNotesModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class AddNotesDao {

    /**
     * Data Access Object for Adding Notes in Firebase RealTime Database
     */

    DatabaseReference databaseReference;

    public AddNotesDao(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(AddNotesModel.class.getSimpleName());
    }

    public Task<Void> addNotes(AddNotesModel addNotesModel){
        return databaseReference.push().setValue(addNotesModel);
    }

    public Task<Void> update(String key, HashMap<String,Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key){
        return databaseReference.child(key).removeValue();
    }

    public Query get(){
        return databaseReference.orderByKey();
    }

    public Query display(String key){
        return databaseReference.child(key);
    }

}
