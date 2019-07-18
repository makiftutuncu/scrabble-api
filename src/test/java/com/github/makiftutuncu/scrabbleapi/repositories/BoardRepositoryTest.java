package com.github.makiftutuncu.scrabbleapi.repositories;

import com.github.makiftutuncu.scrabbleapi.SpringTest;
import com.github.makiftutuncu.scrabbleapi.TestData;
import com.github.makiftutuncu.scrabbleapi.models.Board;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class BoardRepositoryTest extends SpringTest {
    private BoardRepository repository;

    @Before public void truncate() {
        TestData.truncateBoards();
        this.repository = (BoardRepository) wac.getBean("boardRepository");
    }

    @Test public void getActiveBoardsWithNoBoards() {
        assertEquals(repository.getActiveBoards(), Collections.emptyList());
    }

    @Test public void getActiveBoardsWithSomeBoards() {
        Board board1 = new Board("test-board-1", 3);
        Board board2 = new Board("test-board-2", 3);
        board2.deactivate();

        TestData.insertBoard(board1);
        TestData.insertBoard(board2);

        assertEquals(repository.getActiveBoards(), Collections.singletonList(board1));
    }

    @Test public void getBoardThatDoesNotExist() {
        assertEquals(repository.getBoard(-1), Optional.empty());
    }

    @Test public void getBoardWhenItIsActive() {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        assertEquals(repository.getBoard(board.getId(), true), Optional.of(board));
    }

    @Test public void getBoardWhenItIsNotActive() {
        Board board = new Board("test-board", 3);
        board.deactivate();
        TestData.insertBoard(board);

        assertEquals(repository.getBoard(board.getId(), true), Optional.empty());
    }

    @Test public void getBoardEvenWhenItIsNotActive() {
        Board board = new Board("test-board", 3);
        board.deactivate();
        TestData.insertBoard(board);

        assertEquals(repository.getBoard(board.getId(), false), Optional.of(board));
    }
}
