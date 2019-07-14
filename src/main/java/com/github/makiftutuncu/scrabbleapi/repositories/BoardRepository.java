package com.github.makiftutuncu.scrabbleapi.repositories;

import com.github.makiftutuncu.scrabbleapi.models.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BoardRepository {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public BoardRepository() {}

    public List<Board> getActiveBoards() {
        logger.info("Getting active boards from DB");
        return new ArrayList<>();
    }

    public Board createBoard(Board board) {
        logger.info("Writing new board {} to DB", board);
        return board;
    }
}
