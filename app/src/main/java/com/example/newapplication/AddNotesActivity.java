package com.example.newapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.newapplication.DataAccessObject.AddNotesDao;
import com.example.newapplication.DataAccessObject.AddTaskDAO;
import com.example.newapplication.Models.AddNotesModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Multi Purpose Activity
 * Add new Notes and updating already present notes
 */

public class AddNotesActivity extends AppCompatActivity {

    private EditText notesTittle;
    private EditText notesDescription;
    private EditText notesStore;
    private Button notesSave;

    private String key;
    private AddNotesModel list;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        getSupportActionBar().setTitle("Add Notes");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        Calendar calendar = Calendar.getInstance();

        notesTittle = (EditText) findViewById(R.id.editTextNotesTittle);
        notesDescription = (EditText) findViewById(R.id.editTextNotesDescription);
        notesStore  = (EditText) findViewById(R.id.editTextNotesStore);
        notesSave = (Button) findViewById(R.id.buttonSaveNotes);

        AddNotesDao dao = new AddNotesDao();
        key = null;

        if(getIntent().hasExtra("value")) {
            getSupportActionBar().setTitle("Update Notes");
            key = getIntent().getExtras().getString("value");
            dao.display(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list = snapshot.getValue(AddNotesModel.class);
                    if(list != null) {
                        notesTittle.setText(list.getNotesTittle());
                        notesDescription.setText(list.getNotesDescription());
                        notesStore.setText(list.getNotesStore());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        notesSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(key == null) {
                    AddNotesModel mod = new AddNotesModel(notesTittle.getText().toString(),
                            notesDescription.getText().toString(),
                            notesStore.getText().toString(),
                            mUser.getDisplayName(),
                            DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime()));
                    dao.addNotes(mod);
                    finish();
                }else if(key != null){
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("notesTittle",notesTittle.getText().toString());
                    hashMap.put("notesDescription",notesDescription.getText().toString());
                    hashMap.put("notesStore",notesStore.getText().toString());
                    hashMap.put("notesUser",list.getNotesUser());
                    hashMap.put("notesDate",list.getNotesDate());

                    dao.update(key,hashMap);
                    finish();
                }
            }
        });

    }
}