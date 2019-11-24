package com.modelplayground.server.algorithms.persistentranking.domain;


public abstract class Rank implements Comparable<Rank> {

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract String toString();


}
