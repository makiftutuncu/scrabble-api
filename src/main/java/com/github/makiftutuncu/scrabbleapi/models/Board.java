package com.github.makiftutuncu.scrabbleapi.models;

import com.github.makiftutuncu.scrabbleapi.utilities.Letters;
import com.github.makiftutuncu.scrabbleapi.views.CreateBoardRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Stream;

@Entity
@Table(name = "boards", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public final class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private int size;

    @Column(name = "is_active")
    private boolean isActive;

    public Board() {}

    public Board(int id, String name, int size, boolean isActive) {
        setId(id);
        setName(name);
        setSize(size);
        setActive(isActive);
        this.board = new Cell[size][size];
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static final int DEFAULT_SIZE = 15;

    private Cell[][] board;

    private boolean isEmpty = true;

    public static Board from(CreateBoardRequest request) {
        return new Board(-1, request.name, request.size, true);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    private void setSize(int size) {
        this.size = size;
    }

    public boolean isActive() {
        return isActive;
    }

    private void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Adds given word to the board starting at given location in given direction
     *
     * @param wordString   Word to add
     * @param row          Starting row to which to add the word
     * @param column       Starting column to which to add the word
     * @param isHorizontal Whether word should be added horizontally
     *
     * @return Total points earned from adding the word in Optional if the word is added to the board
     */
    public Optional<Integer> addWord(String wordString, int row, int column, boolean isHorizontal) {
        Optional<Word> maybeWord = prepareWordToAdd(wordString, row, column, isHorizontal);

        maybeWord.ifPresent(word -> {
            for (Cell cell : word.cells) {
                board[cell.row][cell.column] = cell;
            }
            isEmpty = false;
        });

        return maybeWord.map(word -> word.point);
    }

    /**
     * Prepares a word to add to the board starting at given location in given direction
     *
     * @param wordString   Word to add
     * @param row          Starting row to which to add the word
     * @param column       Starting column to which to add the word
     * @param isHorizontal Whether word should be added horizontally
     *
     * @return Prepared word in Optional if the word can be added to the board
     */
    private Optional<Word> prepareWordToAdd(String wordString, int row, int column, boolean isHorizontal) {
        if (wordString == null || wordString.trim().isEmpty()) {
            logger.error("Word must not be empty!");
            return Optional.empty();
        }

        if (wordString.length() > DEFAULT_SIZE) {
            logger.error("Word '{}' is too long, it must not be longer than {} characters!", wordString, DEFAULT_SIZE);
            return Optional.empty();
        }

        wordString = wordString.trim();

        int length = wordString.length();

        if (row < 0 || row >= DEFAULT_SIZE) {
            logger.error("Row {} is invalid, it must be in [0, {}) range!", row, DEFAULT_SIZE);
            return Optional.empty();
        }

        if (column < 0 || column >= DEFAULT_SIZE) {
            logger.error("Column {} is invalid, it must be in [0, {}) range!", column, DEFAULT_SIZE);
            return Optional.empty();
        }

        if (!isActive) {
            logger.error("Board is deactivated!");
            return Optional.empty();
        }

        Cell[] cells = new Cell[length];
        boolean boardEmpty = isEmpty;

        for (int i = 0; i < length; i++) {
            int currentRow    = isHorizontal ? row : row + i;
            int currentColumn = isHorizontal ? column + i : column;

            Cell existingCell = board[currentRow][currentColumn];
            Cell newCell      = new Cell(currentRow, currentColumn, wordString.charAt(i));

            if (!boardEmpty && i == 0 && existingCell == null) {
                logger.error("Cannot add word '{}' to [{}, {}] {}, cannot start from empty cell [{}, {}] on a non-empty board!", wordString, row, column, isHorizontal ? "horizontally" : "vertically", currentRow, currentColumn);
                return Optional.empty();
            }

            if (existingCell != null && i != 0 && existingCell.letter != newCell.letter) {
                logger.error("Cannot add word '{}' to [{}, {}] {}, cell [{}, {}] already has letter '{}'!", wordString, row, column, isHorizontal ? "horizontally" : "vertically", currentRow, currentColumn, existingCell.letter);
                return Optional.empty();
            }

            if (existingCell != null && existingCell.letter != newCell.letter) {
                logger.error("Cannot add word '{}' to [{}, {}] {}, cell [{}, {}] already has letter '{}'!", wordString, row, column, isHorizontal ? "horizontally" : "vertically", currentRow, currentColumn, existingCell.letter);
                return Optional.empty();
            }


            logger.info("Adding letter '{}' to cell [{}, {}]", newCell.letter, currentRow, currentColumn);
            cells[i] = existingCell != null ? existingCell : newCell;
            boardEmpty = false;
        }

        Word word = new Word(row, column, cells);

        return Optional.of(word);
    }

    public void deactivate() {
        setActive(false);
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            for (int j = 0; j < DEFAULT_SIZE; j++) {
                Cell cell = board[i][j];

                if (j == 1) { sb.append("|"); }
                sb.append(String.format(" %s ", cell == null ? ' ' : cell.letter));
                if (j > 0 && j < DEFAULT_SIZE - 1) { sb.append("|"); }
            }

            if (i < DEFAULT_SIZE - 1) {
                sb.append("\n");
                for (int j = 0; j < DEFAULT_SIZE; j++) {
                    if (j == 1) { sb.append("|"); }
                    sb.append("---");
                    if (j > 0 && j < DEFAULT_SIZE - 1) { sb.append("|"); }
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public String[][] getLetters() {
        String[][] letters = new String[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell cell = board[i][j];
                letters[i][j] = cell == null ? "" : String.valueOf(cell.letter);
            }
        }
        return letters;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"id\":" + id)
                .add("\"name\":\"" + name + "\"")
                .add("\"size\":" + size)
                .add("\"isActive\":" + isActive)
                .toString();
    }

    public static final class Cell {
        public final int row;
        public final int column;
        public final char letter;
        public final int point;

        private Cell(int row, int column, char letter) {
            this.row    = row;
            this.column = column;
            this.letter = Letters.upperCase(letter);
            this.point  = Letters.pointOf(this.letter);
        }
    }

    public static final class Word {
        public final int row;
        public final int column;
        public final Cell[] cells;
        public final int point;

        private Word(int row, int column, Cell[] cells) {
            this.row    = row;
            this.column = column;
            this.cells  = cells;
            this.point  = Stream.of(cells).map(c -> c.point).reduce(0, Integer::sum);
        }
    }
}
