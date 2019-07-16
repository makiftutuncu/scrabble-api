package com.github.makiftutuncu.scrabbleapi.views;

import com.github.makiftutuncu.scrabbleapi.models.Move;
import com.github.makiftutuncu.scrabbleapi.utilities.Letters;

public class MoveResponse {
    public final String word;
    public final int row;
    public final int column;
    public final boolean isHorizontal;
    public final int points;

    public MoveResponse(Move move) {
        this.word = move.getWord().getWord();
        this.row = move.getRow();
        this.column = move.getColumn();
        this.isHorizontal = move.isHorizontal();
        this.points = Letters.pointsOf(word);
    }
}
