package com.github.makiftutuncu.scrabbleapi.views;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class ScrabbleExceptionResponseTest {
    @Test public void test() {
        ScrabbleExceptionResponse response = new ScrabbleExceptionResponse("test");

        assertEquals("test", response.getMessage());
    }
}
