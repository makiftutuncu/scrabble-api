package com.github.makiftutuncu.scrabbleapi.services;

import com.github.makiftutuncu.scrabbleapi.SpringTest;
import com.github.makiftutuncu.scrabbleapi.TestData;
import com.github.makiftutuncu.scrabbleapi.models.Board;
import com.github.makiftutuncu.scrabbleapi.models.Move;
import com.github.makiftutuncu.scrabbleapi.models.Word;
import com.github.makiftutuncu.scrabbleapi.utilities.ScrabbleException;
import com.github.makiftutuncu.scrabbleapi.views.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class BoardServiceTest extends SpringTest {
    private BoardService service;

    @Before public void truncate() {
        TestData.truncateBoards();
        this.service = (BoardService) wac.getBean("boardService");
    }

    @Test public void getActiveBoardsWithNoBoards() {
        assertEquals(service.getActiveBoards(), Collections.emptyList());
    }

    @Test public void getActiveBoardsWithSomeBoards() {
        Board board1 = new Board("test-board-1", 3);
        Board board2 = new Board("test-board-2", 3);
        board2.deactivate();

        TestData.insertBoard(board1);
        TestData.insertBoard(board2);

        assertEquals(service.getActiveBoards(), Collections.singletonList(new BoardResponse(board1)));
    }

    @Test public void failToCreateBoardBecauseItExists() {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        CreateBoardRequest request = new CreateBoardRequest("test-board", 3);

        try {
            service.createBoard(request);
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
            assertTrue(e.getMessage().contains("already exists"));
        }
    }

    @Test public void createBoard() {
        CreateBoardRequest request = new CreateBoardRequest("test-board", 3);
        BoardResponse response = service.createBoard(request);

        assertTrue(response.getId() > 0);
        assertEquals(response.getName(), request.getName());
        assertEquals(response.getSize(), request.getSize());
        assertTrue(response.getIsActive());
        assertEquals(response.getWords(), Collections.emptyList());
        assertEquals(response.getPoints(), 0);
    }

    @Test public void failToGetBoardBecauseItDoesNotExist() {
        try {
            service.getBoard(-1);
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
            assertTrue(e.getMessage().contains("not found"));
        }
    }

    @Test public void getBoard() {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        BoardResponse response = service.getBoard(board.getId());

        assertEquals(response.getName(), board.getName());
        assertEquals(response.getSize(), board.getSize());
        assertTrue(response.getIsActive());
        assertEquals(response.getWords(), Collections.emptyList());
        assertEquals(response.getPoints(), 0);
    }

    @Test public void failToDeactivateBecauseBoardDoesNotExist() {
        try {
            service.deactivate(-1);
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
            assertTrue(e.getMessage().contains("not found"));
        }
    }

    @Test public void deactivate() {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        BoardResponse response = service.deactivate(board.getId());

        assertTrue(response.getId() > 0);
        assertEquals(response.getName(), board.getName());
        assertEquals(response.getSize(), board.getSize());
        assertFalse(response.getIsActive());
        assertEquals(response.getWords(), Collections.emptyList());
        assertEquals(response.getPoints(), 0);
    }

    @Test public void getMovesWithNoMoves() {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        List<MoveResponse> moves = service.getMoves(board.getId());

        assertEquals(moves, Collections.emptyList());
    }

    @Test public void getMovesWithSomeMoves() {
        Board board = new Board("test-board", 3);
        Word word = new Word(1, "aba");
        Move move = new Move(board, word, 0, 0, true);
        board.addMove(move);
        TestData.insertBoard(board);

        List<MoveResponse> moves = service.getMoves(board.getId());

        assertEquals(moves, Collections.singletonList(new MoveResponse(move)));
    }

    @Test public void failToGetMovesWithStepWithInvalidStep() {
        try {
            service.getMoves(-1, -1);
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
            assertEquals(e.getMessage(), "Step -1 is invalid, it should be greater than 1!");
        }
    }

    @Test public void getMovesWithStepWithNoMoves() {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        List<MoveResponse> moves = service.getMoves(board.getId(), 2);

        assertEquals(moves, Collections.emptyList());
    }

    @Test public void getMovesWithStepWithSomeMoves() {
        Board board = new Board("test-board", 3);
        Word word = new Word(1, "aba");
        Move move = new Move(board, word, 0, 0, true);
        board.addMove(move);
        TestData.insertBoard(board);

        List<MoveResponse> moves = service.getMoves(board.getId(), 2);

        assertEquals(moves, Collections.singletonList(new MoveResponse(move)));
    }

    @Test public void failToGetWordsBecauseBoardDoesNotExist() {
        try {
            service.getWords(-1);
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
            assertTrue(e.getMessage().contains("not found"));
        }
    }

    @Test public void getWordsWithNoWords() {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        List<WordResponse> words = service.getWords(board.getId());

        assertEquals(words, Collections.emptyList());
    }

    @Test public void getWordsWithSomeWords() {
        Board board = new Board("test-board", 3);
        Word word = new Word(1, "aba");
        Move move = new Move(board, word, 0, 0, true);
        board.addMove(move);
        TestData.insertBoard(board);

        List<WordResponse> words = service.getWords(board.getId());

        assertEquals(words, Collections.singletonList(new WordResponse(word.getWord())));
    }

    @Test public void failToAddMoveBecauseBoardDoesNotExist() {
        AddMoveRequest request = new AddMoveRequest("deniz", 0, 0, true);

        try {
            service.addMove(-1, request);
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
            assertTrue(e.getMessage().contains("Board"));
            assertTrue(e.getMessage().contains("not found"));
        }
    }

    @Test public void failToAddMoveBecauseWordDoesNotExist() {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        AddMoveRequest request = new AddMoveRequest("asdzxc", 0, 0, true);

        try {
            service.addMove(board.getId(), request);
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
            assertTrue(e.getMessage().contains("Word"));
            assertTrue(e.getMessage().contains("not found"));
        }
    }

    @Test public void failToAddMoveBecauseMoveIsInvalid() {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        AddMoveRequest request = new AddMoveRequest("deniz", 0, 0, true);

        try {
            service.addMove(board.getId(), request);
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
            assertTrue(e.getMessage().contains("too long"));
        }
    }

    @Test public void addMove() {
        Board board = new Board("test-board", 8);
        TestData.insertBoard(board);

        AddMoveRequest request = new AddMoveRequest("deniz", 0, 0, true);

        MoveResponse response = service.addMove(board.getId(), request);

        assertEquals(response.getWord(), request.getWord());
        assertEquals(response.getRow(), request.getRow());
        assertEquals(response.getColumn(), request.getColumn());
        assertEquals(response.getIsHorizontal(), request.getIsHorizontal());
        assertEquals(response.getPoints(), 10);
    }

    @Test public void failToAddMovesBecauseBoardDoesNotExist() {
        AddMoveRequest request = new AddMoveRequest("deniz", 0, 0, true);

        try {
            service.addMoves(-1, Collections.singletonList(request));
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
            assertTrue(e.getMessage().contains("Board"));
            assertTrue(e.getMessage().contains("not found"));
        }
    }

    @Test public void failToAddMovesBecauseSomeWordsDoesNotExist() {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        AddMoveRequest request1 = new AddMoveRequest("deniz", 0, 0, true);
        AddMoveRequest request2 = new AddMoveRequest("dsada", 0, 0, false);

        try {
            service.addMoves(board.getId(), Arrays.asList(request1, request2));
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
            assertTrue(e.getMessage().contains("words were not valid"));
        }
    }

    @Test public void failToAddMovesBecauseMoveIsInvalid() {
        Board board = new Board("test-board", 8);
        TestData.insertBoard(board);

        AddMoveRequest request1 = new AddMoveRequest("deniz", 0, 0, true);
        AddMoveRequest request2 = new AddMoveRequest("ada", 0, 0, false);

        try {
            service.addMoves(board.getId(), Arrays.asList(request1, request2));
        } catch (ScrabbleException e) {
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
            assertTrue(e.getMessage().contains("word does not start with letter 'd'"));
        }
    }

    @Test public void addMoves() {
        Board board = new Board("test-board", 8);
        TestData.insertBoard(board);

        AddMoveRequest request1 = new AddMoveRequest("deniz", 0, 0, true);
        AddMoveRequest request2 = new AddMoveRequest("deneme", 0, 0, false);

        List<MoveResponse> moves = service.addMoves(board.getId(), Arrays.asList(request1, request2));

        assertEquals(moves.size(), 2);

        MoveResponse response1 = moves.get(0);

        assertEquals(response1.getWord(), request1.getWord());
        assertEquals(response1.getRow(), request1.getRow());
        assertEquals(response1.getColumn(), request1.getColumn());
        assertEquals(response1.getIsHorizontal(), request1.getIsHorizontal());
        assertEquals(response1.getPoints(), 10);

        MoveResponse response2 = moves.get(1);

        assertEquals(response2.getWord(), request2.getWord());
        assertEquals(response2.getRow(), request2.getRow());
        assertEquals(response2.getColumn(), request2.getColumn());
        assertEquals(response2.getIsHorizontal(), request2.getIsHorizontal());
        assertEquals(response2.getPoints(), 9);
    }
}
