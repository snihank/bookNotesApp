package com.trilogyed.book.dao;

import com.trilogyed.book.model.Book;
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
public class BookDaoTest {
    @Autowired
    protected BookDao bookDao;
    @Before
    public void setUp() throws Exception {
        List<Book> bList = bookDao.getAllBooks();
        bList.stream()
                .forEach(book -> bookDao.deleteBook(book.getBookId()));
    }

    @Test
    public void addGetDeleteBook() {
        Book book = new Book();
        book.setTitle("Title");
        book.setAuthor("Author");

        book= bookDao.creatBook(book);

        Book book2 = bookDao.getBook(book.getBookId());

        assertEquals(book, book2);

        bookDao.deleteBook(book.getBookId());

        book2 = bookDao.getBook(book.getBookId());

        assertNull(book2);
    }

    @Test
    public void getAllBooks(){
        Book book = new Book();

        book.setTitle("Title2");
        book.setAuthor("Author2");

        bookDao.creatBook(book);

        book = new Book();
        book.setTitle("Title3");
        book.setAuthor("Author3");

        bookDao.creatBook(book);

        List<Book> bList = bookDao.getAllBooks();

        assertEquals(bList.size(), 2);
    }

    @Test
    public void updateBook() {
        Book book = new Book();

        book.setTitle("Title");
        book.setAuthor("Author");

        book= bookDao.creatBook(book);

        book.setTitle("Update");
        book.setAuthor("Update");

        bookDao.updateBook(book);

        Book book2 = bookDao.getBook(book.getBookId());

        assertEquals(book2, book);

    }
}