package com.github.makiftutuncu.scrabbleapi.utilities;

import org.springframework.http.HttpStatus;

public class ScrabbleException extends RuntimeException {
    private HttpStatus status;

    public ScrabbleException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public ScrabbleException(String message) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
