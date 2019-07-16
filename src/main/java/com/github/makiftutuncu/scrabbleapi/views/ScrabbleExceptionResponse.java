package com.github.makiftutuncu.scrabbleapi.views;

public class ScrabbleExceptionResponse {
    private String message;

    public ScrabbleExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
