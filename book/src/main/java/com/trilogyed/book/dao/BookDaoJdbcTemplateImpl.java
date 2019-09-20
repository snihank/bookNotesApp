package com.trilogyed.book.dao;

import com.trilogyed.book.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class BookDaoJdbcTemplateImpl implements BookDao {

    // Prepared statement strings
    private static final String INSERT_BOOK_SQL =
            "insert into book (title, author) values (?, ?)";

    private static final String SELECT_BOOK_SQL =
            "select * from book where book_id = ?";

    private static final String SELECT_ALL_BOOK_SQL =
            "select * from book";

    private static final String DELETE_BOOK_SQL =
            "delete from book where book_id = ?";

    private static final String UPDATE_BOOK_SQL =
            "update book set title = ?, author = ? where book_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Book getBook(int bookId) {
        try {

            return jdbcTemplate.queryForObject(SELECT_BOOK_SQL, this::mapRowToBook, bookId);

        } catch (EmptyResultDataAccessException e) {
            // if nothing is returned just catch the exception and return null
            return null;
        }
    }

        @Override
        public List<Book> getAllBooks () {
            return jdbcTemplate.query(SELECT_ALL_BOOK_SQL, this::mapRowToBook);
        }

        @Override
        public Book createBook(Book book){
            jdbcTemplate.update(INSERT_BOOK_SQL,
                    book.getTitle(),
                    book.getAuthor());

            int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);

            book.setBookId(id);

            return book;
    }

        @Override
        public void updateBook (Book book){
            jdbcTemplate.update(UPDATE_BOOK_SQL,
                    book.getTitle(),
                    book.getAuthor(),
                    book.getBookId());
        }

        @Override
        public void deleteBook ( int bookId){
            jdbcTemplate.update(DELETE_BOOK_SQL, bookId);

        }

        //HELPER METHOD

        private Book mapRowToBook (ResultSet rs,int rowNum) throws SQLException {
            Book book = new Book();
            book.setBookId(rs.getInt("book_id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));

            return book;
        }
}


