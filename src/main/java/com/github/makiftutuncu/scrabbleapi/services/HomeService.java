package com.github.makiftutuncu.scrabbleapi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HomeService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void greet() {
        logger.info("Welcome to Scrabble API!");
    }
}
