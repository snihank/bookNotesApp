package com.trilogyed.notes.model;

import java.util.Objects;

public class Notes {
    private int noteId;
    private int bookId;
    private String note;

    public Notes(){

    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notes)) return false;
        Notes notes = (Notes) o;
        return getNoteId() == notes.getNoteId() &&
                getBookId() == notes.getBookId() &&
                getNote().equals(notes.getNote());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNoteId(), getBookId(), getNote());
    }
}
