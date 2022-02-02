package com.example.newapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.newapplication.DataAccessObject.AddImageDao;
import com.example.newapplication.DataAccessObject.AddTaskDAO;
import com.example.newapplication.Models.AddTaskModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.example.newapplication.DataAccessObject.AddTaskDAO;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * To Update Already Stored Data In Tasks
 */

public class HomeTaskUpdateActivity extends AppCompatActivity {

    private EditText updateName;
    private EditText updateType;
    private EditText updatePriority;
    private EditText updateUser;
    private EditText updateDate;
    private EditText updateDue;
    private EditText updateStore;
    private EditText updateDescription;
    private Spinner updateStatus;
    private ImageView updateImage1;
    private ImageView updateImage2;
    private ImageView updateImage3;
    private ImageView updateImage4;
    private Button updateSubmit;
    private Button taskDelete;
    private ProgressBar updateProgressBar;

    private String imagePath1;
    private String imagePath2;
    private String imagePath3;
    private String imagePath4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_task_update);
        getSupportActionBar().setTitle("Update Task");

        Bundle extra = getIntent().getExtras();
        String key =  extra.getString("valueUpdate");

        AddTaskDAO addTaskDAO = new AddTaskDAO();
        AddImageDao addImageDao = new AddImageDao();

        updateName = (EditText) findViewById(R.id.editTextUpdateName);
        updateType = (EditText) findViewById(R.id.editTextUpdateType);
        updatePriority = (EditText) findViewById(R.id.editTextUpdatePriority);
        updateUser = (EditText) findViewById(R.id.editTextUpdateUser);
        updateDate = (EditText) findViewById(R.id.editTextUpdateDate);
        updateDue = (EditText) findViewById(R.id.editTextUpdateDue);
        updateStore = (EditText) findViewById(R.id.editTextUpdateStore);
        updateDescription = (EditText) findViewById(R.id.editTextUpdateDescription);
        updateSubmit = (Button) findViewById(R.id.buttonTaskSubmit);
        taskDelete = (Button) findViewById(R.id.buttonTaskDelete);
        updateStatus = (Spinner) findViewById(R.id.spinnerUpdateStatus);
        updateImage1 = (ImageView) findViewById(R.id.imageViewUpdate1);
        updateImage2 = (ImageView) findViewById(R.id.imageViewUpdate2);
        updateImage3 = (ImageView) findViewById(R.id.imageViewUpdate3);
        updateImage4 = (ImageView) findViewById(R.id.imageViewUpdate4);
        updateProgressBar = (ProgressBar) findViewById(R.id.progressBarUpdate);

        updateName.setKeyListener(null);

        addTaskDAO.display(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AddTaskModel list = snapshot.getValue(AddTaskModel.class);
                if(list != null) {
                    updateName.setText(list.getTaskName());
                    updateType.setText(list.getTaskType());
                    updatePriority.setText(list.getPriority());
                    updateUser.setText(list.getUserName());
                    updateDate.setText(list.getCreatedDate());
                    updateDue.setText(list.getDueDate());
                    updateStore.setText(list.getStoreName());
                    updateDescription.setText(list.getDescription());
                    int status = (list.getTaskStatus().equals("New") ? 0 : (list.getTaskStatus().equals("Read") ? 1 : (list.getTaskStatus().equals("Executed") ? 2 : (list.getTaskStatus().equals("In Progress") ? 3 : (list.getTaskStatus().equals("Cancelled") ? 4 : (list.getTaskStatus().equals("Closed") ? 5 : 0))))));
                    updateStatus.setSelection(status);

                    imagePath1 = list.getTaskImage1();
                    imagePath2 = list.getTaskImage2();
                    imagePath3 = list.getTaskImage3();
                    imagePath4 = list.getTaskImage4();

                    if(!list.getTaskImage1().equals("null")){
                        updateProgressBar.setVisibility(View.VISIBLE);
                        addImageDao.displayImage(list.getTaskImage1()).addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).into(updateImage1);
                                updateProgressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                    if(!list.getTaskImage2().equals("null")){
                        addImageDao.displayImage(list.getTaskImage2()).addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).into(updateImage2);
                            }
                        });
                    }
                    if(!list.getTaskImage3().equals("null")){
                        addImageDao.displayImage(list.getTaskImage3()).addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).into(updateImage3);
                            }
                        });
                    }
                    if(!list.getTaskImage4().equals("null")){
                        addImageDao.displayImage(list.getTaskImage4()).addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).into(updateImage4);
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updateImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),101);
            }
        });

        updateImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),102);
            }
        });

        updateImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),103);
            }
        });

        updateImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),104);
            }
        });

        updateSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("taskName",updateName.getText().toString());
                hashMap.put("taskType",updateType.getText().toString());
                hashMap.put("priority",updatePriority.getText().toString());
                hashMap.put("description",updateDescription.getText().toString());
                hashMap.put("createdDate",updateDate.getText().toString());
                hashMap.put("dueDate",updateDue.getText().toString());
                hashMap.put("storeName",updateStore.getText().toString());
                hashMap.put("userName",updateUser.getText().toString());
                String temp = updateStatus.getSelectedItem().toString();
                if(temp.equals("Task Status")){
                    hashMap.put("taskStatus","New");
                }else{
                    hashMap.put("taskStatus",temp);
                }
                hashMap.put("taskImage1",imagePath1);
                hashMap.put("taskImage2",imagePath2);
                hashMap.put("taskImage3",imagePath3);
                hashMap.put("taskImage4",imagePath4);

                addTaskDAO.update(key,hashMap);
                finish();
            }
        });

        taskDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskDAO.remove(key);
                if(updateImage1.getDrawable()!=null) {
                    addImageDao.removeImage(updateName.getText().toString(),"/101.jpg");
                }
                if(updateImage2.getDrawable()!=null) {
                    addImageDao.removeImage(updateName.getText().toString(),"/102.jpg");
                }
                if(updateImage3.getDrawable()!=null) {
                    addImageDao.removeImage(updateName.getText().toString(),"/103.jpg");
                }
                if(updateImage4.getDrawable()!=null) {
                    addImageDao.removeImage(updateName.getText().toString(),"/104.jpg");
                }
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101){
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            updateImage1.setImageBitmap(captureImage);
            imagePath1 = getImageUri(captureImage,101);
        }
        else if(requestCode == 102){
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            updateImage2.setImageBitmap(captureImage);
            imagePath2 = getImageUri(captureImage,102);
        }
        else if(requestCode == 103){
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            updateImage3.setImageBitmap(captureImage);
            imagePath3 = getImageUri(captureImage,103);
        }
        else if(requestCode == 104){
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            updateImage4.setImageBitmap(captureImage);
            imagePath4 = getImageUri(captureImage,104);
        }
    }

    public String getImageUri(Bitmap image,int req){
        String taskImagePath;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,bytes);
        byte b[] = bytes.toByteArray();
        AddImageDao addImageDao = new AddImageDao();
        taskImagePath = imagePath1;
        if(taskImagePath.equals("null")){
            taskImagePath = updateName.getText().toString()+"/"+Integer.toString(req)+".jpg";
        }
        addImageDao.addImage(b,taskImagePath);
        return taskImagePath;
    }

}