package com.trilogyed.notes.dao;

import com.trilogyed.notes.model.Notes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NotesDaoJdbcTemplateImpl implements NotesDao{


    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_NOTE_SQL =
            "insert into note (book_id, note) values (?, ?)";

    private static final String SELECT_NOTE_SQL =
            "select * from note where note_id = ?";

    private static final String SELECT_ALL_NOTES_SQL =
            "select * from note";

    private static final String UPDATE_NOTE_SQL =
            "update note set book_id = ?, note = ? where note_id = ?";

    private static final String DELETE_NOTE_SQL =
            "delete from note where note_id = ?";

    private static final String SELECT_NOTE_BY_BOOK =
            "select * from note where book_id = ?";



    @Autowired
    public NotesDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private Notes mapRowToNote(ResultSet rs, int rowNum) throws SQLException {
        Notes note = new Notes();
        note.setNoteId(rs.getInt("note_id"));
        note.setBookId(rs.getInt("book_id"));
        note.setNote(rs.getString("note"));

        return note;
    }
    @Override
    public Notes createNote(Notes note) {
        jdbcTemplate.update(
                INSERT_NOTE_SQL,
                note.getBookId(),
                note.getNote()
        );
        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        note.setNoteId(id);

        return note;
    }

    @Override
    public Notes getNote(int noteId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_NOTE_SQL, this::mapRowToNote, noteId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Notes> getAllNotes() {
        return jdbcTemplate.query(SELECT_ALL_NOTES_SQL, this::mapRowToNote);
    }

    @Override
    public void updateNote(Notes note) {
        jdbcTemplate.update(UPDATE_NOTE_SQL,
                note.getBookId(),
                note.getNote(),
                note.getNoteId()
        );
    }

    @Override
    public void deleteNote(int noteId) {
        jdbcTemplate.update(DELETE_NOTE_SQL, noteId);
    }

    @Override
    public List<Notes> getNotesByBook(int bookId) {
        return jdbcTemplate.query(SELECT_NOTE_BY_BOOK, this::mapRowToNote, bookId);
    }
}
