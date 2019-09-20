package com.trilogyed.notes.controller;

import com.trilogyed.notes.exception.NotFoundException;
import com.trilogyed.notes.service.ServiceLayer;
import com.trilogyed.notes.viewModel.NotesViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    ServiceLayer service;

    //Create Note
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotesViewModel createNote(@RequestBody @Valid NotesViewModel note){
        return service.createNote(note);
    }

    //Get All Notes
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NotesViewModel> getAllNotes(){
        return service.getAllNotes();
    }

    //Get Note
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NotesViewModel getNote(@PathVariable("id") int noteId) {
        NotesViewModel noteViewModel = service.getNote(noteId);
        if (noteViewModel == null)
            throw new NotFoundException("Cannot find" + noteId);
        return noteViewModel;
    }

    //Get Notes by Book
    @GetMapping("/book/{book_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<NotesViewModel> getNotesByBook(@PathVariable("book_id") int bookId) {
        List<NotesViewModel> nvmList = service.getNotesByBook(bookId);
        if (nvmList.size() == 0)
            throw new NotFoundException("Cannot find book" + bookId);
        return nvmList;
    }

    //Delete Note
    @DeleteMapping("/{note_id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteConsole(@PathVariable("note_id") int noteId){
        service.removeConsole(noteId);
        return "Note successfully deleted.";
    }

    //Update Note
    @PutMapping("/{note_id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateConsole(@PathVariable("note_id") int noteId, @RequestBody @Valid NotesViewModel nvm) {
        service.updateNote(nvm);
        return "Note successfully updated.";
    }

}
