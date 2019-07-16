package com.github.makiftutuncu.scrabbleapi.views;

import com.github.makiftutuncu.scrabbleapi.models.Move;
import com.github.makiftutuncu.scrabbleapi.utilities.Letters;

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
}
