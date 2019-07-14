package com.github.makiftutuncu.scrabbleapi.views;

import com.github.makiftutuncu.scrabbleapi.models.Board;

public class BoardResponse {
    public final int id;
    public final String name;
    public final int size;
    public final boolean isActive;
    public final String[][] letters;

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.name = board.getName();
        this.size = board.getSize();
        this.isActive = board.isActive();
        this.letters = board.getLetters();
    }
}
