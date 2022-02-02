package com.example.newapplication.Models;

import com.google.firebase.database.Exclude;

public class AddNotesModel {

    @Exclude
    private String key;

    private String notesTittle;
    private String notesDescription;
    private String notesStore;
    private String notesUser;
    private String notesDate;

    public AddNotesModel (String notesTittle, String notesDescription, String notesStore, String notesUser, String notesDate){
        this.notesTittle = notesTittle;
        this.notesDescription = notesDescription;
        this.notesStore = notesStore;
        this.notesUser = notesUser;
        this.notesDate = notesDate;
    }

    public AddNotesModel(){

    }

    public String getNotesTittle() {
        return notesTittle;
    }

    public String getNotesDescription() {
        return notesDescription;
    }

    public String getNotesStore() {
        return notesStore;
    }

    public String getNotesUser() {
        return notesUser;
    }

    public String getNotesDate() {
        return notesDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
