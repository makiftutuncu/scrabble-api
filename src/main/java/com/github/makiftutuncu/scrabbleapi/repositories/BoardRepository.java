package com.github.makiftutuncu.scrabbleapi.repositories;

import com.github.makiftutuncu.scrabbleapi.models.Board;
import com.github.makiftutuncu.scrabbleapi.utilities.HibernateUtils;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("boardRepository")
public class BoardRepository {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<Board> getActiveBoards() {
        logger.info("Getting active boards from DB");

        return HibernateUtils.withSession(session -> {
            Query<Board> query = session
                    .createQuery("SELECT b FROM Board b WHERE b.isActive = :isActive", Board.class)
                    .setParameter("isActive", true);

            return query.getResultList();
        });
    }

    public Optional<Board> getBoard(int id, boolean onlyActive) {
        logger.info("Getting board {} {}from DB", id, onlyActive ? "only if it is active " : "");

        return HibernateUtils.withSession(session -> {
            Query<Board> query = session
                    .createQuery("SELECT b FROM Board b WHERE b.id = :id" + (onlyActive ? " AND b.isActive = :isActive" : ""), Board.class)
                    .setParameter("id", id);

            if (onlyActive) {
                query.setParameter("isActive", true);
            }

            return query.uniqueResultOptional();
        });
    }

    public Optional<Board> getBoard(int id) {
        return getBoard(id, true);
    }
}
