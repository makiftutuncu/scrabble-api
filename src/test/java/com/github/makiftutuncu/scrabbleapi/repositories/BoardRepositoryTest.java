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
        assertEquals(Collections.emptyList(), repository.getActiveBoards());
    }

    @Test public void getActiveBoardsWithSomeBoards() {
        Board board1 = new Board("test-board-1", 3);
        Board board2 = new Board("test-board-2", 3);
        board2.deactivate();

        TestData.insertBoard(board1);
        TestData.insertBoard(board2);

        assertEquals(Collections.singletonList(board1), repository.getActiveBoards());
    }

    @Test public void getBoardThatDoesNotExist() {
        assertEquals(Optional.empty(), repository.getBoard(-1));
    }

    @Test public void getBoardWhenItIsActive() {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        assertEquals(Optional.of(board), repository.getBoard(board.getId(), true));
    }

    @Test public void getBoardWhenItIsNotActive() {
        Board board = new Board("test-board", 3);
        board.deactivate();
        TestData.insertBoard(board);

        assertEquals(Optional.empty(), repository.getBoard(board.getId(), true));
    }

    @Test public void getBoardEvenWhenItIsNotActive() {
        Board board = new Board("test-board", 3);
        board.deactivate();
        TestData.insertBoard(board);

        assertEquals(Optional.of(board), repository.getBoard(board.getId(), false));
    }
}
