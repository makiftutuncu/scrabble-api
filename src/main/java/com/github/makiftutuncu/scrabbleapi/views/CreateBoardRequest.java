package com.github.makiftutuncu.scrabbleapi.views;

import com.github.makiftutuncu.scrabbleapi.models.Board;

import java.util.Optional;
import java.util.StringJoiner;

public class CreateBoardRequest {
    public final String name;
    public final Integer size;

    public CreateBoardRequest() {
        this(null, null);
    }

    public CreateBoardRequest(String name, Integer size) {
        this.name = Optional.ofNullable(name).map(String::trim).filter(n -> !n.isEmpty()).orElse("Board " + System.currentTimeMillis());
        this.size = Optional.ofNullable(size).filter(s -> s > 1).orElse(Board.DEFAULT_SIZE);
    }

    @Override public String toString() {
        return new StringJoiner(",", "{", "}")
                .add("\"name\":\"" + name + "\"")
                .add("\"size\":" + size)
                .toString();
    }
}
