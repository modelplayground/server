package com.modelplayground.server.algorithms.persistentranking.domain;


public abstract class Rank implements Comparable<Rank> {
    public abstract void increment(int val);
    public abstract void decrement(int val);


    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract String toString();


}
