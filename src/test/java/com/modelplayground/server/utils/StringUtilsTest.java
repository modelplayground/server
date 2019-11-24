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

    @Test
    void getLexographicMiddleString_ConsecutiveCharacterTest4() {
        String s1 = "yz";
        String s2 = "zzz";
        String res = StringUtils.getLexographicMiddleString(s1,s2);
        assertEquals(res,"zm");
    }

    @Test
    void incrementTest1() {
        String str = StringUtils.increment("abbz",4);
        assertEquals(str,"abcd");
    }
    @Test
    void incrementTest2() {
        String str = StringUtils.increment("abcc",3);
        assertEquals(str,"abcf");
    }
    @Test
    void incrementTest3() {
        String str = StringUtils.increment("abaz",30);
        assertEquals(str,"abcd");
    }
    @Test
    void incrementTest4() {
        String str = StringUtils.increment("aazv",60);
        assertEquals(str,"abcd");
    }


    @Test
    void decrementTest1() {
        String str = StringUtils.decrement("abcd",4);
        assertEquals(str,"abbz");
    }
    @Test
    void decrementTest2() {
        String str = StringUtils.decrement("abcf",3);
        assertEquals(str,"abcc");
    }
    @Test
    void decrementTest3() {
        String str = StringUtils.decrement("abcd",30);

        assertEquals(str,"abaz");
    }
    @Test
    void decrementTest4() {
        String str = StringUtils.decrement("abcd",60);
        assertEquals(str,"aazv");
    }

}