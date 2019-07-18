package com.github.makiftutuncu.scrabbleapi.views;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class AddMoveRequestTest {
    @Test public void testWithDefaults() {
        AddMoveRequest request = new AddMoveRequest();
        assertEquals(request.getWord(), "");
        assertEquals(request.getRow(), -1);
        assertEquals(request.getColumn(), -1);
        assertTrue(request.getIsHorizontal());
        assertEquals(request.toString(), "{\"word\":\"\",\"row\":-1,\"column\":-1,\"isHorizontal\":true}");
    }

    @Test public void testWithCustomData() {
        AddMoveRequest request = new AddMoveRequest("deneme", 1, 2, false);
        assertEquals(request.getWord(), "deneme");
        assertEquals(request.getRow(), 1);
        assertEquals(request.getColumn(), 2);
        assertFalse(request.getIsHorizontal());
        assertEquals(request.toString(), "{\"word\":\"deneme\",\"row\":1,\"column\":2,\"isHorizontal\":false}");
    }
}
