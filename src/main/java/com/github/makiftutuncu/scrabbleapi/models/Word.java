package com.github.makiftutuncu.scrabbleapi.models;

import javax.persistence.*;
import java.util.Objects;
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

    public Word(int id, String word) {
        this.id = id;
        this.word = word;
    }

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

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word that = (Word) o;
        return this.id == that.id && this.word.equals(that.word);
    }

    @Override public int hashCode() {
        return Objects.hash(id, word);
    }
}
