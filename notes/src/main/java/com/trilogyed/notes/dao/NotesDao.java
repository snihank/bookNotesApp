package com.trilogyed.notes.dao;

import com.trilogyed.notes.model.Notes;

import java.util.List;

public interface NotesDao {

    Notes createNote(Notes note);

    Notes getNote(int noteId);

    List<Notes> getAllNotes();

    void updateNote(Notes note);

    void deleteNote(int noteId);

    void deleteNoteByBookId(int bookId);

    List<Notes> getNotesByBook(int bookId);
}
