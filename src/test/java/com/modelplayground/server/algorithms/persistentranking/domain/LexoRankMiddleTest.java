package com.modelplayground.server.algorithms.persistentranking.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LexoRankMiddleTest {


    @Test
    void increment() {
    }

    @Test
    void decrementTest1() {
        LexoRankMiddle lexoRankMiddle = new LexoRankMiddle(0,"aa","abcd");
        lexoRankMiddle.decrement(4);
        assertEquals(lexoRankMiddle.getRank(),"abbz");
    }
    @Test
    void decrementTest2() {
        LexoRankMiddle lexoRankMiddle = new LexoRankMiddle(0,"aa","abcf");
        lexoRankMiddle.decrement(3);
        assertEquals(lexoRankMiddle.getRank(),"abcc");
    }
    @Test
    void decrementTest3() {
        LexoRankMiddle lexoRankMiddle = new LexoRankMiddle(0,"aa","abcd");
        lexoRankMiddle.decrement(30);
        assertEquals(lexoRankMiddle.getRank(),"abaz");
    }
    @Test
    void decrementTest4() {
        LexoRankMiddle lexoRankMiddle = new LexoRankMiddle(0,"aa","abcd");
        lexoRankMiddle.decrement(60);
        assertEquals(lexoRankMiddle.getRank(),"aazv");
    }

}