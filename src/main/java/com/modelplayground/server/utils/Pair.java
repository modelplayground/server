package com.modelplayground.server.utils;

public class Pair<K extends Comparable,V> implements Comparable<Pair>{
    K key;
    V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public int compareTo(Pair o) {
        return this.key.compareTo(o.getKey());
    }
}
