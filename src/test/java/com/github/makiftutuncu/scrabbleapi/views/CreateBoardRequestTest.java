package com.github.makiftutuncu.scrabbleapi.views;

import com.github.makiftutuncu.scrabbleapi.models.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateBoardRequestTest {
    @Test void testDefaultData() {
        CreateBoardRequest createBoardRequest = new CreateBoardRequest();
        assertTrue(createBoardRequest.getName().startsWith("Board "));
        assertEquals(createBoardRequest.getSize(), Board.DEFAULT_SIZE);
    }

    @Test void testInvalidData() {
        CreateBoardRequest createBoardRequest = new CreateBoardRequest("", -1);
        assertTrue(createBoardRequest.getName().startsWith("Board "));
        assertEquals(createBoardRequest.getSize(), Board.DEFAULT_SIZE);
    }

    @Test void testCustomData() {
        CreateBoardRequest createBoardRequest = new CreateBoardRequest("test", 3);
        assertEquals(createBoardRequest.getName(), "test");
        assertEquals(createBoardRequest.getSize(), 3);
    }
}
