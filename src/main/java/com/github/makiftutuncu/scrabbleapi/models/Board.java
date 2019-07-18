package com.github.makiftutuncu.scrabbleapi.models;

import com.github.makiftutuncu.scrabbleapi.utilities.Letters;
import com.github.makiftutuncu.scrabbleapi.utilities.ScrabbleException;
import com.github.makiftutuncu.scrabbleapi.views.AddMoveRequest;
import com.github.makiftutuncu.scrabbleapi.views.CreateBoardRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Access(AccessType.PROPERTY)
    private int id;

    @Column(name = "name")
    @Access(AccessType.PROPERTY)
    private String name;

    @Column(name = "size")
    @Access(AccessType.PROPERTY)
    private int size;

    @Column(name = "is_active")
    @Access(AccessType.PROPERTY)
    private boolean isActive;

    @OneToMany(mappedBy = "board", targetEntity = Move.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Access(AccessType.PROPERTY)
    private List<Move> moves;

    public Board() {}

    public Board(String name, int size) {
        setName(name);
        setSize(size);
    }

    public Board(CreateBoardRequest request) {
        setName(request.getName());
        setSize(request.getSize());
        setIsActive(true);
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

    public boolean getIsActive() {
        return isActive;
    }

    private void setIsActive(boolean active) {
        isActive = active;
    }

    public List<Move> getMoves() {
        if (moves == null) {
            moves = new ArrayList<>();
        }
        return moves;
    }

    public void setMoves(List<Move> moves) {
        if (moves != null) {
            getMoves().addAll(moves);
        }
    }

    @Transient
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static final int DEFAULT_SIZE = 15;

    public void addMove(Move move) {
        getMoves().add(move);
    }

    public Move prepareMove(Word word, AddMoveRequest request) {
        if (!isActive) {
            throw new ScrabbleException(HttpStatus.BAD_REQUEST, String.format("Cannot add word '%s', board '%d' is deactivated!", word.getWord(), id));
        }

        String wordString    = word.getWord();
        int row              = request.getRow() < 0 || request.getRow() >= size ? -1 : request.getRow();
        int column           = request.getColumn() < 0 || request.getColumn() >= size ? -1 : request.getColumn();
        boolean isHorizontal = request.getIsHorizontal();

        int length = wordString.length();

        if (wordString.length() > size || (isHorizontal ? column + length : row + length) > size) {
            throw new ScrabbleException(HttpStatus.BAD_REQUEST, String.format("Cannot add word '%s', it is too long!", word.getWord()));
        }

        if (row == -1) {
            throw new ScrabbleException(HttpStatus.BAD_REQUEST, String.format("Cannot add word '%s' to [%d, %d], row must be in [0, %d) range!", word.getWord(), request.getRow(), request.getColumn(), size));
        }

        if (column == -1) {
            throw new ScrabbleException(HttpStatus.BAD_REQUEST, String.format("Cannot add word '%s' to [%d, %d], column must be in [0, %d) range!", word.getWord(), request.getRow(), request.getColumn(), size));
        }

        Cell[][] cells = getCells();
        boolean boardEmpty = moves == null || moves.isEmpty();

        for (int i = 0; i < length; i++) {
            int currentRow    = isHorizontal ? row : row + i;
            int currentColumn = isHorizontal ? column + i : column;

            Cell existingCell = cells == null ? null : cells[currentRow][currentColumn];
            Cell newCell      = new Cell(currentRow, currentColumn, wordString.charAt(i));

            if (!boardEmpty && i == 0 && existingCell == null) {
                throw new ScrabbleException(HttpStatus.BAD_REQUEST, String.format("Cannot add word '%s' to [%d, %d] %s, cannot start from empty cell on a non-empty board!", word.getWord(), request.getRow(), request.getColumn(), isHorizontal ? "horizontally" : "vertically"));
            }

            if (existingCell != null && i != 0 && existingCell.letter != newCell.letter) {
                throw new ScrabbleException(HttpStatus.BAD_REQUEST, String.format("Cannot add word '%s' to [%d, %d] %s, cell [%d, %d] already has letter '%s'!", word.getWord(), request.getRow(), request.getColumn(), isHorizontal ? "horizontally" : "vertically", currentRow, currentColumn, existingCell.letter));
            }

            if (existingCell != null && existingCell.letter != newCell.letter) {
                throw new ScrabbleException(HttpStatus.BAD_REQUEST, String.format("Cannot add word '%s' to [%d, %d] %s, word does not start with letter '%s'!", word.getWord(), request.getRow(), request.getColumn(), isHorizontal ? "horizontally" : "vertically", existingCell.letter));
            }

            boardEmpty = false;

            logger.info("Letter '{}' will be added to cell [{}, {}]", newCell.letter, currentRow, currentColumn);
        }

        return new Move(this, word, row, column, isHorizontal);
    }

    public void deactivate() {
        setIsActive(false);
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        Cell[][] cells = getCells();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell cell = cells == null ? null : cells[i][j];

                if (j == 1) { sb.append("|"); }
                sb.append(String.format(" %s ", cell == null ? ' ' : cell.letter));
                if (j > 0 && j < size - 1) { sb.append("|"); }
            }

            if (i < size - 1) {
                sb.append("\n");
                for (int j = 0; j < size; j++) {
                    if (j == 1) { sb.append("|"); }
                    sb.append("---");
                    if (j > 0 && j < size - 1) { sb.append("|"); }
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Transient
    private Cell[][] getCells() {
        if (moves == null) {
            return null;
        }

        Cell[][] cells = new Cell[size][size];
        for (Move move : moves) {
            char[] chars = move.getWord().getWord().toCharArray();
            int row = move.getRow();
            int column = move.getColumn();
            boolean isHorizontal = move.getIsHorizontal();
            int count = 0;
            for (char c : chars) {
                int i = isHorizontal ? row : row + count;
                int j = isHorizontal ? column + count : column;
                cells[i][j] = new Cell(i, j, c);
                count++;
            }
        }

        return cells;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;
        Board that = (Board) o;
        return this.id == that.id && this.size == that.size && this.isActive == that.isActive && this.name.equals(that.name) && this.moves.equals(that.moves);
    }

    @Override public int hashCode() {
        return Objects.hash(id, name, size, isActive, moves);
    }

    @Override public String toString() {
        return new StringJoiner(",", "{", "}")
                .add("\"id\":" + id)
                .add("\"name\":\"" + name + "\"")
                .add("\"size\":" + size)
                .add("\"isActive\":" + isActive)
                .toString();
    }

    public static final class Cell {
        private int row;
        private int column;
        private char letter;
        private int point;

        private Cell(int row, int column, char letter) {
            this.row    = row;
            this.column = column;
            this.letter = letter;
            this.point  = Letters.pointsOf(this.letter);
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public char getLetter() {
            return letter;
        }

        public int getPoint() {
            return point;
        }
    }
}
