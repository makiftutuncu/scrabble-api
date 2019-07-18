package com.github.makiftutuncu.scrabbleapi.views;

import com.github.makiftutuncu.scrabbleapi.models.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BoardResponse {
    private int id;
    private String name;
    private int size;
    private boolean isActive;
    private List<WordResponse> words;
    private int points;

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.name = board.getName();
        this.size = board.getSize();
        this.isActive = board.getIsActive();
        this.words = board.getMoves() == null ? new ArrayList<>() : board.getMoves().stream().map(move -> new WordResponse(move.getWord().getWord())).collect(Collectors.toList());
        this.points = this.words.stream().map(WordResponse::getPoints).reduce(0, Integer::sum);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public List<WordResponse> getWords() {
        return words;
    }

    public int getPoints() {
        return points;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardResponse)) return false;
        BoardResponse that = (BoardResponse) o;
        return this.id == that.id && this.size == that.size && this.isActive == that.isActive && this.points == that.points && this.name.equals(that.name) && this.words.equals(that.words);
    }

    @Override public int hashCode() {
        return Objects.hash(id, name, size, isActive, words, points);
    }
}
