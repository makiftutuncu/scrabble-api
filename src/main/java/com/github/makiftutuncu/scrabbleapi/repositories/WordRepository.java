package com.github.makiftutuncu.scrabbleapi.repositories;

import com.github.makiftutuncu.scrabbleapi.models.Word;
import com.github.makiftutuncu.scrabbleapi.utilities.HibernateUtils;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WordRepository {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public WordRepository() {}

    public Optional<Word> getWord(String word) {
        logger.info("Getting word {} from DB", word);

        return HibernateUtils.withSession(session -> {
            Query query = session
                    .createQuery("SELECT w FROM Word w WHERE w.word = :word", Word.class)
                    .setParameter("word", word);

            return Optional.ofNullable((Word) query.uniqueResult());
        });
    }
}
