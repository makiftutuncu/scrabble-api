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

        assertEquals(board.getId(), response.getId());
        assertEquals(board.getName(), response.getName());
        assertEquals(board.getSize(), response.getSize());
        assertEquals(board.getIsActive(), response.getIsActive());
        assertEquals(Collections.emptyList(), response.getWords());
        assertEquals(0, response.getPoints());
        assertEquals("{\"id\":0,\"name\":\"test\",\"size\":8,\"isActive\":true,\"words\":[],\"points\":0}", response.toString());
    }

    @Test public void testWithNonEmptyBoard() {
        CreateBoardRequest request = new CreateBoardRequest("test", 8);
        Board board = new Board(request);
        Move move = new Move(board, new Word("ekmek"), 0, 0, true);
        board.addMove(move);
        BoardResponse response = new BoardResponse(board);

        assertEquals(board.getId(), response.getId());
        assertEquals(board.getName(), response.getName());
        assertEquals(board.getName(), response.getName());
        assertEquals(board.getSize(), response.getSize());
        assertEquals(board.getIsActive(), response.getIsActive());
        assertEquals(Collections.singletonList(new WordResponse("ekmek")), response.getWords());
        assertEquals(6, response.getPoints());
        assertEquals("{\"id\":0,\"name\":\"test\",\"size\":8,\"isActive\":true,\"words\":[{\"word\":\"ekmek\",\"points\":6}],\"points\":6}", response.toString());
    }
}
