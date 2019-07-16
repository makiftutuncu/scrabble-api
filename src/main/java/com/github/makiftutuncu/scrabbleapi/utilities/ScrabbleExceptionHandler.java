package com.github.makiftutuncu.scrabbleapi.utilities;

import com.github.makiftutuncu.scrabbleapi.views.ScrabbleExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ScrabbleExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(ScrabbleException.class)
    public ResponseEntity<ScrabbleExceptionResponse> handle(ScrabbleException e) {
        logger.error("Caught an exception!", e);
        return ResponseEntity.status(e.getStatus()).body(new ScrabbleExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ScrabbleExceptionResponse> handle(Exception e) {
        logger.error("Caught an exception!", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ScrabbleExceptionResponse(e.getMessage()));
    }
}
