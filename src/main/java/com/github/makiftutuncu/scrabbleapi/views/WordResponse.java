package com.github.makiftutuncu.scrabbleapi.views;

import com.github.makiftutuncu.scrabbleapi.utilities.Letters;

public class WordResponse {
    private String word;
    private int points;

    public WordResponse(String word) {
        this.word = word;
        this.points = Letters.pointsOf(word);
    }

    public String getWord() {
        return word;
    }

    public int getPoints() {
        return points;
    }
}
