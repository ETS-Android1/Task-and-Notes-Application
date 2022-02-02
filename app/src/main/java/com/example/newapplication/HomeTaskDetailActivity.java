package com.example.newapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.newapplication.DataAccessObject.AddImageDao;
import com.example.newapplication.DataAccessObject.AddTaskDAO;
import com.example.newapplication.Models.AddTaskModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

/**
 * To Display Already Stored Data In Tasks
 */

public class HomeTaskDetailActivity extends AppCompatActivity {

    String key;

    TextView displayTaskName;
    TextView displayTaskType;
    TextView displayTaskPriority;
    TextView displayCreatedBy;
    TextView displayCreatedDate;
    TextView displayDueDate;
    TextView displayDescription;
    TextView displayStoreName;
    Button displayUpdate;
    Spinner displayTaskStatus;
    ImageView displayImage1;
    ImageView displayImage2;
    ImageView displayImage3;
    ImageView displayImage4;
    ProgressBar displayProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_task_detail);
        getSupportActionBar().setTitle("Task Details");
        Bundle extra = getIntent().getExtras();
        key =  extra.getString("value");

        displayProgressBar = (ProgressBar) findViewById(R.id.progressBarDisplay);

        displayTaskName = (TextView) findViewById(R.id.textViewTaskName);
        displayTaskType = (TextView) findViewById(R.id.textViewTaskType);
        displayTaskPriority = (TextView) findViewById(R.id.textViewTaskPriority);
        displayCreatedBy = (TextView) findViewById(R.id.textViewCreatedBy);
        displayCreatedDate = (TextView) findViewById(R.id.textViewCreatedDate);
        displayDueDate = (TextView) findViewById(R.id.textViewDueDate);
        displayDescription = (TextView) findViewById(R.id.textViewTaskDescription);
        displayStoreName = (TextView) findViewById(R.id.textViewStoreName);
        displayUpdate = (Button) findViewById(R.id.buttonTaskUpdate);
        displayTaskStatus = (Spinner) findViewById(R.id.spinnerViewStatus);
        displayImage1 = (ImageView) findViewById(R.id.imageViewDetail1);
        displayImage2 = (ImageView) findViewById(R.id.imageViewDetail2);
        displayImage3 = (ImageView) findViewById(R.id.imageViewDetail3);
        displayImage4 = (ImageView) findViewById(R.id.imageViewDetail4);

        displayUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeTaskDetailActivity.this,HomeTaskUpdateActivity.class).putExtra("valueUpdate",key));
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.recreate();
    }

    @Override
    protected void onResume() {
        super.onResume();

        AddTaskDAO addTaskDAO = new AddTaskDAO();
        AddImageDao addImageDao = new AddImageDao();

        addTaskDAO.display(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AddTaskModel list = snapshot.getValue(AddTaskModel.class);
                if(list != null) {
                    displayTaskName.setText(list.getTaskName());
                    displayTaskType.setText(list.getTaskType());
                    String tempPriority = list.getPriority();
                    String temp = (tempPriority.equals("High") ? "H" : (tempPriority.equals("Medium") ? "M" : (tempPriority.equals("Low") ? "L" : "P")));
                    displayTaskPriority.setBackgroundResource(temp.equals("H") ? R.drawable.circle_high : (temp.equals("M") ? R.drawable.circle_medium : (temp.equals("L") ? R.drawable.circle_low : R.drawable.circle_high)));
                    displayTaskPriority.setText(temp);
                    displayCreatedBy.setText(list.getUserName());
                    displayCreatedDate.setText(list.getCreatedDate());
                    displayDueDate.setText(list.getDueDate());
                    displayDescription.setText(list.getDescription());
                    displayStoreName.setText(list.getStoreName());
                    int status = (list.getTaskStatus().equals("New") ? 0 : (list.getTaskStatus().equals("Read") ? 1 : (list.getTaskStatus().equals("Executed") ? 2 : (list.getTaskStatus().equals("In Progress") ? 3 : (list.getTaskStatus().equals("Cancelled") ? 4 : (list.getTaskStatus().equals("Closed") ? 5 : 0))))));
                    displayTaskStatus.setSelection(status);
                    displayTaskStatus.setEnabled(false);

                    if(!list.getTaskImage1().equals("null")) {
                        displayProgressBar.setVisibility(View.VISIBLE);

                        addImageDao.displayImage(list.getTaskImage1()).addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).into(displayImage1);
                                displayProgressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                    if(!list.getTaskImage2().equals("null")) {
                        addImageDao.displayImage(list.getTaskImage2()).addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).into(displayImage2);
                            }
                        });
                    }
                    if(!list.getTaskImage3().equals("null")) {
                        addImageDao.displayImage(list.getTaskImage3()).addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).into(displayImage3);
                            }
                        });
                    }
                    if(!list.getTaskImage4().equals("null")) {
                        addImageDao.displayImage(list.getTaskImage4()).addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).into(displayImage4);
                            }
                        });
                    }
                } else{
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("onCancelled", String.valueOf(error));
            }
        });

    }

}