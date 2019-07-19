package com.github.makiftutuncu.scrabbleapi.utilities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;

@RunWith(BlockJUnit4ClassRunner.class)
public class ScrabbleExceptionTest {
    @Test public void testWithDefaults() {
        ScrabbleException exception = new ScrabbleException("test");

        assertEquals("test", exception.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }

    @Test public void testWithCustomData() {
        ScrabbleException exception = new ScrabbleException(HttpStatus.I_AM_A_TEAPOT, "test");

        assertEquals("test", exception.getMessage());
        assertEquals(HttpStatus.I_AM_A_TEAPOT, exception.getStatus());
    }
}
