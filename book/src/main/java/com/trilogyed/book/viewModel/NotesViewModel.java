package com.trilogyed.book.viewModel;

import java.util.Objects;

public class NotesViewModel {
    private int noteId;
    private int bookId;
    private String note;

    //gettets and setters

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

    //equals and hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotesViewModel that = (NotesViewModel) o;
        return getNoteId() == that.getNoteId() &&
                getBookId() == that.getBookId() &&
                getNote().equals(that.getNote());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNoteId(), getBookId(), getNote());
    }
}
