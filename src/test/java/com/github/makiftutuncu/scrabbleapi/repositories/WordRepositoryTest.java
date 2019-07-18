package com.github.makiftutuncu.scrabbleapi.repositories;

import com.github.makiftutuncu.scrabbleapi.SpringTest;

import com.github.makiftutuncu.scrabbleapi.models.Word;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;

public class WordRepositoryTest extends SpringTest {
    private WordRepository repository;

    @Before public void truncate() {
        this.repository = (WordRepository) wac.getBean("wordRepository");
    }

    @Test public void getWordThatDoesNotExist() {
        assertEquals(repository.getWord("java"), Optional.empty());
    }

    @Test public void getWord() {
        assertEquals(repository.getWord("hava"), Optional.of(new Word(439482, "hava")));
    }

    @Test public void getWordsWithNull() {
        assertEquals(repository.getWords(null), Collections.emptyList());
    }

    @Test public void getWords() {
        assertEquals(repository.getWords(Arrays.asList("hava", "civa")), Collections.singletonList(new Word(439482, "hava")));
    }
}
