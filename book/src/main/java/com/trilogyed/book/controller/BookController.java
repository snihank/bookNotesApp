package com.trilogyed.book.controller;

import com.trilogyed.book.service.ServiceLayerBook;
import com.trilogyed.book.viewModel.BookViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
