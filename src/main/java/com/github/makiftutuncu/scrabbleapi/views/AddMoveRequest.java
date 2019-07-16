package com.github.makiftutuncu.scrabbleapi.views;

import java.util.StringJoiner;

public class AddMoveRequest {
    private String word;
    private int row;
    private int column;
    private boolean isHorizontal;

    public AddMoveRequest(String word, int row, int column, boolean isHorizontal) {
        setWord(word);
        setRow(row);
        setColumn(column);
        setIsHorizontal(isHorizontal);
    }

    public AddMoveRequest() {
        this("", -1, -1, true);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
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

    public void setIsHorizontal(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }

    @Override public String toString() {
        return new StringJoiner(",", "{", "}")
                .add("\"word\":\"" + word + "\"")
                .add("\"row\":" + row)
                .add("\"column\":" + column)
                .add("\"isHorizontal\":" + isHorizontal)
                .toString();
    }
}
