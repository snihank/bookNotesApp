package com.trilogyed.book.service;

import com.trilogyed.book.dao.BookDao;
import com.trilogyed.book.dao.BookDaoJdbcTemplateImpl;
import com.trilogyed.book.model.Book;
import com.trilogyed.book.util.feign.NoteClient;
import com.trilogyed.book.viewModel.BookViewModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ServiceLayerBookTest {

    ServiceLayerBook service;
    BookDao bookDao;
    NoteClient client;



    @Before
    public void setUp() throws Exception {

        setUpBookDaoMock();
        setUpNoteClientMock();


        service = new ServiceLayerBook(bookDao, client);
    }

    @Test
    public void createBook() {
    }

    public void setUpBookDaoMock(){
        bookDao = mock(BookDaoJdbcTemplateImpl.class);

        //Book to return
        Book book = new Book();

        book.setBookId(1);
        book.setTitle("300");
        book.setAuthor("Frank Miller");

        //caller Book
        Book bookC = new Book();

        bookC.setTitle("300");
        bookC.setAuthor("Frank Miller");

        //mock method
        doReturn(book).when(bookDao).createBook(bookC);


    }

    public void setUpNoteClientMock(){
        client = mock(NoteClient.class);
    }
}