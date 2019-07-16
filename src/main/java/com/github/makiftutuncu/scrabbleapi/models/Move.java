package com.github.makiftutuncu.scrabbleapi.models;

import javax.persistence.*;
import java.util.StringJoiner;

@Entity
@Table(name = "moves")
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Access(AccessType.PROPERTY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Access(AccessType.PROPERTY)
    private Board board;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Access(AccessType.PROPERTY)
    private Word word;

    @Column(name = "row")
    @Access(AccessType.PROPERTY)
    private int row;

    @Column(name = "\"column\"")
    @Access(AccessType.PROPERTY)
    private int column;

    @Column(name = "is_horizontal")
    @Access(AccessType.PROPERTY)
    private boolean isHorizontal;

    public Move() {}

    public Move(Board board, Word word, int row, int column, boolean isHorizontal) {
        setBoard(board);
        setWord(word);
        setRow(row);
        setColumn(column);
        setIsHorizontal(isHorizontal);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean getIsHorizontal() {
        return isHorizontal;
    }

    public void setIsHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }

    @Override public String toString() {
        return new StringJoiner(",", "{", "}")
                .add("\"id\":" + id)
                .add("\"board\":\"" + board.getName() + "\"")
                .add("\"word\":\"" + word.getWord() + "\"")
                .add("\"row\":" + row)
                .add("\"column\":" + column)
                .add("\"isHorizontal\":" + isHorizontal)
                .toString();
    }
}
