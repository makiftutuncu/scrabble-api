package com.github.makiftutuncu.scrabbleapi.views;

import com.github.makiftutuncu.scrabbleapi.models.Board;
import com.github.makiftutuncu.scrabbleapi.models.Move;
import com.github.makiftutuncu.scrabbleapi.models.Word;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class MoveResponseTest {
    @Test public void test() {
        CreateBoardRequest request = new CreateBoardRequest("test", 8);
        Board board = new Board(request);
        Word word = new Word("ekmek");
        Move move = new Move(board, word, 0, 0, true);
        MoveResponse response = new MoveResponse(move);

        assertEquals(response.getWord(), word.getWord());
        assertEquals(response.getRow(), move.getRow());
        assertEquals(response.getColumn(), move.getColumn());
        assertEquals(response.getIsHorizontal(), move.getIsHorizontal());
        assertEquals(response.getPoints(), 6);
        assertEquals(response.toString(), "{\"word\":\"ekmek\",\"row\":0,\"column\":0,\"isHorizontal\":true,\"points\":6}");
    }
}
