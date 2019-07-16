package com.github.makiftutuncu.scrabbleapi.models;

import javax.persistence.*;
import java.util.StringJoiner;

@Entity
@Table(name = "moves")
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    private Board board;

    @OneToOne(fetch = FetchType.EAGER)
    private Word word;

    @Column(name = "row")
    private int row;

    @Column(name = "column")
    private int column;

    @Column(name = "is_horizontal")
    private boolean isHorizontal;

    public Move() {}

    public Move(Board board, Word word, int row, int column, boolean isHorizontal) {
        this.board = board;
        this.word = word;
        this.row = row;
        this.column = column;
        this.isHorizontal = isHorizontal;
    }

    public int getId() {
        return id;
    }

    public Move setId(int id) {
        this.id = id;
        return this;
    }

    public Board getBoard() {
        return board;
    }

    public Move setBoard(Board board) {
        this.board = board;
        return this;
    }

    public Word getWord() {
        return word;
    }

    public Move setWord(Word word) {
        this.word = word;
        return this;
    }

    public int getRow() {
        return row;
    }

    public Move setRow(int row) {
        this.row = row;
        return this;
    }

    public int getColumn() {
        return column;
    }

    public Move setColumn(int column) {
        this.column = column;
        return this;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public Move setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
        return this;
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
