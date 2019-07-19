package com.github.makiftutuncu.scrabbleapi.views;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class WordResponseTest {
    @Test public void test() {
        WordResponse word = new WordResponse("ekmek");

        assertEquals(6, word.getPoints());
        assertEquals("{\"word\":\"ekmek\",\"points\":6}", word.toString());
    }
}
