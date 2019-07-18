package com.github.makiftutuncu.scrabbleapi;

import com.github.makiftutuncu.scrabbleapi.utilities.HibernateUtils;

public class TestData {
    public static void truncateBoards() {
        HibernateUtils.withTransaction(session -> session.createSQLQuery("TRUNCATE TABLE boards CASCADE").executeUpdate());
    }
}
