package com.github.makiftutuncu.scrabbleapi.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.stream.Stream;

public final class Board {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static final int SIZE = 15;

    private Cell[][] board = new Cell[SIZE][SIZE];

    private boolean isActive = true;
    private boolean isEmpty = true;

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

        if (wordString.length() > SIZE) {
            logger.error("Word '{}' is too long, it must not be longer than {} characters!", wordString, SIZE);
            return Optional.empty();
        }

        wordString = wordString.trim();

        int length = wordString.length();

        if (row < 0 || row >= SIZE) {
            logger.error("Row {} is invalid, it must be in [0, {}) range!", row, SIZE);
            return Optional.empty();
        }

        if (column < 0 || column >= SIZE) {
            logger.error("Column {} is invalid, it must be in [0, {}) range!", column, SIZE);
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
        this.isActive = false;
    }

    public void print() {
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Cell cell = board[i][j];

                if (j == 1) { System.out.print("|"); }
                System.out.printf(" %s ", cell == null ? ' ' : cell.letter);
                if (j > 0 && j < SIZE - 1) { System.out.print("|"); }
            }

            if (i < SIZE - 1) {
                System.out.print("\n");
                for (int j = 0; j < SIZE; j++) {
                    if (j == 1) { System.out.print("|"); }
                    System.out.print("---");
                    if (j > 0 && j < SIZE - 1) { System.out.print("|"); }
                }
                System.out.print("\n");
            }
        }
        System.out.println();
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
