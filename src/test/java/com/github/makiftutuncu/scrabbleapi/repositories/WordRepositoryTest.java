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
        assertEquals(Optional.empty(), repository.getWord("java"));
    }

    @Test public void getWord() {
        assertEquals(Optional.of(new Word(439482, "hava")), repository.getWord("hava"));
    }

    @Test public void getWordsWithNull() {
        assertEquals(Collections.emptyList(), repository.getWords(null));
    }

    @Test public void getWords() {
        assertEquals(Collections.singletonList(new Word(439482, "hava")), repository.getWords(Arrays.asList("hava", "civa")));
    }
}
