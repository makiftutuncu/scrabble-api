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
        assertEquals(request.getSize(), Board.DEFAULT_SIZE);
    }

    @Test public void testWithInvalidData() {
        CreateBoardRequest request1 = new CreateBoardRequest(null, 3);

        assertTrue(request1.getName().startsWith("Board "));
        assertEquals(request1.getSize(), 3);

        CreateBoardRequest request2 = new CreateBoardRequest("", 3);

        assertTrue(request2.getName().startsWith("Board "));
        assertEquals(request2.getSize(), 3);

        CreateBoardRequest request3 = new CreateBoardRequest(" ", 3);

        assertTrue(request3.getName().startsWith("Board "));
        assertEquals(request3.getSize(), 3);

        CreateBoardRequest request4 = new CreateBoardRequest("test", -1);

        assertEquals(request4.getName(), "test");
        assertEquals(request4.getSize(), Board.DEFAULT_SIZE);
    }

    @Test public void testWithCustomData() {
        CreateBoardRequest request = new CreateBoardRequest("test", 3);

        assertEquals(request.getName(), "test");
        assertEquals(request.getSize(), 3);
    }
}
