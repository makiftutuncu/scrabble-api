package com.github.makiftutuncu.scrabbleapi;

import com.github.makiftutuncu.scrabbleapi.models.Board;
import com.github.makiftutuncu.scrabbleapi.utilities.HibernateUtils;

public class TestData {
    public static void truncateBoards() {
        HibernateUtils.withTransaction(session -> session.createSQLQuery("TRUNCATE TABLE boards CASCADE").executeUpdate());
    }

    public static void insertBoard(Board board) {
        HibernateUtils.withTransaction(session -> session.save(board));
    }
}
