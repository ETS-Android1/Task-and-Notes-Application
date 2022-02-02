package com.example.newapplication.Models;

import android.content.Context;

/**
 * Recycler Model for setting Image and Text in Channels(Interest) in Profile Activity
 */

public class RecyclerModel {
    String nameP;
    int imageP;

    public RecyclerModel(String nameP,int imageP){
        this.nameP = nameP;
        this.imageP = imageP;
    }

    public String getNameP() {
        return nameP;
    }

    public int getImageP() {
        return imageP;
    }
}
