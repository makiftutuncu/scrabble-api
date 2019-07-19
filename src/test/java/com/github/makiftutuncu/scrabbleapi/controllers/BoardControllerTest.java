package com.github.makiftutuncu.scrabbleapi.controllers;

import com.github.makiftutuncu.scrabbleapi.SpringTest;
import com.github.makiftutuncu.scrabbleapi.TestData;
import com.github.makiftutuncu.scrabbleapi.models.Board;
import com.github.makiftutuncu.scrabbleapi.models.Move;
import com.github.makiftutuncu.scrabbleapi.models.Word;
import com.github.makiftutuncu.scrabbleapi.views.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class BoardControllerTest extends SpringTest {
    @Before public void truncate() {
        TestData.truncateBoards();
    }

    @Test public void getActiveBoardsWithNoBoards() throws Exception {
        MockHttpServletResponse response = getRequest("/boards");

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), "[]");
    }

    @Test public void getActiveBoardsWithSomeBoards() throws Exception {
        Board board1 = new Board("test-board-1", 3);
        Board board2 = new Board("test-board-2", 3);
        board2.deactivate();

        TestData.insertBoard(board1);
        TestData.insertBoard(board2);

        MockHttpServletResponse response = getRequest("/boards");

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), String.format("[%s]", new BoardResponse(board1)));
    }

    @Test public void failToCreateBoardBecauseItExists() throws Exception {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        CreateBoardRequest request = new CreateBoardRequest("test-board", 3);
        MockHttpServletResponse response = postRequest("/boards", request.toString());

        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
        assertTrue(response.getContentAsString().contains("already exists"));
    }

    @Test public void createBoard() throws Exception {
        CreateBoardRequest request = new CreateBoardRequest("test-board", 3);
        MockHttpServletResponse response = postRequest("/boards", request.toString());

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertTrue(response.getContentAsString().contains("\"name\":\"test-board\",\"size\":3,\"isActive\":true,\"words\":[],\"points\":0"));
    }

    @Test public void failToGetBoardBecauseItDoesNotExist() throws Exception {
        MockHttpServletResponse response = getRequest("/boards/-1");

        assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
        assertTrue(response.getContentAsString().contains("not found"));
    }

    @Test public void getBoard() throws Exception {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        MockHttpServletResponse response = getRequest("/boards/" + board.getId());

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), (new BoardResponse(board)).toString());
    }

    @Test public void failToDeactivateBecauseBoardDoesNotExist() throws Exception {
        MockHttpServletResponse response = postRequest("/boards/-1/deactivate");

        assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
        assertTrue(response.getContentAsString().contains("not found"));
    }

    @Test public void deactivate() throws Exception {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);
        board.deactivate();

        MockHttpServletResponse response = postRequest("/boards/" + board.getId() + "/deactivate");

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), (new BoardResponse(board)).toString());
    }

    @Test public void getMovesWithNoMoves() throws Exception {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        MockHttpServletResponse response = getRequest("/boards/" + board.getId() + "/moves");

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), "[]");
    }

    @Test public void getMovesWithSomeMoves() throws Exception {
        Board board = new Board("test-board", 3);
        Word word = new Word(1, "aba");
        Move move = new Move(board, word, 0, 0, true);
        board.addMove(move);
        TestData.insertBoard(board);

        MockHttpServletResponse response = getRequest("/boards/" + board.getId() + "/moves");

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), String.format("[%s]", new MoveResponse(move)));
    }

    @Test public void failToGetMovesWithStepWithInvalidStep() throws Exception {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        MockHttpServletResponse response = getRequest("/boards/" + board.getId() + "/moves/-1");

        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
        assertTrue(response.getContentAsString().contains("Step -1 is invalid, it should be greater than 1!"));
    }

    @Test public void getMovesWithStepWithNoMoves() throws Exception {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        MockHttpServletResponse response = getRequest("/boards/" + board.getId() + "/moves/2");

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), "[]");
    }

    @Test public void getMovesWithStepWithSomeMoves() throws Exception {
        Board board = new Board("test-board", 3);
        Word word = new Word(1, "aba");
        Move move = new Move(board, word, 0, 0, true);
        board.addMove(move);
        TestData.insertBoard(board);

        MockHttpServletResponse response = getRequest("/boards/" + board.getId() + "/moves/2");

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), String.format("[%s]", new MoveResponse(move)));
    }

    @Test public void failToAddMoveBecauseBoardDoesNotExist() throws Exception {
        AddMoveRequest request = new AddMoveRequest("deniz", 0, 0, true);

        MockHttpServletResponse response = postRequest("/boards/-1/moves", request.toString());

        assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
        assertTrue(response.getContentAsString().contains("Board"));
        assertTrue(response.getContentAsString().contains("not found"));
    }

    @Test public void failToAddMoveBecauseWordDoesNotExist() throws Exception {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        AddMoveRequest request = new AddMoveRequest("asdzxc", 0, 0, true);

        MockHttpServletResponse response = postRequest("/boards/" + board.getId() + "/moves", request.toString());

        assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
        assertTrue(response.getContentAsString().contains("Word"));
        assertTrue(response.getContentAsString().contains("not found"));
    }

    @Test public void failToAddMoveBecauseMoveIsInvalid() throws Exception {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        AddMoveRequest request = new AddMoveRequest("deniz", 0, 0, true);

        MockHttpServletResponse response = postRequest("/boards/" + board.getId() + "/moves", request.toString());

        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
        assertTrue(response.getContentAsString().contains("too long"));
    }

    @Test public void addMove() throws Exception {
        Board board = new Board("test-board", 8);
        TestData.insertBoard(board);

        Word word = new Word(1, "deniz");
        Move move = new Move(board, word, 0, 0, true);
        AddMoveRequest request = new AddMoveRequest(word.getWord(), move.getRow(), move.getColumn(), move.getIsHorizontal());

        MockHttpServletResponse response = postRequest("/boards/" + board.getId() + "/moves", request.toString());

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), new MoveResponse(move).toString());
    }

    @Test public void failToAddMovesBecauseBoardDoesNotExist() throws Exception {
        AddMoveRequest request = new AddMoveRequest("deniz", 0, 0, true);

        MockHttpServletResponse response = postRequest("/boards/-1/multipleMoves", String.format("[%s]", request));

        assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
        assertTrue(response.getContentAsString().contains("Board"));
        assertTrue(response.getContentAsString().contains("not found"));
    }

    @Test public void failToAddMovesBecauseSomeWordsDoesNotExist() throws Exception {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        AddMoveRequest request1 = new AddMoveRequest("deniz", 0, 0, true);
        AddMoveRequest request2 = new AddMoveRequest("dsada", 0, 0, false);

        MockHttpServletResponse response = postRequest("/boards/" + board.getId() + "/multipleMoves", String.format("[%s,%s]", request1, request2));

        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
        assertTrue(response.getContentAsString().contains("words were not valid"));
    }

    @Test public void failToAddMovesBecauseMoveIsInvalid() throws Exception {
        Board board = new Board("test-board", 8);
        TestData.insertBoard(board);

        AddMoveRequest request1 = new AddMoveRequest("deniz", 0, 0, true);
        AddMoveRequest request2 = new AddMoveRequest("ada", 0, 0, false);

        MockHttpServletResponse response = postRequest("/boards/" + board.getId() + "/multipleMoves", String.format("[%s,%s]", request1, request2));

        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
        assertTrue(response.getContentAsString().contains("word does not start with letter 'd'"));
    }

    @Test public void addMoves() throws Exception {
        Board board = new Board("test-board", 8);
        TestData.insertBoard(board);

        Word word1 = new Word(1, "deniz");
        Move move1 = new Move(board, word1, 0, 0, true);
        AddMoveRequest request1 = new AddMoveRequest(word1.getWord(), move1.getRow(), move1.getColumn(), move1.getIsHorizontal());

        Word word2 = new Word(2, "deneme");
        Move move2 = new Move(board, word2, 0, 0, false);
        AddMoveRequest request2 = new AddMoveRequest(word2.getWord(), move2.getRow(), move2.getColumn(), move2.getIsHorizontal());

        MockHttpServletResponse response = postRequest("/boards/" + board.getId() + "/multipleMoves", String.format("[%s,%s]", request1, request2));

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), String.format("[%s,%s]", new MoveResponse(move1), new MoveResponse(move2)));
    }

    @Test public void failToGetWordsBecauseBoardDoesNotExist() throws Exception {
        MockHttpServletResponse response = getRequest("/boards/-1/words");

        assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
        assertTrue(response.getContentAsString().contains("Board"));
        assertTrue(response.getContentAsString().contains("not found"));
    }

    @Test public void getWordsWithNoWords() throws Exception {
        Board board = new Board("test-board", 3);
        TestData.insertBoard(board);

        MockHttpServletResponse response = getRequest("/boards/" + board.getId() + "/words");

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), "[]");
    }

    @Test public void getWordsWithSomeWords() throws Exception {
        Board board = new Board("test-board", 3);
        Word word = new Word(1, "aba");
        Move move = new Move(board, word, 0, 0, true);
        board.addMove(move);
        TestData.insertBoard(board);

        MockHttpServletResponse response = getRequest("/boards/" + board.getId() + "/words");

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), String.format("[%s]", new WordResponse(word.getWord())));
    }

    private MockHttpServletResponse getRequest(String url) throws Exception {
        MockHttpServletRequestBuilder request = get(url);
        return mockMvc.perform(request).andReturn().getResponse();
    }

    private MockHttpServletResponse postRequest(String url) throws Exception {
        MockHttpServletRequestBuilder request = post(url);
        return mockMvc.perform(request).andReturn().getResponse();
    }

    private MockHttpServletResponse postRequest(String url, String data) throws Exception {
        MockHttpServletRequestBuilder request = post(url).contentType(MediaType.APPLICATION_JSON_UTF8).content(data);
        return mockMvc.perform(request).andReturn().getResponse();
    }
}
