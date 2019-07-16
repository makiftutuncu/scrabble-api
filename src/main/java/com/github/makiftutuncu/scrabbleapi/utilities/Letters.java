package com.github.makiftutuncu.scrabbleapi.utilities;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class Letters {
    private static final Locale locale = new Locale("tr", "TR");

    private static final Map<Character, Integer> points = new HashMap<>();
    static {
        points.put('A', 1);
        points.put('B', 3);
        points.put('C', 4);
        points.put('Ç', 4);
        points.put('D', 3);
        points.put('E', 1);
        points.put('F', 7);
        points.put('G', 5);
        points.put('Ğ', 8);
        points.put('H', 5);
        points.put('I', 2);
        points.put('İ', 1);
        points.put('J', 10);
        points.put('K', 1);
        points.put('L', 1);
        points.put('M', 2);
        points.put('N', 1);
        points.put('O', 2);
        points.put('Ö', 7);
        points.put('P', 5);
        points.put('R', 1);
        points.put('S', 2);
        points.put('Ş', 4);
        points.put('T', 1);
        points.put('U', 2);
        points.put('Ü', 3);
        points.put('V', 7);
        points.put('Y', 3);
        points.put('Z', 4);
    }

    public static char upperCase(char letter) {
        return String.valueOf(letter).toUpperCase(locale).charAt(0);
    }

    public static int pointOf(char letter) {
        return points.getOrDefault(upperCase(letter), 0);
    }

    public static int pointsOf(String word) {
        return word.chars().map(c -> pointOf((char) c)).reduce(0, Integer::sum);
    }

    public static boolean isValid(char letter) {
        return points.containsKey(upperCase(letter));
    }
}
