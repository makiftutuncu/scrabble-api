package com.github.makiftutuncu.scrabbleapi.models;

import com.github.makiftutuncu.scrabbleapi.utilities.ScrabbleException;
import com.github.makiftutuncu.scrabbleapi.views.AddMoveRequest;
import com.github.makiftutuncu.scrabbleapi.views.CreateBoardRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.http.HttpStatus;

import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class BoardTest {
    @Test public void createWithCustomData() {
        Board board = new Board("test-board", 5);

        assertEquals(board.getName(), "test-board");
        assertEquals(board.getSize(), 5);
        assertTrue(board.getIsActive());
        assertEquals(board.getMoves(), Collections.emptyList());
        assertEquals(board.toString(), "{\"id\":0,\"name\":\"test-board\",\"size\":5,\"isActive\":true}");
    }

    @Test public void createWithRequest() {
        CreateBoardRequest request = new CreateBoardRequest("test-board", 5);
        Board board = new Board(request);

        assertEquals(board.getName(), "test-board");
        assertEquals(board.getSize(), 5);
        assertTrue(board.getIsActive());
        assertEquals(board.getMoves(), Collections.emptyList());
        assertEquals(board.toString(), "{\"id\":0,\"name\":\"test-board\",\"size\":5,\"isActive\":true}");
    }

    @Test public void failToPrepareMoveWhenBoardIsDeactivated() {
        Board board = new Board("test-board", 5);
        board.deactivate();

        Word word = new Word(1, "aba");
        AddMoveRequest request = new AddMoveRequest(word.getWord(), 0, 0, true);

        try {
            board.prepareMove(word, request);
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
            assertTrue(e.getMessage().contains("deactivated"));
        }
    }

    @Test public void failToPrepareMoveWhenWordIsTooLong() {
        Board board = new Board("test-board", 5);

        Word word1 = new Word(1, "deneme");
        Word word2 = new Word(1, "aba");

        AddMoveRequest request1 = new AddMoveRequest(word1.getWord(), 0, 0, true);

        try {
            board.prepareMove(word1, request1);
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
            assertTrue(e.getMessage().contains("too long"));
        }

        AddMoveRequest request2 = new AddMoveRequest(word2.getWord(), 0, 3, true);

        try {
            board.prepareMove(word2, request2);
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
            assertTrue(e.getMessage().contains("too long"));
        }

        AddMoveRequest request3 = new AddMoveRequest(word2.getWord(), 3, 0, false);

        try {
            board.prepareMove(word2, request3);
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
            assertTrue(e.getMessage().contains("too long"));
        }
    }

    @Test public void failToPrepareMoveWhenRowIsInvalid() {
        Board board = new Board("test-board", 5);

        Word word = new Word(1, "aba");

        AddMoveRequest request = new AddMoveRequest(word.getWord(), -1, 0, true);

        try {
            board.prepareMove(word, request);
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
            assertTrue(e.getMessage().contains("row must be in [0, 5) range"));
        }
    }

    @Test public void failToPrepareMoveWhenColumnIsInvalid() {
        Board board = new Board("test-board", 5);

        Word word = new Word(1, "aba");

        AddMoveRequest request = new AddMoveRequest(word.getWord(), 0, -1, true);

        try {
            board.prepareMove(word, request);
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
            assertTrue(e.getMessage().contains("column must be in [0, 5) range"));
        }
    }

    @Test public void failToPrepareMoveWhenWordIsBeingAddedToEmptyCellOnNonEmptyBoard() {
        Board board = new Board("test-board", 5);

        Word word1 = new Word(1, "aba");
        Word word2 = new Word(2, "ebe");

        board.addMove(new Move(board, word1, 0, 0, true));

        AddMoveRequest request = new AddMoveRequest(word2.getWord(), 1, 0, false);

        try {
            board.prepareMove(word2, request);
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
            assertTrue(e.getMessage().contains("cannot start from empty cell on a non-empty board"));
        }
    }

    @Test public void failToPrepareMoveWhenWordIsBeingAddedToCellWithADifferentLetter() {
        Board board = new Board("test-board", 6);

        Word word1 = new Word(1, "elma");
        Word word2 = new Word(2, "ekmek");
        Word word3 = new Word(3, "mal");
        Word word4 = new Word(4, "matara");

        board.addMove(new Move(board, word1, 0, 0, true));
        board.addMove(new Move(board, word2, 0, 0, false));
        board.addMove(new Move(board, word3, 0, 2, false));

        AddMoveRequest request = new AddMoveRequest(word4.getWord(), 2, 0, true);

        try {
            board.prepareMove(word4, request);
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
            assertTrue(e.getMessage().contains("cell [2, 2] already has letter 'l'"));
        }
    }

    @Test public void failToPrepareMoveWhenWordDoesNotStartWithSameLetter() {
        Board board = new Board("test-board", 6);

        Word word1 = new Word(1, "elma");
        Word word2 = new Word(2, "mat");

        board.addMove(new Move(board, word1, 0, 0, true));

        AddMoveRequest request = new AddMoveRequest(word2.getWord(), 0, 0, false);

        try {
            board.prepareMove(word2, request);
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
            assertTrue(e.getMessage().contains("word does not start with letter 'e'"));
        }
    }

    @Test public void prepareMove() {
        Board board = new Board("test-board", 6);

        Word word1 = new Word(1, "elma");
        Word word2 = new Word(2, "ekmek");
        Word word3 = new Word(3, "mat");
        Word word4 = new Word(4, "matara");

        board.addMove(new Move(board, word1, 0, 0, true));
        board.addMove(new Move(board, word2, 0, 0, false));
        board.addMove(new Move(board, word3, 0, 2, false));

        AddMoveRequest request = new AddMoveRequest(word4.getWord(), 2, 0, true);

        Move move = board.prepareMove(word4, request);

        assertEquals(move.getBoard(), board);
        assertEquals(move.getWord(), word4);
        assertEquals(move.getRow(), request.getRow());
        assertEquals(move.getColumn(), request.getColumn());
        assertEquals(move.getIsHorizontal(), request.getIsHorizontal());
    }

    @Test public void addMove() {
        Board board = new Board("test-board", 6);

        assertEquals(board.getMoves(), Collections.emptyList());

        Word word = new Word(1, "elma");
        Move move = new Move(board, word, 0, 0, true);

        board.addMove(move);

        assertEquals(board.getMoves(), Collections.singletonList(move));
    }

    @Test public void deactivate() {
        Board board = new Board("test-board", 6);

        assertTrue(board.getIsActive());

        board.deactivate();

        assertFalse(board.getIsActive());
    }

    @Test public void printWhenBoardIsEmpty() {
        Board board = new Board("test-board", 3);

        assertEquals(
            board.print(),
            "   |   |   \n" +
            "---|---|---\n" +
            "   |   |   \n" +
            "---|---|---\n" +
            "   |   |   "
        );
    }

    @Test public void printWhenBoardIsNotEmpty() {
        Board board = new Board("test-board", 3);

        Word word1 = new Word(1, "aba");
        Word word2 = new Word(2, "ana");

        Move move1 = new Move(board, word1, 0, 0, true);
        Move move2 = new Move(board, word2, 0, 0, false);

        board.addMove(move1);
        board.addMove(move2);

        assertEquals(
            board.print(),
            " a | b | a \n" +
            "---|---|---\n" +
            " n |   |   \n" +
            "---|---|---\n" +
            " a |   |   "
        );
    }
}
