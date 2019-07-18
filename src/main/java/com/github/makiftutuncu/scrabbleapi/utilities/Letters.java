package com.github.makiftutuncu.scrabbleapi.utilities;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class Letters {
    private static final Locale locale = new Locale("tr", "TR");

    private static final Map<Character, Integer> points = new HashMap<>();
    static {
        points.put('a', 1);
        points.put('b', 3);
        points.put('c', 4);
        points.put('ç', 4);
        points.put('d', 3);
        points.put('e', 1);
        points.put('f', 7);
        points.put('g', 5);
        points.put('ğ', 8);
        points.put('h', 5);
        points.put('ı', 2);
        points.put('i', 1);
        points.put('j', 10);
        points.put('k', 1);
        points.put('l', 1);
        points.put('m', 2);
        points.put('n', 1);
        points.put('o', 2);
        points.put('ö', 7);
        points.put('p', 5);
        points.put('r', 1);
        points.put('s', 2);
        points.put('ş', 4);
        points.put('t', 1);
        points.put('u', 2);
        points.put('ü', 3);
        points.put('v', 7);
        points.put('y', 3);
        points.put('z', 4);
    }

    public static String lowerCase(String word) {
        return word.trim().toLowerCase(locale);
    }

    public static char lowerCase(char letter) {
        return String.valueOf(letter).toLowerCase(locale).charAt(0);
    }

    public static int pointsOf(String word) {
        return word.chars().map(c -> pointsOf((char) c)).reduce(0, Integer::sum);
    }

    public static int pointsOf(char letter) {
        return points.getOrDefault(lowerCase(letter), 0);
    }
}
