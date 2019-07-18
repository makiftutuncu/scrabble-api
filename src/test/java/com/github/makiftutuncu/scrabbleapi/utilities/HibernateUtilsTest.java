package com.github.makiftutuncu.scrabbleapi.utilities;

import com.github.makiftutuncu.scrabbleapi.SpringTest;
import com.github.makiftutuncu.scrabbleapi.TestData;
import com.github.makiftutuncu.scrabbleapi.models.Board;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.NestedExceptionUtils;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;

public class HibernateUtilsTest extends SpringTest {
    @Before public void truncate() {
        TestData.truncateBoards();
    }

    @Test public void testWithSession() {
        assertEquals(
            HibernateUtils.withSession(session -> {
                Query<String> query = session.createQuery("SELECT w.word FROM Word w WHERE id = 1", String.class);
                return query.uniqueResult();
            }),
            "aba"
        );
    }

    @Test(expected = ScrabbleException.class)
    public void testWithSessionThrowing() {
        HibernateUtils.withSession(session -> { throw new ScrabbleException("test"); });
    }

    @Test public void testWithTransaction() {
        Board board = new Board("test-board", 3);

        assertEquals(
            HibernateUtils.withTransaction(session -> {
                session.save(board);
                Query<Board> query = session.createQuery("SELECT b FROM Board b WHERE b.name = 'test-board'", Board.class);
                return query.uniqueResult();
            }),
            board
        );
    }

    @Test public void testWithTransactionRollback() {
        Board board1 = new Board("test-board-1", 3);
        Board board2 = new Board("test-board-2", 3);

        try {
            HibernateUtils.withTransaction(session -> {
                session.save(board1);
                session.save(board2);
                session.save(board1);
                return "OK";
            });
        } catch (Exception e) {
            Throwable rootCause = NestedExceptionUtils.getRootCause(e);
            assertTrue(rootCause instanceof SQLException);
            assertTrue(rootCause.getMessage().contains("already exists"));

            assertEquals(
                HibernateUtils.withSession(session -> {
                    Query<Board> query = session.createQuery("SELECT b FROM Board b WHERE b.name = 'test-board-1'", Board.class);
                    return query.uniqueResult();
                }),
                board1
            );
            assertEquals(
                HibernateUtils.withSession(session -> {
                    Query<Board> query = session.createQuery("SELECT b FROM Board b WHERE b.name = 'test-board-2'", Board.class);
                    return query.uniqueResultOptional();
                }),
                Optional.empty()
            );
        }
    }
}
