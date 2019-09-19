package com.trilogyed.book.dao;

import com.trilogyed.book.model.Book;

import java.util.List;

public interface BookDao {
    Book getBook(int bookId);

    List<Book> getAllBooks ();

    Book creatBook(Book book);

    void updateBook (Book book);

    void deleteBook(int bookId);

}
