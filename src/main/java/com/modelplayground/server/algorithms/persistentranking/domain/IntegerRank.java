package com.modelplayground.server.algorithms.persistentranking.domain;

import java.util.Objects;

public class IntegerRank extends Rank{
    private Integer rank;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public IntegerRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public void increment(int val) {
        rank += val;
    }

    @Override
    public void decrement(int val) {
        rank -= val;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerRank)) return false;
        IntegerRank that = (IntegerRank) o;
        return Objects.equals(rank, that.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank);
    }

    @Override
    public String toString() {
        return "IntegerRank{" +
                "rank=" + rank +
                '}';
    }

    @Override
    public int compareTo(Rank o) {
        if (this == o) return 0;
        if (!(o instanceof IntegerRank)){
            System.out.println("Comparing Different types of ranks !!");
            return -1;
        }

        IntegerRank that = (IntegerRank) o;
        return this.rank - that.rank;
    }
}
