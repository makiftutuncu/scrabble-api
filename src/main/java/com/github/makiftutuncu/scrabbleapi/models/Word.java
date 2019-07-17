package com.github.makiftutuncu.scrabbleapi.models;

import javax.persistence.*;
import java.util.StringJoiner;

@Entity
@Table(name = "words")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Access(AccessType.PROPERTY)
    private int id;

    @Column(name = "word")
    @Access(AccessType.PROPERTY)
    private String word;

    public Word() {}

    public Word(String word) {
        this.word = word;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override public String toString() {
        return new StringJoiner(",", "{", "}")
                .add("\"id\":" + id)
                .add("\"word\":\"" + word + "\"")
                .toString();
    }
}
