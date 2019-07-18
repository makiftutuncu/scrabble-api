package com.github.makiftutuncu.scrabbleapi.views;

import com.github.makiftutuncu.scrabbleapi.utilities.Letters;

import java.util.Objects;

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

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WordResponse)) return false;
        WordResponse that = (WordResponse) o;
        return this.points == that.points && this.word.equals(that.word);
    }

    @Override public int hashCode() {
        return Objects.hash(word, points);
    }
}
