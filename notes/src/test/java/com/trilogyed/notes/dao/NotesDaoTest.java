package com.trilogyed.notes.dao;

import com.trilogyed.notes.model.Notes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NotesDaoTest {

    @Autowired
    NotesDao notesDao;

    @Before
    public void setUp() throws Exception {
        List<Notes> note = notesDao.getAllNotes();
        for (Notes n : note) {
            notesDao.deleteNote(n.getNoteId());
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createGetDeleteNote() {
        Notes note = new Notes();
        note.setBookId(1);
        note.setNote("Test");

        note = notesDao.createNote(note);

        Notes n1 = notesDao.getNote(note.getNoteId());
        assertEquals(n1,note);

        notesDao.deleteNote(note.getNoteId());
        n1 = notesDao.getNote(note.getNoteId());
        assertNull(n1);
    }

    @Test
    public void getAllNotes() {

        Notes note = new Notes();
        note.setBookId(1);
        note.setNote("Test");
        note = notesDao.createNote(note);

        note = new Notes();
        note.setBookId(2);
        note.setNote("Test1");

        notesDao.createNote(note);

        List<Notes> nList = notesDao.getAllNotes();
        assertEquals(2, nList.size());

    }

    @Test
    public void updateNote() {
        Notes note = new Notes();
        note.setBookId(1);
        note.setNote("Test");

        note = notesDao.createNote(note);

        note.setBookId(2);
        note.setNote("Test1");

        notesDao.updateNote(note);

        Notes note1 = notesDao.getNote(note.getNoteId());
        assertEquals(note1,note);
    }


    @Test
    public void getNotesByBook() {
        Notes note = new Notes();
        note.setBookId(1);
        note.setNote("Test");

        note = notesDao.createNote(note);

        note = new Notes();
        note.setBookId(1);
        note.setNote("Test");

        note = notesDao.createNote(note);

        List<Notes> nList = notesDao.getNotesByBook(1);
        assertEquals(2,nList.size());


    }
}