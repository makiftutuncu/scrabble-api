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

        assertEquals(exception.getMessage(), "test");
        assertEquals(exception.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test public void testWithCustomData() {
        ScrabbleException exception = new ScrabbleException(HttpStatus.I_AM_A_TEAPOT, "test");

        assertEquals(exception.getMessage(), "test");
        assertEquals(exception.getStatus(), HttpStatus.I_AM_A_TEAPOT);
    }
}
