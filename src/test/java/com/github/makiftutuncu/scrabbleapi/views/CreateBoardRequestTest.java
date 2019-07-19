package com.github.makiftutuncu.scrabbleapi.views;

import com.github.makiftutuncu.scrabbleapi.models.Board;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class CreateBoardRequestTest {
    @Test public void testWithDefaults() {
        CreateBoardRequest request = new CreateBoardRequest();

        assertTrue(request.getName().startsWith("Board "));
        assertEquals(Board.DEFAULT_SIZE, request.getSize());
    }

    @Test public void testWithInvalidData() {
        CreateBoardRequest request1 = new CreateBoardRequest(null, 3);

        assertTrue(request1.getName().startsWith("Board "));
        assertEquals(3, request1.getSize());

        CreateBoardRequest request2 = new CreateBoardRequest("", 3);

        assertTrue(request2.getName().startsWith("Board "));
        assertEquals(3, request2.getSize());

        CreateBoardRequest request3 = new CreateBoardRequest(" ", 3);

        assertTrue(request3.getName().startsWith("Board "));
        assertEquals(3, request3.getSize());

        CreateBoardRequest request4 = new CreateBoardRequest("test", -1);

        assertEquals("test", request4.getName());
        assertEquals(Board.DEFAULT_SIZE, request4.getSize());
    }

    @Test public void testWithCustomData() {
        CreateBoardRequest request = new CreateBoardRequest("test", 3);

        assertEquals("test", request.getName());
        assertEquals(3, request.getSize());
    }
}
