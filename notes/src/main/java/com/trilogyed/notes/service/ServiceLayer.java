package com.trilogyed.notes.service;

import com.trilogyed.notes.dao.NotesDao;
import com.trilogyed.notes.model.Notes;
import com.trilogyed.notes.viewModel.NotesViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLayer {

    private NotesDao notesDao;

    @Autowired
    public ServiceLayer(NotesDao notesDao){
        this.notesDao = notesDao;
    }

    private NotesViewModel buildNotesViewModel(Notes note) {
        NotesViewModel nvm = new NotesViewModel();
        nvm.setNoteId(note.getNoteId());
        nvm.setBookId(note.getBookId());
        nvm.setNote(note.getNote());

        return nvm;
    }


    public NotesViewModel createNote(NotesViewModel nvm) {
        Notes note = new Notes();
        note.setBookId(nvm.getBookId());
        note.setNote(nvm.getNote());
        note =  notesDao.createNote(note);

        nvm.setNoteId(note.getNoteId());
        return nvm;
    }



    public void updateNote(NotesViewModel nvm)
    {
        Notes note = new Notes();
        note.setNoteId(nvm.getNoteId());
        note.setBookId(nvm.getBookId());
        note.setNote(nvm.getNote());

        notesDao.updateNote(note);
    }



    public NotesViewModel getNote(int note_id) {
        Notes note =  notesDao.getNote(note_id);
        if(note==null){
            throw new NumberFormatException("Cannot find id " + note_id);
        }
        return buildNotesViewModel(note);
    }



    public List<NotesViewModel> getAllNotes() {
        List<Notes> note = notesDao.getAllNotes();
        List<NotesViewModel> nList = new ArrayList<>();
        for (Notes n : note) {
            NotesViewModel nvm = buildNotesViewModel(n);
            nList.add(nvm);
        }
        return nList;
    }



    public void removeConsole(int note_id)
    {
        notesDao.deleteNote(note_id);
    }


    public List<NotesViewModel> getNotesByBook(int bookId) {
        List<Notes> nList = notesDao.getNotesByBook(bookId);
        List<NotesViewModel> nvmList = new ArrayList<>();

        for (Notes n : nList) {
            nvmList.add(buildNotesViewModel(n));
        }
        if (nList.size() == 0)
            return null;
        else
            return nvmList;
    }
}
