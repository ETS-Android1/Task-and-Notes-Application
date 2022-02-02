package com.example.newapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.newapplication.DataAccessObject.AddImageDao;
import com.example.newapplication.DataAccessObject.AddTaskDAO;
import com.example.newapplication.Models.AddTaskModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.Calendar;

/**
 * Add new Tasks Activity
 */

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskTitle;
    private Spinner taskType;
    private Spinner priority;
    private EditText description;
    private EditText date;
    private EditText storeName;
    private ImageView image1,image2,image3,image4;
    private Button buttonCamera,buttonSave;
    private int clickCount = 0;

    private String imagePath1 = "null";
    private String imagePath2 = "null";
    private String imagePath3 = "null";
    private String imagePath4 = "null";

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getSupportActionBar().setTitle("Add Task");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        Calendar calendar = Calendar.getInstance();

        taskTitle = (EditText) findViewById(R.id.editTextTaskTittle);
        taskType = (Spinner) findViewById(R.id.spinnerTaskType);
        priority = (Spinner) findViewById(R.id.spinnerPriority);
        description = (EditText) findViewById(R.id.editTextDescription);
        date = (EditText) findViewById(R.id.editTextDate);
        storeName = (EditText) findViewById(R.id.editTextStoreName);
        buttonCamera = (Button) findViewById(R.id.buttonTaskCamera);
        buttonSave = (Button) findViewById(R.id.buttonSaveTask);
        image1 = (ImageView) findViewById(R.id.imageView1);
        image2 = (ImageView) findViewById(R.id.imageView2);
        image3 = (ImageView) findViewById(R.id.imageView3);
        image4 = (ImageView) findViewById(R.id.imageView4);

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!taskTitle.getText().toString().isEmpty()) {
                    clickCount += 1;
                    if (clickCount == 1) {
                        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 101);
                    } else if (clickCount == 2) {
                        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 102);
                    } else if (clickCount == 3) {
                        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 103);
                    } else if (clickCount == 4) {
                        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 104);
                    } else {
                        Toast.makeText(getApplicationContext(), "Limit Reached", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Enter Task Title", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AddTaskDAO dao = new AddTaskDAO();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTaskModel mod = new AddTaskModel(taskTitle.getText().toString(),
                        taskType.getSelectedItem().toString(),
                        priority.getSelectedItem().toString(),
                        description.getText().toString(),
                        DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime()),
                        date.getText().toString(),
                        storeName.getText().toString(),
                        mUser.getDisplayName(),
                        "New",
                        imagePath1,
                        imagePath2,
                        imagePath3,
                        imagePath4);
                dao.add(mod);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101){
            Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
            image1.setImageBitmap(capturedImage);
            imagePath1 = getImageUri(capturedImage,requestCode);
        } else if(requestCode == 102){
            Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
            image2.setImageBitmap(capturedImage);
            imagePath2 = getImageUri(capturedImage,requestCode);
        } else if(requestCode == 103){
            Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
            image3.setImageBitmap(capturedImage);
            imagePath3 = getImageUri(capturedImage,requestCode);
        }else if(requestCode == 104){
            Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
            image4.setImageBitmap(capturedImage);
            imagePath4 = getImageUri(capturedImage,requestCode);
        }
    }

    //Conversion of BitMap image to Name(String/path of Image) by storing in Firebase Storage
    public String getImageUri(Bitmap image,int req){
        String taskImagePath;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,bytes);
        byte b[] = bytes.toByteArray();
        AddImageDao addImageDao = new AddImageDao();
        taskImagePath = taskTitle.getText().toString()+"/"+Integer.toString(req)+".jpg";
        addImageDao.addImage(b,taskImagePath);
        return taskImagePath;
    }

}