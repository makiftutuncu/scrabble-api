package com.github.makiftutuncu.scrabbleapi;

import com.github.makiftutuncu.scrabbleapi.models.Board;

public class Test {
    public static void main(String[] args) {
        Board board = new Board();
        board.addWord("çalışmak", 0, 0, true);
        board.addWord("çorap", 0, 0, false);
        board.addWord("makas", 0, 5, false);
        board.addWord("kitap", 2, 5, true);
        System.out.println(board.print());
    }
}
