package com.example.newapplication.Models;

/**
 * Notification Model - UNDER CONSTRUCTION
 */

public class NotificationModel {

    String notifyTitle;
    String notifyDescription;
    String notifyDate;

    public NotificationModel(String notifyTitle,String notifyDescription,String notifyDate){
        this.notifyTitle = notifyTitle;
        this.notifyDescription = notifyDescription;
        this.notifyDate = notifyDate;
    }

    public String getNotifyTitle() {
        return notifyTitle;
    }

    public String getNotifyDescription() {
        return notifyDescription;
    }

    public String getNotifyDate() {
        return notifyDate;
    }
}
