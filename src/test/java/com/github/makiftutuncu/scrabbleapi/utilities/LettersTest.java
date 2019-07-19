package com.github.makiftutuncu.scrabbleapi.utilities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class LettersTest {
    @Test public void testLowerCaseWord() {
        assertEquals("", Letters.lowerCase(""));
        assertEquals("123", Letters.lowerCase("123"));
        assertEquals("deneme", Letters.lowerCase("deneme"));
        assertEquals("deneme", Letters.lowerCase("DENEME"));
        assertEquals("ığüşiöç", Letters.lowerCase("IĞÜŞİÖÇ"));
    }

    @Test public void testLowerCaseLetter() {
        assertEquals('1', Letters.lowerCase('1'));
        assertEquals('@', Letters.lowerCase('@'));
        assertEquals('a', Letters.lowerCase('a'));
        assertEquals('a', Letters.lowerCase('A'));
        assertEquals('ı', Letters.lowerCase('I'));
        assertEquals('ğ', Letters.lowerCase('Ğ'));
        assertEquals('ü', Letters.lowerCase('Ü'));
        assertEquals('ş', Letters.lowerCase('Ş'));
        assertEquals('i', Letters.lowerCase('İ'));
        assertEquals('ö', Letters.lowerCase('Ö'));
        assertEquals('ç', Letters.lowerCase('Ç'));
    }

    @Test public void testPointsOfWord() {
        assertEquals(0,  Letters.pointsOf(""));
        assertEquals(0,  Letters.pointsOf("x"));
        assertEquals(0,  Letters.pointsOf("123"));
        assertEquals(0,  Letters.pointsOf("@"));
        assertEquals(2,  Letters.pointsOf("ak"));
        assertEquals(9,  Letters.pointsOf("deneme"));
        assertEquals(25, Letters.pointsOf("çekoslavakya"));
        assertEquals(2,  Letters.pointsOf("AK"));
        assertEquals(9,  Letters.pointsOf("DENEME"));
        assertEquals(25, Letters.pointsOf("ÇEKOSLAVAKYA"));
    }

    @Test public void testPointsOfLetter() {
        assertEquals(0,  Letters.pointsOf('1'));
        assertEquals(0,  Letters.pointsOf('x'));
        assertEquals(0,  Letters.pointsOf('@'));
        assertEquals(1,  Letters.pointsOf('a'));
        assertEquals(3,  Letters.pointsOf('b'));
        assertEquals(4,  Letters.pointsOf('c'));
        assertEquals(4,  Letters.pointsOf('ç'));
        assertEquals(3,  Letters.pointsOf('d'));
        assertEquals(1,  Letters.pointsOf('e'));
        assertEquals(7,  Letters.pointsOf('f'));
        assertEquals(5,  Letters.pointsOf('g'));
        assertEquals(8,  Letters.pointsOf('ğ'));
        assertEquals(5,  Letters.pointsOf('h'));
        assertEquals(2,  Letters.pointsOf('ı'));
        assertEquals(1,  Letters.pointsOf('i'));
        assertEquals(10, Letters.pointsOf('j'));
        assertEquals(1,  Letters.pointsOf('k'));
        assertEquals(1,  Letters.pointsOf('l'));
        assertEquals(2,  Letters.pointsOf('m'));
        assertEquals(1,  Letters.pointsOf('n'));
        assertEquals(2,  Letters.pointsOf('o'));
        assertEquals(7,  Letters.pointsOf('ö'));
        assertEquals(5,  Letters.pointsOf('p'));
        assertEquals(1,  Letters.pointsOf('r'));
        assertEquals(2,  Letters.pointsOf('s'));
        assertEquals(4,  Letters.pointsOf('ş'));
        assertEquals(1,  Letters.pointsOf('t'));
        assertEquals(2,  Letters.pointsOf('u'));
        assertEquals(3,  Letters.pointsOf('ü'));
        assertEquals(7,  Letters.pointsOf('v'));
        assertEquals(3,  Letters.pointsOf('y'));
        assertEquals(4,  Letters.pointsOf('z'));
    }
}
