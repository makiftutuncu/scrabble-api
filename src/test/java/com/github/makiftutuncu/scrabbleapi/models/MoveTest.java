package com.github.makiftutuncu.scrabbleapi.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class MoveTest {
    @Test public void test() {
        Board board = new Board("test-board", 5);
        Word word = new Word(1, "deneme");
        Move move = new Move(board, word, 1, 2, true);

        assertEquals(board, move.getBoard());
        assertEquals(word, move.getWord());
        assertEquals(1, move.getRow());
        assertEquals(2, move.getColumn());
        assertTrue(move.getIsHorizontal());
        assertEquals("{\"id\":0,\"board\":\"test-board\",\"word\":\"deneme\",\"row\":1,\"column\":2,\"isHorizontal\":true}", move.toString());
    }
}
