package com.github.makiftutuncu.scrabbleapi.views;

import com.github.makiftutuncu.scrabbleapi.utilities.Letters;

public class WordResponse {
    public final String word;
    public final int points;

    public WordResponse(String word) {
        this.word = word;
        this.points = Letters.pointsOf(word);
    }
}
