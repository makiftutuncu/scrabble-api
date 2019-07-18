package com.github.makiftutuncu.scrabbleapi.repositories;

import com.github.makiftutuncu.scrabbleapi.models.Word;
import com.github.makiftutuncu.scrabbleapi.utilities.HibernateUtils;
import com.github.makiftutuncu.scrabbleapi.utilities.Letters;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("wordRepository")
public class WordRepository {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public WordRepository() {}

    public Optional<Word> getWord(String word) {
        logger.info("Getting word {} from DB", word);

        return HibernateUtils.withSession(session -> {
            Query<Word> query = session
                    .createQuery("SELECT w FROM Word w WHERE w.word = :word", Word.class)
                    .setParameter("word", Letters.lowerCase(word));

            return query.uniqueResultOptional();
        });
    }

    public List<Word> getWords(List<String> words) {
        logger.info("Getting {} words from DB", words == null ? -1 : words.size());

        if (words == null) {
            return new ArrayList<>();
        }

        return HibernateUtils.withSession(session -> {
            Query<Word> query = session
                    .createQuery("SELECT w FROM Word w WHERE w.word IN :words", Word.class)
                    .setParameterList("words", words.stream().map(Letters::lowerCase).collect(Collectors.toList()));

            return query.list();
        });
    }
}
