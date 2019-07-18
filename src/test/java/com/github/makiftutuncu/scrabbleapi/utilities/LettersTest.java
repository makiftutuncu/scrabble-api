package com.github.makiftutuncu.scrabbleapi.utilities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class LettersTest {
    @Test public void testLowerCaseWord() {
        assertEquals(Letters.lowerCase(""), "");
        assertEquals(Letters.lowerCase("123"), "123");
        assertEquals(Letters.lowerCase("deneme"), "deneme");
        assertEquals(Letters.lowerCase("DENEME"), "deneme");
        assertEquals(Letters.lowerCase("IĞÜŞİÖÇ"), "ığüşiöç");
    }

    @Test public void testLowerCaseLetter() {
        assertEquals(Letters.lowerCase('1'), '1');
        assertEquals(Letters.lowerCase('@'), '@');
        assertEquals(Letters.lowerCase('a'), 'a');
        assertEquals(Letters.lowerCase('A'), 'a');
        assertEquals(Letters.lowerCase('I'), 'ı');
        assertEquals(Letters.lowerCase('Ğ'), 'ğ');
        assertEquals(Letters.lowerCase('Ü'), 'ü');
        assertEquals(Letters.lowerCase('Ş'), 'ş');
        assertEquals(Letters.lowerCase('İ'), 'i');
        assertEquals(Letters.lowerCase('Ö'), 'ö');
        assertEquals(Letters.lowerCase('Ç'), 'ç');
    }

    @Test public void testPointsOfWord() {
        assertEquals(Letters.pointsOf(""), 0);
        assertEquals(Letters.pointsOf("x"), 0);
        assertEquals(Letters.pointsOf("123"), 0);
        assertEquals(Letters.pointsOf("@"), 0);
        assertEquals(Letters.pointsOf("ak"), 2);
        assertEquals(Letters.pointsOf("deneme"), 9);
        assertEquals(Letters.pointsOf("çekoslavakya"), 25);
        assertEquals(Letters.pointsOf("AK"), 2);
        assertEquals(Letters.pointsOf("DENEME"), 9);
        assertEquals(Letters.pointsOf("ÇEKOSLAVAKYA"), 25);
    }

    @Test public void testPointsOfLetter() {
        assertEquals(Letters.pointsOf('1'), 0);
        assertEquals(Letters.pointsOf('x'), 0);
        assertEquals(Letters.pointsOf('@'), 0);
        assertEquals(Letters.pointsOf('a'), 1);
        assertEquals(Letters.pointsOf('b'), 3);
        assertEquals(Letters.pointsOf('c'), 4);
        assertEquals(Letters.pointsOf('ç'), 4);
        assertEquals(Letters.pointsOf('d'), 3);
        assertEquals(Letters.pointsOf('e'), 1);
        assertEquals(Letters.pointsOf('f'), 7);
        assertEquals(Letters.pointsOf('g'), 5);
        assertEquals(Letters.pointsOf('ğ'), 8);
        assertEquals(Letters.pointsOf('h'), 5);
        assertEquals(Letters.pointsOf('ı'), 2);
        assertEquals(Letters.pointsOf('i'), 1);
        assertEquals(Letters.pointsOf('j'), 10);
        assertEquals(Letters.pointsOf('k'), 1);
        assertEquals(Letters.pointsOf('l'), 1);
        assertEquals(Letters.pointsOf('m'), 2);
        assertEquals(Letters.pointsOf('n'), 1);
        assertEquals(Letters.pointsOf('o'), 2);
        assertEquals(Letters.pointsOf('ö'), 7);
        assertEquals(Letters.pointsOf('p'), 5);
        assertEquals(Letters.pointsOf('r'), 1);
        assertEquals(Letters.pointsOf('s'), 2);
        assertEquals(Letters.pointsOf('ş'), 4);
        assertEquals(Letters.pointsOf('t'), 1);
        assertEquals(Letters.pointsOf('u'), 2);
        assertEquals(Letters.pointsOf('ü'), 3);
        assertEquals(Letters.pointsOf('v'), 7);
        assertEquals(Letters.pointsOf('y'), 3);
        assertEquals(Letters.pointsOf('z'), 4);
    }
}
