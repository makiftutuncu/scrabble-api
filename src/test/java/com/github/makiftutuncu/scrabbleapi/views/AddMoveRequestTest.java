package com.github.makiftutuncu.scrabbleapi.views;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class AddMoveRequestTest {
    @Test public void testWithDefaults() {
        AddMoveRequest request = new AddMoveRequest();
        assertEquals("", request.getWord());
        assertEquals(-1, request.getRow());
        assertEquals(-1, request.getColumn());
        assertTrue(request.getIsHorizontal());
        assertEquals("{\"word\":\"\",\"row\":-1,\"column\":-1,\"isHorizontal\":true}", request.toString());
    }

    @Test public void testWithCustomData() {
        AddMoveRequest request = new AddMoveRequest("deneme", 1, 2, false);
        assertEquals("deneme", request.getWord());
        assertEquals(1, request.getRow());
        assertEquals(2, request.getColumn());
        assertFalse(request.getIsHorizontal());
        assertEquals("{\"word\":\"deneme\",\"row\":1,\"column\":2,\"isHorizontal\":false}", request.toString());
    }
}
