package com.github.makiftutuncu.scrabbleapi.views;

import com.github.makiftutuncu.scrabbleapi.models.Board;
import com.github.makiftutuncu.scrabbleapi.models.Move;
import com.github.makiftutuncu.scrabbleapi.models.Word;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class BoardResponseTest {
    @Test public void testWithEmptyBoard() {
        CreateBoardRequest request = new CreateBoardRequest("test", 8);
        Board board = new Board(request);
        BoardResponse response = new BoardResponse(board);

        assertEquals(response.getId(), board.getId());
        assertEquals(response.getName(), board.getName());
        assertEquals(response.getName(), board.getName());
        assertEquals(response.getSize(), board.getSize());
        assertEquals(response.getIsActive(), board.getIsActive());
        assertEquals(response.getWords(), Collections.emptyList());
        assertEquals(response.getPoints(), 0);
    }

    @Test public void testWithNonEmptyBoard() {
        CreateBoardRequest request = new CreateBoardRequest("test", 8);
        Board board = new Board(request);
        Move move = new Move(board, new Word("ekmek"), 0, 0, true);
        board.addMove(move);
        BoardResponse response = new BoardResponse(board);

        assertEquals(response.getId(), board.getId());
        assertEquals(response.getName(), board.getName());
        assertEquals(response.getName(), board.getName());
        assertEquals(response.getSize(), board.getSize());
        assertEquals(response.getIsActive(), board.getIsActive());
        assertEquals(response.getWords(), Collections.singletonList(new WordResponse("ekmek")));
        assertEquals(response.getPoints(), 6);
    }
}
