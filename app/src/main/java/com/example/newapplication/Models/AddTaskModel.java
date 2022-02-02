package com.example.newapplication.Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.google.firebase.database.Exclude;

public class AddTaskModel {
    @Exclude
    private String key;

    private String taskName;
    private String taskType;
    private String priority;
    private String description;
    private String createdDate;
    private String dueDate;
    private String storeName;
    private String userName;
    private String taskStatus;
    private String taskImage1;
    private String taskImage2;
    private String taskImage3;
    private String taskImage4;

    public AddTaskModel(String taskName, String taskType, String priority, String description, String createdDate, String dueDate, String storeName, String userName, String taskStatus, String taskImage1, String taskImage2, String taskImage3, String taskImage4){
        this.taskName = taskName;
        this.taskType = taskType;
        this.priority = priority;
        this.description = description;
        this.createdDate = createdDate;
        this.dueDate = dueDate;
        this.storeName = storeName;
        this.userName = userName;
        this.taskStatus = taskStatus;
        this.taskImage1 = taskImage1;
        this.taskImage2 = taskImage2;
        this.taskImage3 = taskImage3;
        this.taskImage4 = taskImage4;
    }

    public AddTaskModel() {

    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskType() {
        return taskType;
    }

    public String getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getUserName() {
        return userName;
    }

    public String getKey() {
        return key;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTaskImage1() {
        return taskImage1;
    }

    public String getTaskImage2() {
        return taskImage2;
    }

    public String getTaskImage3() {
        return taskImage3;
    }

    public String getTaskImage4() {
        return taskImage4;
    }
}
