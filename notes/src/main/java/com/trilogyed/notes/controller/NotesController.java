package com.trilogyed.notes.controller;

import com.trilogyed.notes.exception.NotFoundException;
import com.trilogyed.notes.service.ServiceLayer;
import com.trilogyed.notes.viewModel.NotesViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    ServiceLayer service;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotesViewModel createNote(@RequestBody @Valid NotesViewModel note){
        return service.createNote(note);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NotesViewModel getNote(@PathVariable("id") int note_id) {
        NotesViewModel noteViewModel = service.getNote(note_id);
        if (noteViewModel == null)
            throw new NotFoundException("Cannot find" + note_id);
        return noteViewModel;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NotesViewModel> getAllNotes(){
        return service.getAllNotes();
    }

    @GetMapping("/books/{book_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<NotesViewModel> getNotesByBook(@PathVariable("{book_id}") int book_id) {
        List<NotesViewModel> nvmList = service.getNotesByBook(book_id);
        if (nvmList.size() == 0)
            throw new NotFoundException("Cannot find book" + book_id);
        return nvmList;
    }


    @DeleteMapping("/{note_id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteConsole(@PathVariable("note_id") int note_id){
        service.removeConsole(note_id);
        return "Note successfully deleted.";
    }

    @PutMapping("/{note_id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateConsole(@PathVariable("note_id") int note_id, @RequestBody @Valid NotesViewModel nvm) {
        service.updateNote(nvm);
        return "Note successfully updated.";
    }

}
