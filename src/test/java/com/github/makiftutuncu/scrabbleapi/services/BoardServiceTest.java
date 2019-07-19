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
        assertEquals(Collections.emptyList(), service.getActiveBoards());
    }

    @Test public void getActiveBoardsWithSomeBoards() {
        Board board1 = new Board("test-board-1", 3);
        Board board2 = new Board("test-board-2", 3);
        board2.deactivate();

        TestData.insertBoard(board1);
        TestData.insertBoard(board2);

        assertEquals(Collections.singletonList(new BoardResponse(board1)), service.getActiveBoards());
    }

    @Test public void failToCreateBoardBecauseItExists() {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        CreateBoardRequest request = new CreateBoardRequest("test-board", 3);

        try {
            service.createBoard(request);
        } catch (ScrabbleException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
            assertTrue(e.getMessage().contains("already exists"));
        }
    }

    @Test public void createBoard() {
        CreateBoardRequest request = new CreateBoardRequest("test-board", 3);
        BoardResponse response = service.createBoard(request);

        assertTrue(response.getId() > 0);
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getSize(), response.getSize());
        assertTrue(response.getIsActive());
        assertEquals(Collections.emptyList(), response.getWords());
        assertEquals(0, response.getPoints());
    }

    @Test public void failToGetBoardBecauseItDoesNotExist() {
        try {
            service.getBoard(-1);
        } catch (ScrabbleException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
            assertTrue(e.getMessage().contains("not found"));
        }
    }

    @Test public void getBoard() {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        BoardResponse response = service.getBoard(board.getId());

        assertEquals(board.getName(), response.getName());
        assertEquals(board.getSize(), response.getSize());
        assertTrue(response.getIsActive());
        assertEquals(Collections.emptyList(), response.getWords());
        assertEquals(0, response.getPoints());
    }

    @Test public void failToDeactivateBecauseBoardDoesNotExist() {
        try {
            service.deactivate(-1);
        } catch (ScrabbleException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
            assertTrue(e.getMessage().contains("not found"));
        }
    }

    @Test public void deactivate() {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        BoardResponse response = service.deactivate(board.getId());

        assertTrue(response.getId() > 0);
        assertEquals(board.getName(), response.getName());
        assertEquals(board.getSize(), response.getSize());
        assertFalse(response.getIsActive());
        assertEquals(Collections.emptyList(), response.getWords());
        assertEquals(0, response.getPoints());
    }

    @Test public void getMovesWithNoMoves() {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        List<MoveResponse> moves = service.getMoves(board.getId());

        assertEquals(Collections.emptyList(), moves);
    }

    @Test public void getMovesWithSomeMoves() {
        Board board = new Board("test-board", 3);
        Word word = new Word(1, "aba");
        Move move = new Move(board, word, 0, 0, true);
        board.addMove(move);
        TestData.insertBoard(board);

        List<MoveResponse> moves = service.getMoves(board.getId());

        assertEquals(Collections.singletonList(new MoveResponse(move)), moves);
    }

    @Test public void failToGetMovesWithStepWithInvalidStep() {
        try {
            service.getMoves(-1, -1);
        } catch (ScrabbleException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
            assertEquals("Step -1 is invalid, it should be greater than 1!", e.getMessage());
        }
    }

    @Test public void getMovesWithStepWithNoMoves() {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        List<MoveResponse> moves = service.getMoves(board.getId(), 2);

        assertEquals(Collections.emptyList(), moves);
    }

    @Test public void getMovesWithStepWithSomeMoves() {
        Board board = new Board("test-board", 3);
        Word word = new Word(1, "aba");
        Move move = new Move(board, word, 0, 0, true);
        board.addMove(move);
        TestData.insertBoard(board);

        List<MoveResponse> moves = service.getMoves(board.getId(), 2);

        assertEquals(Collections.singletonList(new MoveResponse(move)), moves);
    }

    @Test public void failToGetWordsBecauseBoardDoesNotExist() {
        try {
            service.getWords(-1);
        } catch (ScrabbleException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
            assertTrue(e.getMessage().contains("not found"));
        }
    }

    @Test public void getWordsWithNoWords() {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        List<WordResponse> words = service.getWords(board.getId());

        assertEquals(Collections.emptyList(), words);
    }

    @Test public void getWordsWithSomeWords() {
        Board board = new Board("test-board", 3);
        Word word = new Word(1, "aba");
        Move move = new Move(board, word, 0, 0, true);
        board.addMove(move);
        TestData.insertBoard(board);

        List<WordResponse> words = service.getWords(board.getId());

        assertEquals(Collections.singletonList(new WordResponse(word.getWord())), words);
    }

    @Test public void failToAddMoveBecauseBoardDoesNotExist() {
        AddMoveRequest request = new AddMoveRequest("deniz", 0, 0, true);

        try {
            service.addMove(-1, request);
        } catch (ScrabbleException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
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
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
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
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
            assertTrue(e.getMessage().contains("too long"));
        }
    }

    @Test public void addMove() {
        Board board = new Board("test-board", 8);
        TestData.insertBoard(board);

        AddMoveRequest request = new AddMoveRequest("deniz", 0, 0, true);

        MoveResponse response = service.addMove(board.getId(), request);

        assertEquals(request.getWord(), response.getWord());
        assertEquals(request.getRow(), response.getRow());
        assertEquals(request.getColumn(), response.getColumn());
        assertEquals(request.getIsHorizontal(), response.getIsHorizontal());
        assertEquals(10, response.getPoints());
    }

    @Test public void failToAddMovesBecauseBoardDoesNotExist() {
        AddMoveRequest request = new AddMoveRequest("deniz", 0, 0, true);

        try {
            service.addMoves(-1, Collections.singletonList(request));
        } catch (ScrabbleException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
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
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
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
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
            assertTrue(e.getMessage().contains("word does not start with letter 'd'"));
        }
    }

    @Test public void addMoves() {
        Board board = new Board("test-board", 8);
        TestData.insertBoard(board);

        AddMoveRequest request1 = new AddMoveRequest("deniz", 0, 0, true);
        AddMoveRequest request2 = new AddMoveRequest("deneme", 0, 0, false);

        List<MoveResponse> moves = service.addMoves(board.getId(), Arrays.asList(request1, request2));

        assertEquals(2, moves.size());

        MoveResponse response1 = moves.get(0);

        assertEquals(request1.getWord(), response1.getWord());
        assertEquals(request1.getRow(), response1.getRow());
        assertEquals(request1.getColumn(), response1.getColumn());
        assertEquals(request1.getIsHorizontal(), response1.getIsHorizontal());
        assertEquals(10, response1.getPoints());

        MoveResponse response2 = moves.get(1);

        assertEquals(request2.getWord(), response2.getWord());
        assertEquals(request2.getRow(), response2.getRow());
        assertEquals(request2.getColumn(), response2.getColumn());
        assertEquals(request2.getIsHorizontal(), response2.getIsHorizontal());
        assertEquals(9, response2.getPoints());
    }
}
