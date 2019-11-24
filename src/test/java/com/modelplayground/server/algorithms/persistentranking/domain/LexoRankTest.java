package com.modelplayground.server.algorithms.persistentranking.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LexoRankTest {


    @Test
    void increment() {
    }

    @Test
    void decrementTest1() {
        LexoRank lexoRank = new LexoRank(0,"aa","abcd");
        lexoRank.decrement(4);
        assertEquals(lexoRank.getRank(),"abbz");
    }
    @Test
    void decrementTest2() {
        LexoRank lexoRank = new LexoRank(0,"aa","abcf");
        lexoRank.decrement(3);
        assertEquals(lexoRank.getRank(),"abcc");
    }
    @Test
    void decrementTest3() {
        LexoRank lexoRank = new LexoRank(0,"aa","abcd");
        lexoRank.decrement(30);
        assertEquals(lexoRank.getRank(),"abaz");
    }
    @Test
    void decrementTest4() {
        LexoRank lexoRank = new LexoRank(0,"aa","abcd");
        lexoRank.decrement(60);
        assertEquals(lexoRank.getRank(),"aazv");
    }

}