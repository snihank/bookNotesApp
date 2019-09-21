package com.trilogyed.book.controller;

import com.trilogyed.book.service.ServiceLayerBook;
import com.trilogyed.book.viewModel.BookViewModel;
import com.trilogyed.book.viewModel.NotesViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RefreshScope
public class BookController {

    @Autowired
    ServiceLayerBook service;

    // uri: /books ////////////////////////////////////////////////////////
    //Add a book to the database
    @RequestMapping(value = "/books", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public BookViewModel addBook(@RequestBody BookViewModel bvm){

        return service.createBook(bvm);
    }

    //Get all books
    @RequestMapping(value = "/books", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<BookViewModel> findAllBook(){

        return service.getAllBooks();
    }

    // uri: /books/{id}
    //Get a book
    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public BookViewModel findBook(@PathVariable int id){

        return service.getBook(id);
    }

    //Update a book
    @RequestMapping(value = "/books/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateBook(@RequestBody BookViewModel bvm, @PathVariable int id){

        if(id == bvm.getBookId()){
            service.updateBook(bvm);
        }else{
            //Throw exception
        }
    }

    //Delete a book
    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable int id){
        service.deleteBook(id);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // uri: books/notes //////////////////////////////////////////////////////////
    //Get all the Notes
    @RequestMapping(value = "/books/notes", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<NotesViewModel> getAllNotes(){
        return service.getAllNotes();
    }

    // uri: books/{book_id}/notes ////////////////////////////////////////////////
    //Create a Note for the specified book
    @RequestMapping(value = "/books/{book_id}/notes", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public NotesViewModel addNoteToBook(@PathVariable(name = "book_id") int bookId , @RequestBody NotesViewModel nvm){

        //validateBook(bookId);

        nvm.setBookId(bookId);

        return service.createNote(nvm);
    }

    //Get all the Notes by Book
    @RequestMapping(value = "/books/{book_id}/notes", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<NotesViewModel> getNotesByBook(@PathVariable(name = "book_id") int bookId){

        BookViewModel book = service.getBook(bookId);
        List<NotesViewModel> notesByBook = book.getNotes();

        if(notesByBook == null){
            throw new IllegalArgumentException("There is no Notes for this Book");
        }else{
            return notesByBook;
        }
    }

    // uri: books/{book_id}/notes/{note_id} ////////////////////////////////////////////////
    //Update Note
    @RequestMapping(value = "/books/{book_id}/notes/{note_id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateNote(@PathVariable(name = "book_id") int bookId , @PathVariable(name = "note_id") int noteId, @RequestBody NotesViewModel nvm){

        //validateBook(bookId);

        BookViewModel book = service.getBook(bookId);

        if(noteId == nvm.getNoteId()){
            nvm.setBookId(bookId);
            nvm.setNoteId(noteId);
            service.updateNote(noteId,nvm);
        }

    }

    //Delete Note
    @RequestMapping(value = "/books/{book_id}/notes/{note_id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteNote(@PathVariable(name = "book_id") int bookId , @PathVariable(name = "note_id") int noteId){

        BookViewModel book = service.getBook(bookId);

        service.deleteNote(noteId);
    }

    //Helper Method
//    public void validateBook(int bookId){
//        BookViewModel book = service.getBook(bookId);
//
//        if(book == null){
//            throw new IllegalArgumentException("Book not found");
//        }else{
//
//        }
//    }
}
