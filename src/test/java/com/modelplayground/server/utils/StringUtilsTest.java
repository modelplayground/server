package com.modelplayground.server.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void getLexographicMiddleString() {

    }

    @Test
    void getLexographicMiddleString_SimpleTest1() {
        String s1 = "abc";
        String s2 = "abe";
        String res = StringUtils.getLexographicMiddleString(s1,s2);
        assertEquals(res,"abd");
    }

    @Test
    void getLexographicMiddleString_SimpleTest2() {
        String s1 = "aace";
        String s2 = "aalf";
        String res = StringUtils.getLexographicMiddleString(s1,s2);
        assertEquals(res,"aag");
    }

    @Test
    void getLexographicMiddleString_UnevenLengthTest() {
        String s1 = "aa";
        String s2 = "aaefg";
        String res = StringUtils.getLexographicMiddleString(s1,s2);
        assertEquals(res,"aac");
    }

    @Test
    void getLexographicMiddleString_ConsecutiveCharacterTest() {
        String s1 = "aa";
        String s2 = "ab";
        String res = StringUtils.getLexographicMiddleString(s1,s2);
        assertEquals(res,"aam");
    }

    @Test
    void getLexographicMiddleString_ConsecutiveCharacterTest1() {
        String s1 = "aaa";
        String s2 = "abe";
        String res = StringUtils.getLexographicMiddleString(s1,s2);
        assertEquals(res,"aap");
    }

    @Test
    void getLexographicMiddleString_ConsecutiveCharacterTest2() {
        String s1 = "aay";
        String s2 = "abc";
        String res = StringUtils.getLexographicMiddleString(s1,s2);
        assertEquals(res,"aba");
    }

    @Test
    void getLexographicMiddleString_ConsecutiveCharacterTest3() {
        String s1 = "aazzz";
        String s2 = "abaaa";
        String res = StringUtils.getLexographicMiddleString(s1,s2);
        assertEquals(res,"aazzzm");
    }
}