package com.trilogyed.book.service;

import com.trilogyed.book.dao.BookDao;
import com.trilogyed.book.dao.BookDaoJdbcTemplateImpl;
import com.trilogyed.book.model.Book;
import com.trilogyed.book.viewModel.BookViewModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ServiceLayerBookTest {
    BookDao bookDao;

    ServiceLayerBook serviceLayerBook;


    @Before
    public void setUp() throws Exception {
        setUpBookDaoMock();
        serviceLayerBook = new ServiceLayerBook(bookDao);
    }

    private void setUpBookDaoMock(){

        bookDao = mock(BookDaoJdbcTemplateImpl.class);

        Book book = new Book();
        book.setBookId(1);
        book.setTitle("Title");
        book.setAuthor("Author");


        //this book2 won't have an id because we are
        // using this object to confirm that they are equal
        Book book2 = new Book();
        book2.setTitle("Title");
        book2.setAuthor("Author");

        doReturn(book).when(bookDao).createBook(book2);
        doReturn(book).when(bookDao).getBook(1);

        List<Book> bList = new ArrayList<>();

        bList.add(book);
        doReturn(bList).when(bookDao).getAllBooks();

        Book updateBook = new Book();

        updateBook.setBookId(2);
        updateBook.setTitle("Updated Title");
        updateBook.setAuthor("Updated Author");

        doNothing().when(bookDao).updateBook(updateBook);
        doReturn(updateBook).when(bookDao).getBook(2);

        Book deleteBook = new Book();

        deleteBook.setBookId(3);
        deleteBook.setTitle("Updated Title");
        deleteBook.setAuthor("Updated Author");

        doNothing().when(bookDao).deleteBook(3);
        doReturn(null).when(bookDao).getBook(3);

    }

    @Test
    public void createBook(){
        BookViewModel bookViewModel = new BookViewModel();
        bookViewModel.setTitle("Title");
        bookViewModel.setAuthor("Author");

        bookViewModel = serviceLayerBook.createBook(bookViewModel);

        BookViewModel fromService = serviceLayerBook.getBook(bookViewModel);
        assertEquals(bookViewModel, fromService);
    }

    @Test
    public void getBook(){
        Book book = new Book();
        book.setBookId(1);
        book.setTitle("Title");
        book.setAuthor("Author");

        BookViewModel bookViewModel = serviceLayerBook.getBook(1);

        Book book2 = new Book();

        book2.setBookId(bookViewModel.getBookId());
        book2.setTitle(bookViewModel.getTitle());
        book2.setAuthor(bookViewModel.getAuthor());

        assertEquals(book2, book);
    }

    @Test
    public void getAllBooks(){
        List<BookViewModel> fromService = serviceLayerBook.getAllBooks();

        assertEquals(1, fromService.size());
    }

    @Test
    public void updateBook(){
        BookViewModel updateBookViewModel = new BookViewModel();

        Book updateBook = new Book();
        updateBook.setBookId(2);
        updateBook.setTitle("Updated Title");
        updateBook.setAuthor("Updated Author");

        updateBookViewModel.setBookId(updateBook.getBookId());
        updateBookViewModel.setTitle(updateBook.getTitle());
        updateBookViewModel.setAuthor(updateBook.getAuthor());


    }
}