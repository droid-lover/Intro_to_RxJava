package com.jarvis.rxjavaexamples.model;
/**
 * Created by Sachin
 */

public class NoteUnique {
    int id;
    String note;

    public NoteUnique(int id, String note) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof NoteUnique)) {
            return false;
        }
        return note.equalsIgnoreCase(((NoteUnique) obj).getNote());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.note != null ? this.note.hashCode() : 0);
        return hash;
    }
}
