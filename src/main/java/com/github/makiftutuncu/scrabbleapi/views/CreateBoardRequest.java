package com.github.makiftutuncu.scrabbleapi.views;

import com.github.makiftutuncu.scrabbleapi.models.Board;

import java.util.Optional;
import java.util.StringJoiner;

public class CreateBoardRequest {
    private String name;
    private int size;

    public CreateBoardRequest(String name, int size) {
        setName(name);
        setSize(size);
    }

    public CreateBoardRequest() {
        this("", Board.DEFAULT_SIZE);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = Optional.ofNullable(name).map(String::trim).filter(n -> !n.isEmpty()).orElse("Board " + System.currentTimeMillis());
    }

    public int getSize() {
        return size;
    }

    private void setSize(int size) {
        this.size = size > 1 ? size : Board.DEFAULT_SIZE;
    }

    @Override public String toString() {
        return new StringJoiner(",", "{", "}")
                .add("\"name\":\"" + name + "\"")
                .add("\"size\":" + size)
                .toString();
    }
}
