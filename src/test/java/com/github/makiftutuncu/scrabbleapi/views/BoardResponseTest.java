package com.github.makiftutuncu.scrabbleapi.views;

import com.github.makiftutuncu.scrabbleapi.models.Board;
import com.github.makiftutuncu.scrabbleapi.models.Move;
import com.github.makiftutuncu.scrabbleapi.models.Word;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardResponseTest {
    @Test void testEmpty() {
        Board board = new Board(new CreateBoardRequest("test", 8));
        Move move = new Move(board, new Word("ekmek"), 0, 0, true);
        BoardResponse boardResponse = new BoardResponse(board);
        assertTrue(boardResponse.getWords().isEmpty());
        assertEquals(boardResponse.getPoints(), 0);
    }

    @Test void testNonEmpty() {
        Board board = new Board(new CreateBoardRequest("test", 8));
        board.addMove(new Move(board, new Word("ekmek"), 0, 0, true));
        BoardResponse boardResponse = new BoardResponse(board);
        assertEquals(boardResponse.getWords().size(), 1);
        assertEquals(boardResponse.getPoints(), 6);
    }
}
