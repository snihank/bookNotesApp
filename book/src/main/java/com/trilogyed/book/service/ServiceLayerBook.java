package com.trilogyed.book.service;

import com.trilogyed.book.dao.BookDao;
import com.trilogyed.book.model.Book;
import com.trilogyed.book.util.feign.NoteClient;
import com.trilogyed.book.viewModel.BookViewModel;
import com.trilogyed.book.viewModel.NotesViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServiceLayerBook {

    private BookDao bookDao;

    private final NoteClient client;

    @Autowired
    public ServiceLayerBook(BookDao bookDao, NoteClient client){
        this.bookDao = bookDao;
        this.client = client;
    }

    //uri: /books ////////////////////////////////////////////////
    //Create a book
    public BookViewModel createBook(BookViewModel bvm){

        bvm = buildBookViewModel(bookDao.createBook(buildBook(bvm)));

        List<NotesViewModel> nvmList = bvm.getNotes();

        int bookId = bvm.getBookId();

        //Set the bookId for all the Notes
        nvmList.stream().forEach(notesViewModel -> notesViewModel.setBookId(bookId));


        //Build message and Send the List to the queue

        return bvm;
    }

    //public List<NotesViewModel>

    //Get all books, with notes.
    public List<BookViewModel> getAllBooks(){

        //Get all books from database
        List<Book> bookList = bookDao.getAllBooks();

        //Get all Notes from the note-service
        List<NotesViewModel> allNotesForAllBooks = client.getAllNotes();

        //List to be returned
        List<BookViewModel> bookViewModelList = new ArrayList<>();

        //Stores all the NotesViewModel for a book
        List<NotesViewModel> notesForBook;

        for(int i = 0; i < bookList.size(); i ++){

            Book book = bookList.get(i);
            int bookId = book.getBookId();

            notesForBook =
                    allNotesForAllBooks
                    .stream()
                    .filter(notesViewModel -> notesViewModel.getBookId() == bookList.get(bookId).getBookId())
                    .collect(Collectors.toList());

            BookViewModel bvm = new BookViewModel();
            bvm.setBookId(book.getBookId());
            bvm.setAuthor(book.getAuthor());
            bvm.setTitle(book.getTitle());
            bvm.setNotes(notesForBook);

            bookViewModelList.add(bvm);
        }

        return bookViewModelList;
    }

    //uri: books/{id}/////////////////////////////////////////////////
    //Get book
    public BookViewModel getBook(int bookId){

        BookViewModel bvm = buildBookViewModel(bookDao.getBook(bookId));

        List<NotesViewModel> nvmList = client.getNotesByBook(bookId);

        bvm.setNotes(nvmList);

        return bvm;
    }

    //Update book
    public void updateBook(BookViewModel bvm){

        bookDao.updateBook(buildBook(bvm));
    }

    //Delete book
    public void deleteBook(int bookId){
        //Should delete all the books and all the notes related to it
        bookDao.deleteBook(bookId);
    }

    //Helper methods////////////////////////////////////////////////////

    public BookViewModel buildBookViewModel(Book book){
        BookViewModel bvm = new BookViewModel();

        bvm.setBookId(book.getBookId());
        bvm.setTitle(book.getTitle());
        bvm.setAuthor(book.getAuthor());

        return bvm;
    }

    public Book buildBook(BookViewModel bvm){
        Book book = new Book();

        book.setBookId(bvm.getBookId());
        book.setTitle(bvm.getTitle());
        book.setAuthor(bvm.getAuthor());

        return book;
    }
}

//bookList.stream().forEach(book -> nvmList.add(client.getNotesByBook(book.getBookId())));
//          List<List<NotesViewModel>> nvmList = new ArrayList<>();
//        //Getting all the Notes content from the list notes
//        List<String> noteContent = new ArrayList<>();
//
//        //BooksViewModels List to return
//        //List<BookViewModel> bookViewModelList = new ArrayList<>();
//
//        for(int i = 0; i < bookList.size(); i++){
//
//            Book book = bookList.get(i);
//
//            BookViewModel bvm = new BookViewModel();
//
//            nvmList.get(i).stream().forEach(note -> noteContent.add(note.getNote()));
//
//            //Creating the bvm
//            bvm.setBookId(book.getBookId());
//            bvm.setTitle(book.getTitle());
//            bvm.setAuthor(book.getAuthor());
//            bvm.setNotes(noteContent);
//
//            //Adding the bvm to the List
//            bookViewModelList.add(bvm);
//        }