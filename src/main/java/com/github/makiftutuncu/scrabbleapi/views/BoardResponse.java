package com.github.makiftutuncu.scrabbleapi.views;

import com.github.makiftutuncu.scrabbleapi.models.Board;

import java.util.List;
import java.util.stream.Collectors;

public class BoardResponse {
    public final int id;
    public final String name;
    public final int size;
    public final boolean isActive;
    public final List<WordResponse> words;
    public final int points;

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.name = board.getName();
        this.size = board.getSize();
        this.isActive = board.isActive();
        this.words = board.getMoves().stream().map(move -> new WordResponse(move.getWord().getWord())).collect(Collectors.toList());
        this.points = this.words.stream().map(wr -> wr.points).reduce(0, Integer::sum);
    }
}
