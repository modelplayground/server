package com.modelplayground.server.algorithms.persistentranking.domain;


public abstract class Rank implements Comparable<Rank> {
    public abstract void increment(long val);
    public abstract void decrement(long val);


    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract String toString();


}
