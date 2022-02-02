package com.example.newapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.newapplication.Adapter.AdapterRecycler;
import com.example.newapplication.Models.RecyclerModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Profile Activity to show details about users
 */

public class ProfileActivity extends AppCompatActivity {

    private ImageView displayPictureProfile;
    private EditText nameProfile;
    private EditText emailProfile;
    private EditText phoneProfile;
    private RecyclerView recyclerView;
    private ImageView saveProfile;

    List<RecyclerModel> lt;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile Details");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        displayPictureProfile = (ImageView) findViewById(R.id.profileDisplayPic);
        nameProfile = (EditText) findViewById(R.id.profileUserName);
        emailProfile = (EditText) findViewById(R.id.profileEmail);
        phoneProfile = (EditText) findViewById(R.id.profilePhoneNum);
        saveProfile = (ImageView) findViewById(R.id.saveProfile);

        displayPictureProfile.setImageURI(mUser.getPhotoUrl());
        nameProfile.setText(mUser.getDisplayName()!= null ? mUser.getDisplayName() : "null");
        emailProfile.setText(mUser.getEmail()!= null ? mUser.getEmail() : "null");
        phoneProfile.setText(mUser.getPhoneNumber()!= null ? mUser.getPhoneNumber() : "null");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerProfileChannel);
        dataEntry();
        AdapterRecycler adapterRecycler = new AdapterRecycler(lt);

        recyclerView.setAdapter(adapterRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));

        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(nameProfile.getText().toString()).build();
                mUser.updateProfile(profileUpdates);
                //mUser.updatePhoneNumber(phoneProfile.getText().toString());
                mUser.updateEmail(emailProfile.getText().toString());
                startActivity(new Intent(ProfileActivity.this, HomeActivity.class));

            }
        });

        displayPictureProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),100);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100) {
            Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
            displayPictureProfile.setImageBitmap(capturedImage);
        }

    }


    //Channel(Interest of an Particular Profile)
    private void dataEntry(){
        lt = new ArrayList<>();

        lt.add(new RecyclerModel("Travel",R.drawable.app_travel));
        lt.add(new RecyclerModel("Coding",R.drawable.app_coding));
        lt.add(new RecyclerModel("Content Writing",R.drawable.app_writing));
        lt.add(new RecyclerModel("Photography",R.drawable.app_photo));
        lt.add(new RecyclerModel("Arts & Designing",R.drawable.app_arts));
        lt.add(new RecyclerModel("Startups",R.drawable.app_startups));
    }

}