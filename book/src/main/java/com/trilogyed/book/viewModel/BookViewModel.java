package com.trilogyed.book.viewModel;

import java.util.List;
import java.util.Objects;

public class BookViewModel {
    private int bookId;
    private String title;
    private String author;
    private List<NotesViewModel> notes;

    //getters and setters

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<NotesViewModel> getNotes() {
        return notes;
    }

    public void setNotes(List<NotesViewModel> notes) {
        this.notes = notes;
    }

    //equals and hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookViewModel that = (BookViewModel) o;
        return getBookId() == that.getBookId() &&
                getTitle().equals(that.getTitle()) &&
                getAuthor().equals(that.getAuthor()) &&
                getNotes().equals(that.getNotes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookId(), getTitle(), getAuthor(), getNotes());
    }
}
