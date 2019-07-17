package com.github.makiftutuncu.scrabbleapi.views;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordResponseTest {
    @Test void testPointsOfWord() {
        WordResponse word = new WordResponse("ekmek");
        assertEquals(word.getPoints(), 6);
    }
}
