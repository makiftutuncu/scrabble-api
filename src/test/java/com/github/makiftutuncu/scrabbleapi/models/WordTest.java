package com.github.makiftutuncu.scrabbleapi.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class WordTest {
    @Test public void test() {
        Word word = new Word(1, "deneme");

        assertEquals(1, word.getId());
        assertEquals("deneme", word.getWord());
        assertEquals("{\"id\":1,\"word\":\"deneme\"}", word.toString());
    }
}
