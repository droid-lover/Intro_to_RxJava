package com.jarvis.rxjavaexamples.model;
/**
 * Created by Sachin
 */

public class Note {
    int id;
    String note;

    public Note(int id, String note) {
        this.id = id;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
