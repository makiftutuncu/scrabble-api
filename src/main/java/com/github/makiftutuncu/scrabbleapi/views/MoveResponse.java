package com.github.makiftutuncu.scrabbleapi.views;

import com.github.makiftutuncu.scrabbleapi.models.Move;
import com.github.makiftutuncu.scrabbleapi.utilities.Letters;

import java.util.Objects;

public class MoveResponse {
    private String word;
    private int row;
    private int column;
    private boolean isHorizontal;
    private int points;

    public MoveResponse(Move move) {
        this.word = move.getWord().getWord();
        this.row = move.getRow();
        this.column = move.getColumn();
        this.isHorizontal = move.getIsHorizontal();
        this.points = Letters.pointsOf(word);
    }

    public String getWord() {
        return word;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean getIsHorizontal() {
        return isHorizontal;
    }

    public int getPoints() {
        return points;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoveResponse)) return false;
        MoveResponse that = (MoveResponse) o;
        return this.row == that.row && this.column == that.column && this.isHorizontal == that.isHorizontal && this.points == that.points && this.word.equals(that.word);
    }

    @Override public int hashCode() {
        return Objects.hash(word, row, column, isHorizontal, points);
    }
}
