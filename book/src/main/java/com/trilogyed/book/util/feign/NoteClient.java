package com.trilogyed.book.util.feign;

import com.trilogyed.book.viewModel.NotesViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "notes-service")
public interface NoteClient {

    //Create note
    @RequestMapping(value = "/notes", method = RequestMethod.POST)
    public NotesViewModel createNote(@RequestBody NotesViewModel nvm);

    //Get all Notes by book
    @RequestMapping(value = "/notes/book/{book_id}", method = RequestMethod.GET)
    public List<NotesViewModel> getNotesByBook(@PathVariable(name = "book_id") int bookId);

    //Get all Notes
    @RequestMapping(value = "/notes", method = RequestMethod.GET)
    public List<NotesViewModel> getAllNotes();

    //Update Note
    @RequestMapping(value = "/notes/{note_id}", method = RequestMethod.PUT)
    public void updateNote(@PathVariable(name = "note_id") int noteId, @RequestBody NotesViewModel nvm);

    //Delete Note
    @RequestMapping(value = "/notes/{note_id}", method = RequestMethod.DELETE)
    public void deleteNote(@PathVariable(name = "note_id") int noteId);
}
