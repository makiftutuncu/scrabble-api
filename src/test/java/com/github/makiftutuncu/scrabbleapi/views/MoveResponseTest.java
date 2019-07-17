package com.github.makiftutuncu.scrabbleapi.views;

import com.github.makiftutuncu.scrabbleapi.models.Board;
import com.github.makiftutuncu.scrabbleapi.models.Move;
import com.github.makiftutuncu.scrabbleapi.models.Word;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveResponseTest {
    @Test void testPointsOfWord() {
        MoveResponse moveResponse = new MoveResponse(new Move(new Board(new CreateBoardRequest("test", 8)), new Word("ekmek"), 0, 0, true));
        assertEquals(moveResponse.getPoints(), 6);
    }
}
